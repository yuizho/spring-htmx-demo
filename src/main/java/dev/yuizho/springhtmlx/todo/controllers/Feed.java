package dev.yuizho.springhtmlx.todo.controllers;

import java.time.LocalDateTime;

public record Feed(
        int id,
        String userName,
        String tittle,
        String contentSummary,
        String createdAt
) {
}
