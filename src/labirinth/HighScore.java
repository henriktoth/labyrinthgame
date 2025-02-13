/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labirinth;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author henriktoth0517
 */
public class HighScore{
    
    private PreparedStatement insertStatement;
    private Connection connection;

    public HighScore() throws SQLException {
        String dbURL = "jdbc:derby://localhost:1527/database;";
        connection = DriverManager.getConnection(dbURL);
        
        String insertQuery = "INSERT INTO LEADERBOARD (TIMESTAMP, NAME, SCORE) VALUES (?, ?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
    }
    /**
    * inserts record of (name,score) into the database.
    * @param name - name of player
    * @param score - score of player
    * @throws SQLException
    */
    public void insert(String name, int score) throws SQLException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        insertStatement.setTimestamp(1, timestamp);
        insertStatement.setString(2, name);
        insertStatement.setInt(3, score);
        insertStatement.executeUpdate();
        System.out.println("High score inserted successfully!");
    }
    
     /**
    * returns the high score table in an ArrayList of Strings
    * @throws SQLException
    * @returns ArrayList of Strings
    * @throws SQLException
    */
    public ArrayList<String> getHighScores() throws SQLException {
        String query = "SELECT NAME, SCORE FROM LEADERBOARD ORDER BY SCORE DESC";
        ArrayList<String> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);


        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(name + ": " + score);
        }

        return highScores;
    }
    
}
