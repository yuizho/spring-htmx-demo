package dev.yuizho.springhtmlx.todo.infrastructures;

import dev.yuizho.springhtmlx.todo.domain.Todo;
import dev.yuizho.springhtmlx.todo.domain.TodoRepository;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class JdbcTodoRepository implements TodoRepository {
    private final NamedParameterJdbcOperations jdbcTemplate;

    public JdbcTodoRepository(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Todo> findAll() {
        var sql = """
                SELECT id, name FROM todo
                """;
        return jdbcTemplate.query(
                sql,
                new DataClassRowMapper<>(Todo.class)
        );
    }

    @Override
    public int save(String name) {
        var sql = """
                INSERT INTO todo(name) VALUES(:name)
                """;
        return jdbcTemplate.update(sql, Map.of("name", name));
    }

    @Override
    public int delete(int id) {
        var sql = """
                DELETE FROM todo WHERE id = :id
                """;
        return jdbcTemplate.update(sql, Map.of("id", id));
    }
}
