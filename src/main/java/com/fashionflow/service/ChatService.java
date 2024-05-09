package com.fashionflow.service;

import com.fashionflow.dto.chat.ChatMessageDTO;
import com.fashionflow.entity.ChatMessage;
import com.fashionflow.entity.ChatRoom;
import com.fashionflow.repository.ChatMessageRepository;
import com.fashionflow.repository.ChatRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessageDTO> getChatHistory(String uuid){
        ChatRoom chatRoom = chatRoomRepository.findByUuid(uuid).orElseThrow(() ->
                new EntityNotFoundException("채팅방이 존재하지 않습니다. uuid = " + uuid));

        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatRoomIdOrderByIdAsc(chatRoom.getId());

        List<ChatMessageDTO> chatHistory = new ArrayList<>();

        for(ChatMessage chatMessage : chatMessageList){
            ChatMessageDTO chatMessageDTO = ChatMessageDTO.entityToDTO(chatMessage);
            chatHistory.add(chatMessageDTO);
        }

        return chatHistory;
    }
}
