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
import ru.benzoback.library.dao.UserAccountDao;
import ru.benzoback.library.dao.UserDao;
import ru.benzoback.library.model.User;
import ru.benzoback.library.model.UserAccount;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
@WebAppConfiguration
public class UserDaoImplTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testCrudOperations() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "books");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user_account");

        //cleas tables
        int rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "books");
        Assert.assertTrue(rowCount == 0);
        rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "users");
        Assert.assertTrue(rowCount == 0);
        rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "user_account");
        Assert.assertTrue(rowCount == 0);

        //create user and account
        User primaryUser = new User("userName");
        UserAccount primaryUserAccount = new UserAccount("login", "password");

        //add user to db
        userDao.addUser(primaryUser, primaryUserAccount);

        //check rows count
        rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "users");
        Assert.assertTrue(rowCount == 1);
        rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "user_account");
        Assert.assertTrue(rowCount == 1);

        //find all users
        List<User> users = userDao.findAllUsers();
        primaryUser = users.get(0);
        Assert.assertTrue(users.size() == 1);

        //find user account by user id
        UserAccount userAccount1 = userAccountDao.findUserAccountByUserId(users.get(0).getId());
        Assert.assertTrue(userAccount1.getUser().getId().equals(users.get(0).getId()));
        primaryUserAccount = userAccount1;


        //addUserAccount and deleteUserAccount must be using only in UserDao
        // ...

        //user from db
        User userFindById = userDao.findById(primaryUser.getId());
        User userFindByLogin = userDao.findByLogin(primaryUserAccount.getLogin());

        Assert.assertTrue(userFindById.getId().equals(userFindByLogin.getId()));
        Assert.assertTrue(userFindById.getName().equals(userFindByLogin.getName()));

        users = userDao.findAllUsers();

        //change user
        User editedUser = new User("editedName");
        editedUser.setId(users.get(0).getId());
        UserAccount editedUserAccount = new UserAccount("editedLogin", "editedPassword");
        editedUserAccount.setUser(editedUser);
        editedUserAccount.setId(users.get(0).getId());

        //update user
        userDao.editUser(editedUser, editedUserAccount);

        users = userDao.findAllUsers();
        userAccount1 = userAccountDao.findUserAccountByUserId(users.get(0).getId());

        Assert.assertTrue(users.get(0).getName().equals("editedName"));
        Assert.assertTrue(userAccount1.getLogin().equals("editedLogin"));
        Assert.assertTrue(userAccount1.getPassword().equals("editedPassword"));
        Assert.assertTrue(userAccount1.getUser().getId().equals(users.get(0).getId()));

        // delete
        userDao.deleteUser(users.get(0).getId());
        rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "users");
        Assert.assertTrue(rowCount == 0);
        rowCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "user_account");
        Assert.assertTrue(rowCount == 0);
    }
}
