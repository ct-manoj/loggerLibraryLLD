package sinks;

import core.LogMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSink implements Sink {

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS logs (" +
                    "id SERIAL PRIMARY KEY, " +
                    "level VARCHAR(10), " +
                    "timestamp VARCHAR(50), " +
                    "namespace VARCHAR(100), " +
                    "message TEXT" +
                    ")";
    private static final String INSERT_LOG_SQL =
            "INSERT INTO logs (level, timestamp, namespace, message) VALUES (?, ?, ?, ?)";

    private Connection connection;

    public DBSink(String dbHost, int dbPort, String dbName, String dbUser, String dbPassword) {
        openConnection(dbHost, dbPort, dbName, dbUser, dbPassword);
        createLogTableIfNeeded();
    }

    private void openConnection(String dbHost, int dbPort, String dbName, String dbUser, String dbPassword) {
        try {
            String url = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to open DB connection: " + e.getMessage(), e);
        }
    }

    private void createLogTableIfNeeded() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            System.err.println("Error creating logs table: " + e.getMessage());
        }
    }

    @Override
    public synchronized void log(LogMessage message) {
        try (PreparedStatement pstmt = connection.prepareStatement(INSERT_LOG_SQL)) {
            pstmt.setString(1, message.getLevel().toString());
            pstmt.setString(2, message.getTimestamp());
            pstmt.setString(3, message.getNamespace());
            pstmt.setString(4, message.getContent());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting log into DB: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.err.println("Error closing DB connection: " + e.getMessage());
        }
    }
}
