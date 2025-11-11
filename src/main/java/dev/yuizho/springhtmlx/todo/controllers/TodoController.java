package dev.yuizho.springhtmlx.todo.controllers;

import dev.yuizho.springhtmlx.todo.applications.TodoService;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.FragmentsRendering;

@Controller
@RequestMapping("/todo")
class TodoController {
    private final TodoService todoService;

    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    String todo(Model model) {
        model.addAttribute(
                "todoList",
                todoService.findAll()
        );
        return "todo/index";
    }

    @HxRequest
    @PostMapping
    View add(@RequestParam("new-todo") String newTodo,
             Model model,
             HtmxResponse htmxResponse
                     ) {
        todoService.register(newTodo);
        model.addAttribute(
                "todoList",
                todoService.findAll()
        );

        return FragmentsRendering
                .with("todo/index :: todo-form")
                .fragment("todo/index :: todo-list")
                .build();
    }

    @DeleteMapping(produces = MediaType.TEXT_HTML_VALUE, path = "/{todoId}")
    @ResponseBody
    String delete(@PathVariable int todoId) {
        todoService.delete(todoId);
        return "";
    }
}
