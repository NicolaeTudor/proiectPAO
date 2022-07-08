package org.example;

import org.example.Services.ListingService;
import org.example.Services.UserService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        var _userService = UserService.getInstance();
        var _listingService = ListingService.getInstance();

        var createReturn = _userService.create("Farfa");

        var user = _userService.get(createReturn.CreatedUser().get_publicKey());

        System.out.println("username = " + user.get_username());

        var createdCategory = _listingService.createCategory("Games");
        var getCategory = _listingService.getCategory(createdCategory.listingCategoryId());

        System.out.println("category name = " + getCategory.name());
    }
}