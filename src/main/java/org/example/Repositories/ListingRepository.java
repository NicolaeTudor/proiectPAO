package org.example.Repositories;

import org.example.DataModels.ListingCategoryDM;
import org.example.DataModels.ListingDM;
import org.example.DataModels.UserDM;
import org.example.Enums.ListingStage;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class ListingRepository extends BaseRepository{
    public ListingDM Get(UUID listingId) {
        try {
            String query =
                    """
                            select BIN_TO_UUID(listingId) as listingId, BIN_TO_UUID(userId) as userId, title, description, stage, categoryId
                            from listings
                            where listingId = UUID_TO_BIN(?)
                    """;

            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setString (1, listingId.toString());

            ResultSet rs = preparedStmt.executeQuery();

            rs.next();
            return new ListingDM(
                    UUID.fromString(rs.getString("listingId")),
                    UUID.fromString(rs.getString("ownerId")),
                    rs.getString("title"),
                    rs.getString("description"),
                    ListingStage.fromInteger(rs.getInt("stage")),
                    rs.getInt("categoryId")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ListingDM Create(UUID ownerId, String title, String description, int categoryId) {
        try {
            var listingId = UUID.randomUUID();
            String query =
                    """
                            insert into listings (listingId, userId, title, description, stage, categoryId)
                            values (UUID_TO_BIN(?), UUID_TO_BIN(?), ?, ?, ?, ?)
                    """;

            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setString (1, ownerId.toString());
            preparedStmt.setString (2, UUID.randomUUID().toString());
            preparedStmt.setString (3, title);
            preparedStmt.setString (4, description);
            preparedStmt.setInt (5, ListingStage.Draft.getValue());
            preparedStmt.setInt (5, categoryId);

            preparedStmt.execute();

            return Get(listingId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ListingDM Update(UUID listingId, Optional<UUID> ownerId, Optional<String> title, Optional<String> description, Optional<Integer> categoryId, Optional<ListingStage> stage) {
        try {
            String query =
                    """
                            update listings
                            set
                    """;

            if(ownerId.isPresent()) query += " userId = ?";
            if(title.isPresent()) query += " title = ?";
            if(description.isPresent()) query += " description = ?";
            if(categoryId.isPresent()) query += " categoryId = ?";
            if(stage.isPresent()) query += " stage = ?";

            query += "where listingId = UUID_TO_BIN(?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);

            int idx = 1;

            if(ownerId.isPresent()) {
                preparedStmt.setString (idx, ownerId.get().toString());
                idx+=1;
            }
            if(title.isPresent()) {
                preparedStmt.setString (idx, title.get());
                idx+=1;
            }
            if(description.isPresent()) {
                preparedStmt.setString (idx, description.get());
                idx+=1;
            }
            if(categoryId.isPresent()) {
                preparedStmt.setInt (idx, categoryId.get());
                idx+=1;
            }
            if(stage.isPresent()) {
                preparedStmt.setInt (idx, stage.get().getValue());
                idx+=1;
            }

            preparedStmt.setString (idx, listingId.toString());
            preparedStmt.execute();

            return Get(listingId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
