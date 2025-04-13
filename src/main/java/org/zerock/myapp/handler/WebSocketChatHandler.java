package org.zerock.myapp.handler;

//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor

public class WebSocketChatHandler extends TextWebSocketHandler {
	
	// 채팅방 ID에 연결된 세션들
	private Map<Long, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>();
	
	// 웹 소켓을 이용해서, 클라이언트와 서버의 접속포인트(endpoint) 간에
	// 연결이 생성된 직후 자동 호출되는 콜백 메소드
	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug(">>> WebSocket 연결됨: {}", session);

        // 채팅방 ID 파싱 (예: /chatroom/3 → 3)
        Long chatId = extractChatId(session);
        if (chatId != null) {
            chatRooms.computeIfAbsent(chatId, k -> ConcurrentHashMap.newKeySet()).add(session);
            log.info("채팅방 {}에 접속: {}", chatId, session.getId());
        }
    }
	
	// 웹 소켓을 이용해서, 클라이언트와 서버의 접속포인트(endpoint) 간에
	// 연결이 닫힌 직후 자동 호출되는 콜백 메소드
	@Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long chatId = extractChatId(session);
        if (chatId != null) {
            Set<WebSocketSession> sessions = chatRooms.get(chatId);
            if (sessions != null) {
                sessions.remove(session);
                log.info("채팅방 {}에서 연결 종료: {}", chatId, session.getId());
                if (sessions.isEmpty()) {
                    chatRooms.remove(chatId);
                }
            }
        }
    }// afterConnectionClosed

	// 웹 소켓을 이용해서, 클라이언트로부터 텍스트 메시지가 수신되었을 때
	// 연결이 생성된 직후 자동 호출되는 콜백 메소드	
	@Override
	   public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	       Long chatId = extractChatId(session);
	       if (chatId != null) {
	           Set<WebSocketSession> sessions = chatRooms.get(chatId);
	           if (sessions != null) {
	               for (WebSocketSession s : sessions) {
	                   if (s.isOpen()) {
	                       s.sendMessage(message);
	                   }
	               }
	           }
	       }
	   } // handleTextMessage

	// 웹 소켓 연결 주소
	private Long extractChatId(WebSocketSession session) {
        try {
            String path = session.getUri().getPath(); // 예: /chatroom/3
            String[] parts = path.split("/");
            return Long.parseLong(parts[parts.length - 1]);
        } catch (Exception e) {
            log.error("채팅방 ID 추출 실패", e);
            return null;
        }
    } // extractChatId
	
	

}// end class