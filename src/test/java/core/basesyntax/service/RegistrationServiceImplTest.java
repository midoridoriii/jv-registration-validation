package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void registerValidUser_Ok() {
        User user = new User("123456", "123456", 18);
        registrationService.register(user);
        User actual = storageDao.get("123456");
        assertNotNull(actual);
        assertEquals("123456", actual.getLogin());
        assertEquals("123456", actual.getPassword());
        assertEquals(18, actual.getAge());
    }

    @Test
    public void loginAlreadyExist_NotOk() {
        User existingUser = new User("123456", "123456", 18);
        storageDao.add(existingUser);
        User newUser = new User("123456", "123456", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void loginLessThan6Symbols_NotOk() {
        User actual = new User("12345", "123456", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void passwordLessThan6Symbols_NotOk() {
        User actual = new User("123456", "12345", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void ageLessThan18_NotOk() {
        User actual =  new User("123456", "123456", 15);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void userIsNull_NotOk() {
        User actual = new User(null, null, null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void loginIsNull_NotOk() {
        User actual = new User(null, "123456", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void passwordIsNull_NotOk() {
        User actual = new User("123456", null, 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void ageIsNull_NotOk() {
        User actual = new User("123456", "123456", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void storageIsNotEmpty_Ok() {
        User validUser = new User("123456", "123456", 18);
        registrationService.register(validUser);
        User actual = storageDao.get(validUser.getLogin());
        assertNotNull(actual);
    }

    @Test
    void storageIsEmpty_Ok() {
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void userGetsIdAfterRegistration_OK() {
        User user = new User("123456", "123456", 20);
        registrationService.register(user);
        assertNotNull(user.getId());
        assertTrue(user.getId() > 0);
    }

    @Test
    void registerReturnsSameUserInstance_OK() {
        User user = new User("123456", "123456", 20);
        User returned = registrationService.register(user);
        assertSame(user, returned);
    }

    @Test
    void getUnknownLogin_ReturnsNull_OK() {
        assertNull(storageDao.get("notExistingLogin"));
    }

    @Test
    void registeringTwoUsers_OK() {
        User user1 = new User("login1", "password1", 18);
        User user2 = new User("login2", "password2", 18);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(2, Storage.people.size());
        assertNotNull(storageDao.get("login1"));
        assertNotNull(storageDao.get("login2"));
    }
}