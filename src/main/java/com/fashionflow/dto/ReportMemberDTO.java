package com.fashionflow.dto;

import com.fashionflow.constant.ReportStatus;
import com.fashionflow.entity.Member;
import com.fashionflow.entity.ReportMember;
import com.fashionflow.entity.ReportMemberTag;
import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportMemberDTO {
    private Long id;

    //private Long reporterMemberId;
    private String reporterMemberEmail;

    //private Long reportedMemberId;
    private String reportedMemberEmail;

    private LocalDateTime regdate;

    private String content;

    private ReportStatus reportStatus;

    private boolean isMyReport;

    @Builder.Default
    private List<ReportMemberTagDTO> reportMemberTagDTOList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public static ReportMemberDTO entityToDTO(ReportMember reportMember){
        ReportMemberDTO reportMemberDTO = modelMapper.map(reportMember, ReportMemberDTO.class);
        //reportMemberDTO.setReporterMemberId(reportMember.getReporterMember().getId());
        reportMemberDTO.setReporterMemberEmail(reportMember.getReporterMember().getEmail());
        //reportMemberDTO.setReportedMemberId(reportMember.getReportedMember().getId());
        reportMemberDTO.setReportedMemberEmail(reportMember.getReportedMember().getEmail());

        return reportMemberDTO;
    }
}
