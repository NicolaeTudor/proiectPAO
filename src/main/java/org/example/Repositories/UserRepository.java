package org.example.Repositories;

import org.example.DataModels.UserDM;
import org.example.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepository extends BaseRepository {
    public UserDM Get(UUID userId) {
        try {
            String query =
                    """
                            select BIN_TO_UUID(userId) as userId, BIN_TO_UUID(privateKey) as privateKey, username
                            from users
                            where userId = UUID_TO_BIN(?)
                    """;

            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setString (1, userId.toString());

            ResultSet rs = preparedStmt.executeQuery();

            rs.next();
            var username =  rs.getString("username");
            return new UserDM(
                    UUID.fromString(rs.getString("userId")),
                    UUID.fromString(rs.getString("privateKey")),
                    rs.getString("username"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: check for username uniqueness
    public UserDM Create(String username) {
        try {
            var userId = UUID.randomUUID();
            String query =
                    """
                            insert into users (userId, privateKey, username)
                            values (UUID_TO_BIN(?), UUID_TO_BIN(?), ?)
                    """;

            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setString (1, userId.toString());
            preparedStmt.setString (2, UUID.randomUUID().toString());
            preparedStmt.setString (3, username);

            preparedStmt.execute();

            return Get(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
