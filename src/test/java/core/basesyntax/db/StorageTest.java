package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void get_notExistingLogin_returnsNull_OK() {
        assertNull(storageDao.get("notExistingLogin"));
    }

    @Test
    void add_validUser_userIsStored_OK() {
        User user = new User("123456", "123456", 20);
        storageDao.add(user);
        User actual = storageDao.get("123456");
        assertNotNull(actual);
    }
}
