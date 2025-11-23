package dev.yuizho.springhtmlx.sse;

import com.github.javafaker.Faker;
import dev.yuizho.springhtmlx.infscroll.Feed;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.view.FragmentsRendering;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sse")
class SSEController {
    private final SseService sseService;

    public SSEController(SseService sseService) {
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