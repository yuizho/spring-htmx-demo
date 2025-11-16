package dev.yuizho.springhtmlx.feed;

import com.github.javafaker.Faker;
import dev.yuizho.springhtmlx.todo.controllers.Feed;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/feed")
class FeedController {
    private static final Faker FAKER = new Faker();
    private static final List<Feed> FEEDS = IntStream.range(0, 100)
            .mapToObj(i -> new Feed(
                    i,
                    FAKER.name().fullName(),
                    FAKER.lorem().sentence(),
                    FAKER.lorem().paragraph(),
                    SimpleDateFormat.getDateTimeInstance().format(FAKER.date().birthday()))
            )
            .toList();

    @GetMapping
    String feed(Model model) {
        model.addAttribute(
                "feeds",
                FEEDS.subList(0, 10)
        );
        model.addAttribute(
                "page",
                2
        );
        return "feed/index";
    }

    @HxRequest
    @GetMapping("/{page}")
    View feed(
            @PathVariable int page,
            Model model,
            HtmxResponse htmxResponse
    ) throws InterruptedException {
        Thread.sleep(1000);

        int to = page * 10;
        model.addAttribute(
                "feeds",
                FEEDS.subList(0, to)
        );
        model.addAttribute(
                "page",
                page + 1
        );

        return FragmentsRendering
                .with("feed/index :: feeds")
                .fragment("feed/index :: load")
                .build();
    }
}