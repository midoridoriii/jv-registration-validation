package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALUE = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null!");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_VALUE) {
            throw new RegistrationException("Login length must be at least 6 characters!");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_VALUE) {
            throw new RegistrationException("Password length must be at least 6 characters!");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age must be at least 18!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with same login already exists!");
        }
        storageDao.add(user);
        return user;
    }
}
