package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void register_userWithExistingLogin_throwsException() {
        User existingUser = new User("123456", "123456", 18);
        storageDao.add(existingUser);
        User newUser = new User("123456", "123456", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
    }

    @Test
    void register_loginTooShort_throwsException() {
        User actual = new User("login", "123456", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_passwordTooShort_throwsException() {
        User actual = new User("123456", "12345", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_ageBelow18_throwsException() {
        User actual = new User("123456", "123456", 15);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_nullUser_throwsException() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_throwsException() {
        User actual = new User(null, "123456", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_nullPassword_throwsException() {
        User actual = new User("123456", null, 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }

    @Test
    void register_nullAge_throwsException() {
        User actual = new User("123456", "123456", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(actual));
    }
}
