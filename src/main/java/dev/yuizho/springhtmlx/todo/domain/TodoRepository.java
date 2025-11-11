package dev.yuizho.springhtmlx.todo.domain;

import java.util.List;

public interface TodoRepository {
    List<Todo> findAll();
    int save(String name);
    int delete(int id);
}
