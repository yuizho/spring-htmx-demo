package dev.yuizho.springhtmlx.incrementalsearch;

import com.github.javafaker.Faker;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/search")
public class SearchController {
    private static final Faker FAKER = new Faker();
    private static final List<Contact> CONTACTS = IntStream.range(0, 100)
            .mapToObj(i -> new Contact(
                            FAKER.name().firstName(),
                            FAKER.name().lastName(),
                            FAKER.internet().emailAddress()
                    )
            )
            .toList();

    @GetMapping
    String search(Model model) {
        model.addAttribute(
                "contacts",
                CONTACTS
        );
        return "incrementalsearch/index";
    }

    @HxRequest
    @GetMapping
    View search(
            @RequestParam String keyword,
            Model model
    ) {

        model.addAttribute(
                "contacts",
                CONTACTS.stream().filter(
                        contact -> contact.firstName().toLowerCase().contains(keyword.toLowerCase())
                                || contact.lastName().toLowerCase().contains(keyword.toLowerCase())
                                || contact.email().toLowerCase().contains(keyword.toLowerCase())
                ).toList()
        );

        return FragmentsRendering
                .with("incrementalsearch/index :: contacts")
                .build();
    }
}
