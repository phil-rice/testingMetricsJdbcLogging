package org.invertthepyramid;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.function.Function;

class JdbcListGetter<From, To> implements Function<From, List<To>> {
    public JdbcTemplate jdbcTemplate;
    public String sql;
    public Function<From, Object[]> params;
    public RowMapper<To> rowMapper;

    public JdbcListGetter(JdbcTemplate jdbcTemplate, String sql, Function<From, Object[]> params, RowMapper<To> rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.sql = sql;
        this.params = params;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<To> apply(From from) {
        return jdbcTemplate.query(sql, params.apply(from), rowMapper);
    }
}
