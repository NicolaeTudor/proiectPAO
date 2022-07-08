package org.example.Services;

import org.example.DataModelAdapter;
import org.example.Enums.ListingStage;
import org.example.Models.Listing;
import org.example.Models.ListingCategory;
import org.example.Models.UserAuthToken;
import org.example.Repositories.ListingCategoryRepository;
import org.example.Repositories.ListingRepository;

import java.util.UUID;

public class ListingService {
    private static volatile ListingService instance;

    private final ListingRepository _listingRepository;
    private final ListingCategoryRepository _listingCategoryRepository;

    public ListingService() {
        _listingRepository = new ListingRepository();
        _listingCategoryRepository = new ListingCategoryRepository();
    }

    public static ListingService getInstance() {
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
        ListingService result = instance;
        if (result != null) {
            return result;
        }
        synchronized(ListingService.class) {
            if (instance == null) instance = new ListingService();
            return instance;
        }
    }

    public ListingCategory getCategory(Integer categoryId) {
        var dbListingCategory = _listingCategoryRepository.Get(categoryId);

        return DataModelAdapter.FromListingCategoryDM(dbListingCategory);
    }

    public ListingCategory createCategory(String categoryName) {
        var dbListingCategory = _listingCategoryRepository.Create(categoryName);

        return DataModelAdapter.FromListingCategoryDM(dbListingCategory);
    }

    public Listing get(UUID listingId) {
        var dbListing = _listingRepository.Get(listingId);

        return DataModelAdapter.FromListingDM(dbListing);
    }

    public Listing create(
            UserAuthToken userAuth,
            String title,
            String description,
            Integer categoryId)
    {
        var _userService = UserService.getInstance();
        var isValidAuth = _userService.checkAuthToken(userAuth);
        if(!isValidAuth)    return null;

        var dbListing = _listingRepository.Create(userAuth.publicKey(), title, description, categoryId);

        return DataModelAdapter.FromListingDM(dbListing);
    }

    public Listing updateStatus(
            UserAuthToken userAuth,
            String title,
            String description,
            Integer categoryId)
    {
        var _userService = UserService.getInstance();
        var isValidAuth = _userService.checkAuthToken(userAuth);
        if(!isValidAuth)    return null;

        var dbListing = _listingRepository.Create(userAuth.publicKey(), title, description, categoryId);

        return DataModelAdapter.FromListingDM(dbListing);
    }
}
