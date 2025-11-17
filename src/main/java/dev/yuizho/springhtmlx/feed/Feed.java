package dev.yuizho.springhtmlx.feed;

public record Feed(
        int id,
        String userName,
        String tittle,
        String contentSummary,
        String createdAt
) {
}
