package dev.yuizho.springhtmlx.chat.configs;

import dev.yuizho.springhtmlx.chat.controllers.MessageHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MessageHandler(), "/ws");
    }
}
