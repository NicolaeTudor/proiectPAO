package org.example;

import org.example.DataModels.ListingCategoryDM;
import org.example.DataModels.ListingDM;
import org.example.DataModels.UserDM;
import org.example.Models.Listing;
import org.example.Models.ListingCategory;
import org.example.Models.User;

public class DataModelAdapter {
    public static User FromUserDM(UserDM dbUser) {
        return new User(
                dbUser.name(),
                dbUser.userId()
        );
    }

    public static ListingCategory FromListingCategoryDM(ListingCategoryDM dbListingCategory) {
        return new ListingCategory(
                dbListingCategory.listingCategoryId(),
                dbListingCategory.name()
        );
    }

    public static Listing FromListingDM(ListingDM dbListing) {
        return new Listing(
                dbListing.listingId(),
                dbListing.userId(),
                dbListing.title(),
                dbListing.description(),
                dbListing.stage(),
                dbListing.category());
    }
}
