package dev.yuizho.springhtmlx.polling.controllers;


import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/polling")
class PollingController {
    @GetMapping
    String index(Model model) {
        model.addAttribute(
                "now",
                LocalDateTime.now().toString()
        );
        return "polling/index";
    }

    @HxRequest // Hx-RquestHeaderがついてるリクエストのみ処理する。それ以外は404
    @GetMapping("/now")
    View now(
            Model model,
            HtmxResponse htmxResponse
    ) {
        model.addAttribute(
                "now",
                LocalDateTime.now().toString()
        );

        return FragmentsRendering
                .with("polling/index :: now")
                .build();
    }
}