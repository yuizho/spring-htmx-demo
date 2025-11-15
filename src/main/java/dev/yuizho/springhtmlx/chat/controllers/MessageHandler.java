package dev.yuizho.springhtmlx.chat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MessageHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    private ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.putIfAbsent(session.getId(), session);
        var response = new TextMessage(
                String.format(
                        "<ul hx-swap-oob=\"beforeend:#histories\"><li>%sが入出しました！</li></ul>",
                        session.getId()
                )
        );

        // ブロードキャスティング
        for (var s : sessions.values()) {
            if (s.isOpen()) {
                s.sendMessage(response);
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        var payload = message.getPayload();
        logger.info("Received message: {}", payload);

        var objectMapper = new ObjectMapper();
        var jsonNode = objectMapper.readTree(payload);
        var content = jsonNode.get("message").asText();
        var response = new TextMessage(
                String.format(
                        """
                                <ul hx-swap-oob=\"beforeend:#histories\"><li>%sからメッセージが届きました！</li></ul>
                                
                                <ul hx-swap-oob=\"beforeend:#messages\"><li>%s: %s</li></ul>
                                """,
                        session.getId(),
                        session.getId(),
                        HtmlUtils.htmlEscape(content)
                )
        );

        // ブロードキャスティング
        for (var s : sessions.values()) {
            if (s.isOpen()) {
                s.sendMessage(response);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        var response = new TextMessage(
                String.format(
                        "<ul hx-swap-oob=\"beforeend:#histories\"><li>%sが退出しました！</li></ul>",
                        session.getId()
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
