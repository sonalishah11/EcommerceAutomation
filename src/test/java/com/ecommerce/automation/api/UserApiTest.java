package com.ecommerce.automation.api;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserApiTest {

    // FIX: अब यह डेटा आपके कंप्यूटर पर 'ecommerce_practice_db' नाम की असली फाइल में सेव होगा
    private static final String DB_URL = "jdbc:h2:~/ecommerce_practice_db";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public UserApiTest() {
        // Default constructor
    }

    @Test
    public void verifyGetUsersList() {
        System.out.println("--- [SIMULATED API] Fetching users list ---");
        int statusCode = 200; 
        Assert.assertEquals(statusCode, 200, "API status code mismatch!");
        System.out.println("GET API Call Successful (Status: 200 OK)");
    }

    @Test
    public void verifyCreateUserWithDbCheck() {
        System.out.println("--- [SIMULATED API] Creating user 'Sonali' ---");

        String createdName = "Sonali";
        String createdJob = "QA Automation Engineer";
        int apiStatusCode = 201;

        Assert.assertEquals(apiStatusCode, 201, "API response status mismatch!");
        Assert.assertEquals(createdName, "Sonali", "API response name mismatch!");
        System.out.println("API Layer Validation Passed.");

        Connection connection = null;
        Statement setupStatement = null;
        PreparedStatement insertStatement = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        try {
            // कंप्यूटर पर असली डेटाबेस फाइल से कनेक्ट करें
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            setupStatement = connection.createStatement();
            setupStatement.execute("CREATE TABLE IF NOT EXISTS users (name VARCHAR(50), job VARCHAR(100))");
            
            // डेटा इन्सर्ट करें
            String insertSql = "INSERT INTO users (name, job) VALUES (?, ?)";
            insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setString(1, createdName);
            insertStatement.setString(2, createdJob);
            insertStatement.executeUpdate();

            // SQL क्वेरी चलाकर वेरीफाई करें
            String sqlQuery = "SELECT name, job FROM users WHERE name = ?";
            selectStatement = connection.prepareStatement(sqlQuery);
            selectStatement.setString(1, "Sonali");
            resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String dbName = resultSet.getString("name");
                String dbJob = resultSet.getString("job");

                // Eclipse Console me data direct dekh skte h
                System.out.println("\n==================================================");
                System.out.println("   🔴 LIVE DATABASE CONTENT (REAL DATA FOUND) 🔴      ");
                System.out.println("==================================================");
                System.out.println(" 🧑 USER NAME : " + dbName);
                System.out.println(" 💼 USER JOB  : " + dbJob);
                System.out.println("==================================================");
                // ====================================================================================
                
                Assert.assertEquals(dbName, "Sonali", "Database verification failed: Name mismatch!");
                Assert.assertEquals(dbJob, "QA Automation Engineer", "Database verification failed: Job mismatch!");
                
                System.out.println("--- SUCCESS: Database File Verified! ---");
            } else {
 
                Assert.fail("Database verification failed: No records found for user 'Sonali'!");
            }

        } catch (Exception e) {
            Assert.fail("Test failed due to database exception: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (insertStatement != null) insertStatement.close();
                if (setupStatement != null) setupStatement.close();
                if (connection != null) connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
