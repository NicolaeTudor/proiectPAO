package org.example.Models;

import java.util.UUID;

public class User {
    private final String _username;
    private final UUID _publicKey;
    public User(String username, UUID publicKey) {
        _username = username;
        _publicKey = publicKey;
    }

    public String get_username() {
        return _username;
    }

    public UUID get_publicKey() {
        return _publicKey;
    }
}
