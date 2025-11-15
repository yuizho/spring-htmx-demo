package dev.yuizho.springhtmlx.chat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MessageHandler extends TextWebSocketHandler {
    ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sessions.putIfAbsent(session.getId(), session);

        var payload = message.getPayload();
        System.out.println("Received message: " + payload);

        var objectMapper = new ObjectMapper();
        var jsonNode = objectMapper.readTree(payload);
        var content = jsonNode.get("message").asText();
        var response = new TextMessage(
                // FIXME: needs xss prevention
                String.format(
                        """
                                <ul hx-swap-oob=\"beforeend:#histories\"><li>%sからメッセージが届きました！</li></ul>
                                
                                <ul hx-swap-oob=\"beforeend:#messages\"><li>%s</li></ul>
                                """,
                        session.getId(),
                        content
                )
        );

        // ブロードキャスティング
        for (var s : sessions.values()) {
            if (s.isOpen()) {
                s.sendMessage(response);
            }
        }
    }
}
