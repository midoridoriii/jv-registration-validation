package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_VALUE = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null!");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_VALUE) {
            throw new RegistrationException("Login length must be greater than 6!");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_VALUE ) {
            throw new RegistrationException("Password length must be greater than 6!");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age must be greater than '18'!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with same login already exists!");
        }
        storageDao.add(user);
        return user;
    }
}
