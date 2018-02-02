import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.benzoback.library.configuration.AppConfig;
import ru.benzoback.library.dao.BookDao;
import ru.benzoback.library.dao.UserDao;
import ru.benzoback.library.model.Book;
import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;

import java.util.List;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class})
@WebAppConfiguration
public class UserServiceImplTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testCrudOperations() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "books");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");

        //addBook
        Book book = new Book("123", "authorrr","titleee", null);
        Assert.assertTrue(bookDao.addBook(book) == 1);

        //findAllBooks
        List<Book> bookList = bookDao.findAllBooks(0, "", "");
        Assert.assertNotNull(bookList);
        Assert.assertTrue("authorrr".equals(bookList.get(0).getAuthor()));
        Assert.assertTrue(bookList.size() == 1);

        //row count
        int c = JdbcTestUtils.countRowsInTable(jdbcTemplate, "books");
        Assert.assertTrue(c == 1);

        //update
        Book book1 = new Book("1234", "authorrr1", "titleee1", null);
        book1.setId(13);
        Assert.assertTrue(bookDao.updateBook(book1) == 1);

        //findAll again
        bookList = bookDao.findAllBooks(0, "", "");
        Assert.assertNotNull(bookList);
        Assert.assertTrue("authorrr1".equals(bookList.get(0).getAuthor()));
        Assert.assertTrue(bookList.size() == 1);

        //delete
        Assert.assertTrue(bookDao.deleteBook(13) == 1);
        c = JdbcTestUtils.countRowsInTable(jdbcTemplate, "books");
        Assert.assertTrue(c == 0);

        //add again
        Assert.assertTrue(bookDao.addBook(book) == 1);
        c = JdbcTestUtils.countRowsInTable(jdbcTemplate, "books");
        Assert.assertTrue(c == 1);

        //put
        //create user and account
        User user = new User("userName");
        UserAccount userAccount = new UserAccount("login", "password");

        //add user with account for test
        userDao.addUser(user, userAccount);
        //check addition
        c = JdbcTestUtils.countRowsInTable(jdbcTemplate, "users");
        Assert.assertTrue(c == 1);

        //get user from db and book
        List<User> users = userDao.findAllUsers();
        bookList = bookDao.findAllBooks(0, "", "");
        User userTest = users.get(0);
        Book bookTest = bookList.get(0);

        //took book
        Assert.assertTrue(bookDao.takeBook(users.get(0), 14) == 1);
        bookList = bookDao.findAllBooks(0, "", "");
        //check user who took book
        Assert.assertTrue(bookList.get(0).getUser().getId().equals(userTest.getId()));

        //put book
        Assert.assertTrue(bookDao.putBook(users.get(0), 14) == 1);
        bookList = bookDao.findAllBooks(0, "", "");
        //check that book has no owner
        Assert.assertTrue(bookList.get(0).getUser() == null);

        //final clear
        bookDao.deleteBook(14);
        c = JdbcTestUtils.countRowsInTable(jdbcTemplate, "books");
        Assert.assertTrue(c == 0);
    }
}
