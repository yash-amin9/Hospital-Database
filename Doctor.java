package hostpital;
/**
 * @author Mikheil Uglava
 * Hospital database
 * This class is responsible of creating, modifying and fetching doctor's information
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Doctor {
    private static final String DBURL = "jdbc:derby://localhost:1527/Doctor;create=true;user=doctor;password=1234";
    private static Connection conn;
    public static void main(String[] args ){
        
        createConnection(); // Call method to create a connection
        
        addDoctor(1,"Mikheil ","Uglava","123456",1); 
        addDoctor(2,"John ","Doe","123456",2);
      
         getDoctorsList();
       
        
         
        
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
    private static void addDoctor(int doctorID, String firstName, String lastName, String specialty,int operationRoom )
    {
        try
        {
            String sql = "INSERT INTO doctor(ID, First_Name, Last_Name, Specialty, Operation_Room) VALUES (?, ?, ?, ?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, doctorID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, specialty);
            stmt.setInt(5, operationRoom);
            int rowInserted = stmt.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("Doctor inserted successfully!");
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
    private static void getDoctorsList()
    {
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from doctor");
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
     /**
      Update doctor information
    */
    private static void updateContact(int ID, String firstName, String lastName, String specialty,int operationRoom)
    {
        try
        {
            String sql = "UPDATE doctor SET First_Name=?, Last_Name=?, specialty=?, Operation_Room=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, specialty);
            stmt.setInt(5, operationRoom);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing contact was updated successfully!");
            }
        }
        catch (SQLException e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
    /**
     * Delete doctor's info from the table
     * @param ID Doctor's ID number
     */
    private static void deleteDoctorInfo(int ID)
    {
        try
        {
            String sql = "DELETE FROM doctor WHERE ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A contact was deleted successfully!");
            }
        }
        catch (SQLException e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
