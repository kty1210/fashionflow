package com.fashionflow.controller;

import com.fashionflow.dto.MemberFormDTO;
import com.fashionflow.entity.Member;
import com.fashionflow.entity.ProfileImage;
import com.fashionflow.repository.ProfileImageRepository;
import com.fashionflow.service.MemberService;
import com.fashionflow.service.ProfileImgService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final ProfileImageRepository profileImageRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입



    //로그인 페이지 이동
    @GetMapping("/login")
    public String loginPage(){
        if(memberService.findUnregisteredOAuthMember()){
            return "redirect:/oauth/login";
        }

        return "members/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(HttpServletRequest request, Model model){

        String errorMessage = (String) request.getSession().getAttribute("loginErrorMsg");
        model.addAttribute("loginErrorMsg", errorMessage);
        return "members/memberLoginForm";
    }

    @GetMapping("/oauthError")
    public String oauthError(Model model) {

        model.addAttribute("registeredMember", "이미 다른 방법으로 등록된 이메일입니다.");
        return "members/memberLoginForm";
    }


    //회원가입 페이지 이동
    @GetMapping("/register")
    public String registerPage(Model model) {
        if(memberService.findMemberByCurrentEmail()!=null){
            return ("redirect:/");
        }
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "members/memberRegister";
    }

    // 회원 정보 입력
    @PostMapping("/register")
    public ModelAndView registerMember(@Valid @ModelAttribute("memberFormDTO") MemberFormDTO memberFormDTO, BindingResult bindingResult) {


        ModelAndView modelAndView = new ModelAndView();

        // 비밀번호와 비밀번호 확인이 일치하는지 확인
        if (!memberFormDTO.getPwd().equals(memberFormDTO.getConfirmPwd())) {
            //에러 메세지 바인딩 시키는 메소드
            bindingResult.rejectValue("confirmPwd", "error.memberFormDTO", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 유효성 검사 실패 시 회원가입 페이지로 다시 이동
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("memberFormDTO", memberFormDTO);
            modelAndView.setViewName("members/memberRegister");
            return modelAndView;
        }

        try {

            // 유효성 검사 성공 시 회원 등록 처리
            memberService.registerMember(memberFormDTO, passwordEncoder);


            // 회원가입 성공 시 메인 페이지로 리다이렉트
            modelAndView.setViewName("redirect:/");
        } catch (IllegalStateException e) {
            // 실패한 경우 ModelAndView에 기존에 입력한 정보 추가하여 전달
            modelAndView.addObject("memberFormDTO", memberFormDTO);
            // 중복 회원 예외 처리
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("members/memberRegister");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return modelAndView;
    }

    // 회원 수정 페이지
    @GetMapping("/memberEdit")
    public String memberEditPage(Model model) {
        if(memberService.findMemberByCurrentEmail()==null){
            return "redirect:/";
        }

        Member currentMember = memberService.findMemberByCurrentEmail();

        model.addAttribute("currentMember", currentMember);
        model.addAttribute("memberFormDTO", new MemberFormDTO());

        // 현재 회원의 프로필 이미지 가져오기
        ProfileImage profileImage = profileImageRepository.findByMemberId(currentMember.getId());
        model.addAttribute("profileImage", profileImage);

        if (currentMember.getProviderId() != null) {
            return "oauth/oauthEdit"; // OAuth 수정 페이지 이동
        } else {
            return "members/memberEdit"; // 일반 수정 페이지로 이동
        }
    }


    @PostMapping("/memberEdit")
    public String memberEdit(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {
        // 현재 멤버 정보를 가져옴
        Member currentMember = memberService.findMemberByCurrentEmail();
        // 프로필 이미지 정보를 가져옴
        ProfileImage profileImage = profileImageRepository.findByMemberId(currentMember.getId());

        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            model.addAttribute("currentMember", currentMember);
            model.addAttribute("profileImage", profileImage);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "members/memberEdit";
        }

        // 비밀번호와 비밀번호 확인 일치 여부 확인
        if (!memberFormDTO.getPwd().equals(memberFormDTO.getConfirmPwd())) {
            model.addAttribute("pwdErrorMessage", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            model.addAttribute("currentMember", currentMember);
            model.addAttribute("profileImage", profileImage);
            return "members/memberEdit";
        }

        // 닉네임 중복 검사 및 회원 정보 업데이트
        try {
            boolean deleteImage = (memberFormDTO.getProfileImageFile() != null && memberFormDTO.getProfileImageFile().isEmpty());
            memberService.updateMember(memberFormDTO, passwordEncoder, deleteImage);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("currentMember", currentMember);
            model.addAttribute("profileImage", profileImage);
            model.addAttribute("duplicateErrorMessage", e.getMessage());
            return "members/memberEdit";
        } catch (Exception e) {
            model.addAttribute("currentMember", currentMember);
            model.addAttribute("profileImage", profileImage);
            model.addAttribute("generalErrorMessage", "회원 정보 업데이트 중 오류가 발생했습니다.");
            return "members/memberEdit";
        }
    }





    // 회원 탈퇴
    @PostMapping("/deleteMember")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMember() {
        String email = memberService.currentMemberEmail();
        if (email == null) {
            throw new IllegalArgumentException("로그인된 사용자가 없습니다.");
        }
        memberService.deleteMember(email);
    }

    //아이디 찾기 페이지
    @GetMapping("/findId")
    public String findIdPage(){
        if(memberService.findUnregisteredOAuthMember()){
            return "redirect:/oauth/login";
        }
        return "members/findId";
    }

    //회원 아이디 찾기
    @PostMapping("/findId")
    public String findIdByNameAndPhone(@RequestParam("name") String name, @RequestParam("phone") String phone, Model model) {

        try {
            String email = memberService.findId(name, phone);
            model.addAttribute("email", email);
            return "members/findId";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "members/findId";
        }
    }
}
