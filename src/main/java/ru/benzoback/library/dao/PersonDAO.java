package ru.benzoback.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class PersonDAO {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

//    public List<Person> getPersons() {
//        return jdbcTemplate.query("SELECT * FROM person",
//                (ResultSet rs, int rowNum) -> {
//                    Person p = new Person();
//                    p.setId(rs.getInt("id"));
//                    p.setFirstName(rs.getString("first_name"));
//                    p.setLastName(rs.getString("last_name"));
//                    p.setDateOfBirth(rs.getDate("date_of_birth"));
//                    p.setPlaceOfBirth(rs.getString("place_of_birth"));
//                    return p;
//                });
//    }

    public final static RowMapper<User> userMapper = BeanPropertyRowMapper.newInstance(User.class);
    public final static RowMapper<Book> bookMapper = BeanPropertyRowMapper.newInstance(Book.class);

    public User findUser(Integer userId){
        return jdbcTemplate.query("SELECT * FROM users", new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                return null;
            }
        });
    }
}
