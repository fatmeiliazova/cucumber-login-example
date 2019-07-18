package com.qa.util;

import com.qa.test.InvestorPlatformUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Arrays;

public class DBConnection {
    private static Logger LOG = LoggerFactory.getLogger(DBConnection.class);


    private static Connection getDBConnection(String dbName) {
        final String DB_URL = InvestorPlatformUI.testParams.getDBURL() + "/" + dbName + "?autoReconnect=true&useSSL=false";
        LOG.info("Database URL=" + DB_URL);
        Connection conn = null;
        try {
            String USER = InvestorPlatformUI.testParams.getTestEnvironment();
            String PASS = Decode.decodeText(InvestorPlatformUI.testParams.getDBPassword());
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void executeUpdateQuery(String dbName, String query) {
        Connection dbConnection = getDBConnection(dbName);
        Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();
            stmt.executeUpdate("use " + dbName);
            stmt.close();
            stmt = dbConnection.createStatement();
            LOG.info("Executing query=" + query);
            stmt.executeUpdate(query);
            stmt.close();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDBConnectionForUpdateQuery(stmt, dbConnection);
        }
    }

    private static void closeDBConnectionForUpdateQuery(Statement stmt, Connection dbConnection) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            LOG.info(Arrays.toString(e.getStackTrace()));
        }
        try {
            if (dbConnection != null)
                dbConnection.close();
        } catch (SQLException se) {
            LOG.info(Arrays.toString(se.getStackTrace()));
        }
    }


    private static void closeDBConnectionForSelectQuery(ResultSet rs, Statement stmt, Connection dbConnection) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            LOG.info(Arrays.toString(e.getStackTrace()));
        }
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            LOG.info(Arrays.toString(e.getStackTrace()));
        }
        try {
            if (dbConnection != null)
                dbConnection.close();
        } catch (SQLException se) {
            LOG.info(Arrays.toString(se.getStackTrace()));
        }
    }

    public static String getInvestorIDFromUserEmail(String dbName, String query) {
        Connection dbConnection = getDBConnection(dbName);
        Statement stmt = null;
        ResultSet rs = null;
        String investor_id = null;
        try {
            stmt = dbConnection.createStatement();
            stmt.executeQuery("use " + dbName);
            stmt.close();
            stmt = dbConnection.createStatement();
            LOG.info("Executing query=" + query);
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                investor_id = rs.getString("investor_id");
            }
            rs.close();
            stmt.close();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDBConnectionForSelectQuery(rs, stmt, dbConnection);
        }
        return investor_id;
    }

    public static String getBankAccountIDFromUserEmail(String dbName, String query) {
        Connection dbConnection = getDBConnection(dbName);
        Statement stmt = null;
        ResultSet rs = null;
        String account_id = null;
        try {
            stmt = dbConnection.createStatement();
            stmt.executeQuery("use " + dbName);
            stmt.close();
            stmt = dbConnection.createStatement();
            LOG.info("Executing query=" + query);
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                account_id = rs.getString("id");
            }
            rs.close();
            stmt.close();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDBConnectionForSelectQuery(rs, stmt, dbConnection);
        }
        return account_id;
    }

}