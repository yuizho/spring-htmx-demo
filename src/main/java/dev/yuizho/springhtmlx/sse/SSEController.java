package dev.yuizho.springhtmlx.sse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/sse")
class SSEController {
    private final SseAsyncTaskService sseService;

    public SSEController(SseAsyncTaskService sseService) {
        this.sseService = sseService;
    }

    @GetMapping
    String index(Model model) {
        return "sse/index";
    }

    @GetMapping("/message")
    SseEmitter message() {
        // timeout to 30 min
        SseEmitter emitter = new SseEmitter(30L * 60L * 1000L);
        sseService.readLongDoc(emitter);
        return emitter;
    }
}