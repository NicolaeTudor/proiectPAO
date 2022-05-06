package org.example;

import org.example.DataModels.UserDM;
import org.example.Models.User;

public class DataModelAdapter {
    public static User FromUserDM(UserDM dbUser) {
        return new User(dbUser.name(), dbUser.userId());
    }
}
