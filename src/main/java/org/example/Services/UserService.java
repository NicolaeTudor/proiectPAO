package org.example.Services;

import org.example.DataModelAdapter;
import org.example.Models.User;
import org.example.Repositories.UserRepository;

import java.util.UUID;

public final class UserService {
    private static volatile UserService instance;

    private final UserRepository _userRepository;
    private UserService() {
        _userRepository = new UserRepository();
    }

    public static UserService getInstance() {
        // The approach taken here is called double-checked locking (DCL). It
        // exists to prevent race condition between multiple threads that may
        // attempt to get singleton instance at the same time, creating separate
        // instances as a result.
        //
        // It may seem that having the `result` variable here is completely
        // pointless. There is, however, a very important caveat when
        // implementing double-checked locking in Java, which is solved by
        // introducing this local variable.
        //
        // You can read more info DCL issues in Java here:
        // https://refactoring.guru/java-dcl-issue
        UserService result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UserService.class) {
            if (instance == null) instance = new UserService();
            return instance;
        }
    }

    public User getUser(UUID publicKey) {
        var dbUser =  _userRepository.Get(publicKey);

        return DataModelAdapter.FromUserDM(dbUser);
    }

    public UUID createUser(String username) {
        var dbUser = _userRepository.Create(username);

        return dbUser.userId();
    }
}
