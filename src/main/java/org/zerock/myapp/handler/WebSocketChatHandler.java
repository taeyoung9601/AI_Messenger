package org.zerock.myapp.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.zerock.myapp.domain.MessageDTO;
import org.zerock.myapp.service.MessageService;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor

public class WebSocketChatHandler extends TextWebSocketHandler {
    @Autowired MessageService messageService;
	
	private final ObjectMapper mapper;
    

    // 현재 연결된 세션들
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // chatRoomId: {session1, session2} , 채팅방 당 연결된 세션을 담음
    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결됨", session.getId());
        this.sessions.add(session);
    }

    // 소켓 통신 시 메세지의 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        // 페이로드 -> chatMessageDto로 변환
        MessageDTO messageDto = mapper.readValue(payload, MessageDTO.class);
        log.info("session {}", messageDto.toString());

        Long chatRoomId = messageDto.getChat().getId();
        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        if(!chatRoomSessionMap.containsKey(chatRoomId)){
            chatRoomSessionMap.put(chatRoomId,new HashSet<>());
        }
        
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);
        
        messageService.saveMessage(messageDto);
        
        // 방 참가 등록
        chatRoomSession.add(session);

        // 닫힌 세션 정리
        removeClosedSession(chatRoomSession);

        // 메시지 전송
        sendMessageToChatRoom(messageDto, chatRoomSession);

    }

    // 소켓 종료 확인
    @Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("afterConnectionClosed({},{}) invoked.", session, status);
		
		this.sessions.remove(session);
		this.sessions.forEach(s-> log.info("\t + Active Session:{}", s));
	}//afterConnectionClosed

    // ====== 채팅 관련 메소드 ======
    
    // 연결이 끊어진 세션 삭제
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !this.sessions.contains(sess));
    }

    
    // 세션에게 메세지 전송
    private void sendMessageToChatRoom(MessageDTO messageDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, messageDto));//2
    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    
}
