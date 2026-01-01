package dev.yuizho.springhtmlx.login;

import com.github.javafaker.Faker;
import dev.yuizho.springhtmlx.feed.Feed;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/login")
class LoginController {
    private static final Faker FAKER = new Faker();
    private static final List<String> users = new CopyOnWriteArrayList<>(
            IntStream.range(0, 3)
                    .mapToObj(i -> FAKER.name().fullName())
                    .toList()
    );

    @GetMapping("/home")
    String feed(Model model) {
        return "login/index";
    }

    @HxRequest
    @GetMapping("/users")
    View users(
            Model model,
            HtmxResponse htmxResponse
    ) {
        model.addAttribute("users", users);
        return FragmentsRendering
                .with("login/index :: users")
                .build();
    }

    @HxRequest
    @PostMapping("/user")
    View addUser(Model model,
                 HtmxResponse htmxResponse
    ) {
        users.add(FAKER.name().fullName());

        model.addAttribute("users", users);
        return FragmentsRendering
                .with("login/index :: users")
                .build();
    }
}