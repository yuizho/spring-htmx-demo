package dev.yuizho.springhtmlx.infscroll;

import com.github.javafaker.Faker;
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
@RequestMapping("/infscroll")
class InfScrollController {
    private static final Faker FAKER = new Faker();
    private static final List<Feed> FEEDS = IntStream.range(0, 10000)
            .mapToObj(i -> new Feed(
                    i,
                    FAKER.name().fullName(),
                    FAKER.lorem().sentence(),
                    SimpleDateFormat.getDateTimeInstance().format(FAKER.date().birthday()))
            )
            .toList();

    @GetMapping
    String feed(Model model) {
        model.addAttribute(
                "feeds",
                FEEDS.subList(0, 5)
        );
        model.addAttribute(
                "page",
                2
        );
        return "infscroll/index";
    }

    @HxRequest
    @GetMapping("/{page}")
    View feed(
            @PathVariable int page,
            Model model,
            HtmxResponse htmxResponse
    ) throws InterruptedException {
        Thread.sleep(1000);

        int from = (page - 1) * 5;
        int to = Math.min(page * 5, FEEDS.size());

        if (from >= FEEDS.size()) {
            // empty response to stop infinite scroll
            return FragmentsRendering.with("").build();
        }

        model.addAttribute(
                "feeds",
                FEEDS.subList(from, to)
        );
        model.addAttribute(
                "page",
                page + 1
        );

        return FragmentsRendering
                .with("infscroll/index :: feed-row")
                .build();
    }
}