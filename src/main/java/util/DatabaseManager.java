package util;

import java.sql.*;

public class DatabaseManager {

    private static final String CREATE_TABLE_QUERY = "create table IF NOT exists public.test_results( " +
            "id serial primary key, " +
            "created timestamp, " +
            "scenario_name text, " +
            "scenario_status text " +
            ")";


    private static final String INSERT_TEST_RESULT = "insert into public.test_results" +
            " (created, scenario_name,scenario_status)" +
            " values " +
            "(now(), ?, ?)";

    private final Connection connection;
    private final PreparedStatement insertResultStatement;

    public DatabaseManager() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "example");
        Statement statement = connection.createStatement();
        statement.execute(CREATE_TABLE_QUERY);
        insertResultStatement = connection.prepareStatement(INSERT_TEST_RESULT);
    }


    public void recordTestResult(String scenarioName, String scenarioStatus) throws SQLException {
        insertResultStatement.setString(1, scenarioName);
        insertResultStatement.setString(2, scenarioStatus);
        insertResultStatement.execute();
    }

    public void close() throws SQLException {
        connection.close();
    }
}
