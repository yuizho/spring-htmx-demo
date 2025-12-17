package dev.yuizho.springhtmlx.boost;

import com.github.javafaker.Faker;
import dev.yuizho.springhtmlx.feed.Feed;
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
@RequestMapping("/boost")
class BoostController {
    @GetMapping
    String inext() {
        return "boost/index";
    }

    @GetMapping("/next")
    String next() {
        return "boost/next";
    }
}