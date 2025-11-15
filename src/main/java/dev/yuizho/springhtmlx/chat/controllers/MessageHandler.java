package dev.yuizho.springhtmlx.chat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MessageHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sessions.putIfAbsent(session.getId(), session);

        var payload = message.getPayload();
        logger.info("Received message: {}", payload);

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
