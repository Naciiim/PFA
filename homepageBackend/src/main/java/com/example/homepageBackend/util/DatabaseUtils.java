package com.example.homepageBackend.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {

    private static final String DATABASE_URL = "jdbc:h2:mem:boa"; // Utilisation de la même URL que dans application.properties
    private static final String USERNAME = "nassim"; // Utilisation du même nom d'utilisateur que dans application.properties
    private static final String PASSWORD = ""; // Utilisation du même mot de passe que dans application.properties
    private static final String DRIVER_CLASS_NAME = "org.h2.Driver"; // Utilisation du même driver que dans application.properties

    public static List<String> getDatabaseColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                columnNames.add(columnName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error fetching column names from database: " + e.getMessage());
        }

        return columnNames;
    }
}
