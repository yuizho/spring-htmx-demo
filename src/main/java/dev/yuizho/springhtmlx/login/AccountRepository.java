package dev.yuizho.springhtmlx.login;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AccountRepository {
    private final NamedParameterJdbcOperations jdbcTemplate;

    public AccountRepository(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Account> findBy(String username) {
        var sql = """
                SELECT username, password FROM account WHERE username = :username
                """;
        List<Account> accounts = jdbcTemplate.query(
                sql,
                Map.of("username", username),
                new DataClassRowMapper<>(Account.class)
        );
        return accounts.isEmpty() ? Optional.empty() : Optional.of(accounts.get(0));
    }
}
