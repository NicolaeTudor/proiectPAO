package org.example;

import org.example.Services.UserService;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var _userService = UserService.getInstance();

        var userId = _userService.createUser("Cody");
        //var userId = UUID.fromString("2a3b67b3-85b3-4090-a230-69bbb17277ad");
        var user = _userService.getUser(userId);
        System.out.println("username = " + user.get_username());
    }
}