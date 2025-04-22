package org.zerock.myapp.handler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.zerock.myapp.domain.MessageDTO;
import org.zerock.myapp.entity.Employee;
import org.zerock.myapp.entity.Message;
import org.zerock.myapp.persistence.EmployeeRepository;
import org.zerock.myapp.service.MessageService;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
    
	@Autowired private ObjectMapper objectMapper;
    @Autowired private final MessageService messageService;
    @Autowired private EmployeeRepository employeeRepository;

    private final Map<Long, Set<WebSocketSession>> chatRoomSessions = new ConcurrentHashMap<>();

    private final Map<String, Set<WebSocketSession>> userSessions = new ConcurrentHashMap<>();
    
    public Map<Long, Set<WebSocketSession>> getChatRoomSessions() {
        return this.chatRoomSessions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("afterConnectionEstablished({}) invoked.", session);
        
        Long chatId = getChatIdFromSession(session);
        String empno = getEmpnoFromSession(session); // 새로 추가

        // 채팅방 세션 등록
        chatRoomSessions.computeIfAbsent(chatId, k -> ConcurrentHashMap.newKeySet()).add(session);
        
        // 사용자 세션 관리
        userSessions.computeIfAbsent(empno, k -> ConcurrentHashMap.newKeySet()).add(session);
    }// afterConnectionEstablished

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.debug("handleTextMessage({}, {}) invoked.", session, message);
        
        try {
            // 1. JSON 문자열을 MessageDTO 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            MessageDTO messageDTO = objectMapper.readValue(message.getPayload(), MessageDTO.class);
            
            // 2. chatId 추출 (쿼리에서 파싱하거나 DTO에 포함되어 있어야 함)
            Long chatId = getChatIdFromSession(session);
            
            // empno로 사원 정보 가져오기
            Optional<Employee> optEmployee = employeeRepository.findById(messageDTO.getEmpno());
            if (optEmployee.isEmpty()) {
                throw new RuntimeException("사원 정보 없음: " + messageDTO.getEmpno());
            }
            Employee employee = optEmployee.get();

            //DTO에 사원 정보 넣기
            messageDTO.setEmployee(employee);
            
            // 3. 메시지 타입에 따른 처리
            switch (messageDTO.getType()) {
            case "INVITE":
                List<String> invitedEmpnos = messageDTO.getInvitedEmpnos();
                if (invitedEmpnos == null || invitedEmpnos.isEmpty()) {
                    throw new IllegalArgumentException("초대 대상자가 지정되지 않았습니다");
                }

                for (String targetEmpno : invitedEmpnos) {
                    try {
                        Employee target = employeeRepository.findById(targetEmpno)
                            .orElseThrow(() -> new NoSuchElementException("사원 정보 없음: " + targetEmpno));

                        MessageDTO inviteMsg = new MessageDTO();
                        inviteMsg.setType("INVITE");
                        inviteMsg.setChatId(messageDTO.getChatId());
                        inviteMsg.setDetail(target.getName() + " 님을 초대했습니다"); // content로 변경
                        inviteMsg.setEmpno(target.getEmpno()); // 객체 대신 empno만

                        sendToUser(targetEmpno, inviteMsg);
                    } catch (Exception e) {
                        log.error("{} 초대 실패: {}", targetEmpno, e.getMessage());
                    }
                }

                MessageDTO systemMsg = new MessageDTO();
                systemMsg.setType("SYSTEM");
                systemMsg.setDetail(invitedEmpnos.size() + "명이 초대되었습니다"); //  content
                systemMsg.setChatId(messageDTO.getChatId());
                log.debug("브로드캐스트 메시지: {}", systemMsg.getDetail());
                broadcastToChatRoom(chatId, systemMsg);

                break;
                
                case "LEAVE":
                    // 퇴장 메시지 처리
                	String empno = messageDTO.getEmpno();
            	    Long chatRoomId = messageDTO.getChatId();
            	    
            	    // 1. 세션 제거
            	    chatRoomSessions.getOrDefault(chatRoomId, Collections.emptySet())
            	        .removeIf(s -> {
            	            try {
            	                return getEmpnoFromSession(s) == empno;
            	            } catch (Exception e) {
            	                return false;
            	            }
            	        });
            	    
            	    // 2. 시스템 알림
            	    sendSystemMessage(chatId, 
            	        messageDTO.getEmployee().getName() + " 님이 퇴장하셨습니다", 
            	        "LEAVE");
            	    break;
            	    
                case "MESSAGE":
                    // 일반 채팅 메시지 처리
                    broadcastToChatRoom(chatId, messageDTO);
                    break;
                default:
                    log.warn("알 수 없는 메시지 타입: {}", messageDTO.getType());
            }
            Message savedMessage = this.messageService.saveMessage(messageDTO);
        } catch (IOException e) {
            log.error("메시지 처리 중 오류 발생", e);
        }
    } // handleTextMessage

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("afterConnectionClosed({}, {}) invoked.", session, status);

        Long chatId = getChatIdFromSession(session);
        Set<WebSocketSession> sessions = chatRoomSessions.get(chatId);
        if (sessions != null) {
            sessions.remove(session);
        }
    } // afterConnectionClosed

    private Long getChatIdFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        
        if (query == null || !query.contains("chatId=")) {
            throw new IllegalArgumentException("chatId 파라미터가 존재하지 않습니다: " + query);
        }
        
        try {
            return Arrays.stream(query.split("&"))
                        .filter(param -> param.startsWith("chatId="))
                        .findFirst()
                        .map(param -> Long.parseLong(param.split("=")[1]))
                        .orElseThrow(() -> new IllegalArgumentException("chatId 파라미터 누락"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("chatId는 숫자 형식이어야 합니다: " + query);
        }
    }// getChatIdFromSession

    
    public void broadcastToChatRoom(Long chatId, MessageDTO messageDTO) {
        Set<WebSocketSession> sessions = chatRoomSessions.get(chatId);
        if (sessions != null) {
            try {
                String messageJson = new ObjectMapper().writeValueAsString(messageDTO);
                for (WebSocketSession session : sessions) {
                    session.sendMessage(new TextMessage(messageJson));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } // try
        } // if
    }// broadcastToChatRoom
    
    
    private void sendToUser(String empno, MessageDTO messageDTO) {
        userSessions.getOrDefault(empno, Collections.emptySet())
            .forEach(session -> {
                if(session.isOpen()) {
                    try {
                        String json = objectMapper.writeValueAsString(messageDTO);
                        session.sendMessage(new TextMessage(json));
                    } catch (IOException e) {
                        log.error("메시지 전송 실패: {}", e.getMessage());
                    }// try
                }// if
            });// forEach
    }// sendToUser
    
    
    private void sendSystemMessage(Long chatId, String detail, String type) {
        MessageDTO systemMsg = new MessageDTO();
        systemMsg.setType(type);
        systemMsg.setChatId(chatId);
        systemMsg.setDetail(detail);
        
        broadcastToChatRoom(chatId, systemMsg);
    }// sendSystemMessage
    
    private String getEmpnoFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        
        if (query == null || !query.contains("empno=")) {
            throw new IllegalArgumentException("empno 파라미터가 존재하지 않습니다: " + query);
        }
        
        return Arrays.stream(query.split("&"))
                     .filter(param -> param.startsWith("empno="))
                     .findFirst()
                     .map(param -> param.split("=")[1])
                     .orElseThrow(() -> 
                         new IllegalArgumentException("empno 파라미터 형식이 잘못되었습니다"));
    }// getEmpnoFromSession
    
}
