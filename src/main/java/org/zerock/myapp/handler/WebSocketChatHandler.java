package org.zerock.myapp.handler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.zerock.myapp.domain.MessageDTO;
import org.zerock.myapp.entity.Message;
import org.zerock.myapp.service.MessageService;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor

@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
	
	@Autowired private final MessageService messageService;
	
    private final Map<Long, Set<WebSocketSession>> chatRoomSessions = new ConcurrentHashMap<>();
    
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
    	log.debug("afterConnectionEstablished({}) invoked.", session);
    	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 1. messageService:{}, this:{}", this.messageService, this);
    	
        // 쿼리파라미터에서 채팅방 ID 추출
        Long chatId = getChatIdFromSession(session);
        chatRoomSessions.computeIfAbsent(chatId, k -> ConcurrentHashMap.newKeySet()).add(session);
    } // afterConnectionEstablished

    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)  {
    	log.debug("handleTextMessage({}, {}) invoked.", session, message);
    	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 2. messageService:{}, this:{}", this.messageService, this);
    	
    	 try {
    	        // 1. JSON 문자열을 MessageDTO 객체로 변환
    	        ObjectMapper objectMapper = new ObjectMapper();
    	        MessageDTO messageDTO = objectMapper.readValue(message.getPayload(), MessageDTO.class);

    	        // 2. chatId 추출 (쿼리에서 파싱하거나 DTO에 포함되어 있어야 함)
    	        Long chatId = getChatIdFromSession(session);

    	        // 3. 메시지 저장
    	        Message savedMessage = messageService.saveMessage(messageDTO); // 저장 후 DTO 반환

    	        // 4. 다시 JSON으로 변환
    	        String savedJson = objectMapper.writeValueAsString(savedMessage);

    	        // 5. 해당 채팅방 모든 세션에 브로드캐스트
    	        Set<WebSocketSession> sessions = chatRoomSessions.get(chatId);
    	        if (sessions != null) {
    	            for (WebSocketSession s : sessions) {
    	                s.sendMessage(new TextMessage(savedJson));
    	            }
    	        }
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    } // handleTextMessage

    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    	log.debug("afterConnectionClosed({}, {}) invoked.", session, status);
    	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 3. messageService:{}, this:{}", this.messageService, this);
    	
        Long chatId = getChatIdFromSession(session);
        Set<WebSocketSession> sessions = chatRoomSessions.get(chatId);
        if (sessions != null) {
            sessions.remove(session);
        }
    } // afterConnectionClosed

    
    private Long getChatIdFromSession(WebSocketSession session) {
    	log.debug("getChatIdFromSession({}) invoked.", session);
    	
        String query = session.getUri().getQuery(); // 예: "chatId=3"

        if (query == null || !query.startsWith("chatId=")) {
            throw new IllegalArgumentException("chatId 파라미터가 유효하지 않습니다: " + query);
        }

        try {
            return Long.parseLong(query.split("=")[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("chatId가 숫자가 아닙니다: " + query);
        }
    }// getChatIdFromSession
    
} // end class