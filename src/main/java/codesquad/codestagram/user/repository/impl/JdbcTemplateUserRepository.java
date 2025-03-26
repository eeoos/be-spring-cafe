package codesquad.codestagram.user.repository.impl;

import codesquad.codestagram.user.domain.User;
import codesquad.codestagram.user.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("seq");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userId", user.getUserId());
        parameters.put("password", user.getPassword());
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setSeq(key.longValue());
        return user;
    }

    @Override
    public Optional<User> findBySeq(Long seq) {
        List<User> result = jdbcTemplate.query("select * from member where seq = ?", userRowMapper(), seq);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from member", userRowMapper());
    }

    @Override
    public User update(User updatedUser) {
        String sql = "update member set userId = ?, password = ?, name = ?, email = ? where seq = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                updatedUser.getUserId(),
                updatedUser.getPassword(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getSeq()
                );
        return (rowsAffected > 0) ? updatedUser: null;
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(
                    rs.getString("userId"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("email")
            );
            user.setSeq(rs.getLong("seq"));
            return user;
        };
    }
}
