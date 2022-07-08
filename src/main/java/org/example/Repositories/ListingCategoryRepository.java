package org.example.Repositories;

import org.example.DataModels.ListingCategoryDM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class ListingCategoryRepository extends BaseRepository{
    public ListingCategoryDM Get(int categoryId) {
        try {
            String query =
                    """
                            select listing_categories_id, name
                            from listing_categories
                            where listing_categories_id = ?
                    """;

            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setInt(1, categoryId);

            ResultSet rs = preparedStmt.executeQuery();

            rs.next();
            return new ListingCategoryDM(
                    rs.getInt("listing_categories_id"),
                    rs.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ListingCategoryDM Create(String name) {
        try {
            var userId = UUID.randomUUID();
            String query =
                    """
                            insert into listing_categories (name)
                            values (?)
                    """;

            PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStmt.setString (1, name);

            preparedStmt.execute();

            ResultSet generatedKeys = preparedStmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                return Get(generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("Creating listing category failed, no ID obtained.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
