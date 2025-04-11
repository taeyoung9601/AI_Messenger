//package org.zerock.myapp.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//import lombok.RequiredArgsConstructor;
//
//@Configuration
////@EnableWebSocket
//@RequiredArgsConstructor
//public class WebSocketConfig implements WebSocketConfigurer {
//
//	@Autowired
//    private final WebSocketHandler webSocketHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//
//        registry.addHandler(webSocketHandler, "/chatroom/room1").setAllowedOrigins("*");
//    }
//}