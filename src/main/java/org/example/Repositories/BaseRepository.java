package org.example.Repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseRepository {
    Connection conn;

    public BaseRepository()
    {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/auction_platform",
                    "admin", "P@ssw0rd");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
