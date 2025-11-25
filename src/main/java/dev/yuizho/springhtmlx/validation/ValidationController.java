package dev.yuizho.springhtmlx.validation;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.ConstraintViolationException;

@Validated
@Controller
@RequestMapping("/validation")
class ValidationController {
    static private final Logger LOGGER = LoggerFactory.getLogger(ValidationController.class);

    @GetMapping
    String index(Model model) {
        return "validation/index";
    }

    @HxRequest
    @PostMapping
    View post(@Size(min = 1, max = 20) @RequestParam("name") String name,
              Model model) {
        LOGGER.info("name: {}", name);

        model.addAttribute(
                "message",
                "Hello " + name + "!"
        );
        return FragmentsRendering
                .with("validation/index :: message")
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    View handleConstraintViolationException(ConstraintViolationException e, Model model) {
        model.addAttribute("message", e.getConstraintViolations().iterator().next().getMessage());
        return FragmentsRendering
                .with("validation/index :: message")
                .build();
    }
}