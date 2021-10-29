/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hostpital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author misho
 */
public class OperationRoom {
     private static final String DBURL = "jdbc:derby://localhost:1527/Doctor;create=true;user=doctor;password=1234";
    private static Connection conn;
    public static void main(String[] args){
        createConnection();
        addDoctor(1,"Mikheil", "Dr. Strauss");
        getRoomData();
    }
    /**
     * Creates connection to database.
     */
    private static void createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(DBURL);
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
    /**
     * Insert a new doctor
    */
    private static void addDoctor(int ID, String patientName, String doctorName )
    {
        try
        {
            String sql = "INSERT INTO OperationRoom(ID, Operation_Patient_Name, Operation_Doctor_Name) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            stmt.setString(2, patientName);
            stmt.setString(3,doctorName);
      
            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("Data inserted successfully!");
            }
        }
        catch (SQLException e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
    /**
      Print all doctors info
    */
    private static void getRoomData()
    {
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from OperationRoom");
            ResultSetMetaData rsmd = results.getMetaData();
            //Get number of columns in the result set
            int numberCols = rsmd.getColumnCount();
            //Print column names as header
            for (int i = 1; i <= numberCols; i++)
            {
                //print Column Names
                System.out.printf("%12s", rsmd.getColumnLabel(i));
            }
            System.out.println();
            //Print the results
            while(results.next())
            {
                for (int i = 1; i <= numberCols; i++)
                {
                    System.out.printf("%12s", results.getString(i));
                }
                System.out.println();
            }
            results.close();
            stmt.close();
        }
        catch (SQLException e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        
    }

}
