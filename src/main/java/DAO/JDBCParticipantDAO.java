package DAO;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCParticipantDAO implements ParticipantDAO {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // code to load the configuration and establish a connection
        String url = "jdbc:mysql://mysql/devDB";
        String username = "root";
        String password = "admin123!";
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("Error in getConnection JDBCPARTICIPANTDAO line 23");
            // handle the error
        }
        return DriverManager.getConnection(url, username, password);
    }

    public CachedRowSet executeSQLQuery(String sqlQuery) {
        Connection conn = null;
        Statement stmt = null;
        CachedRowSet rowSet = null;
        // Create a CachedRowSet from the ResultSet

        try {
            // Load the JDBC driver and establish a connection
            conn = getConnection();

            // Execute a SELECT statement to get the participants
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            rowSet = RowSetProvider.newFactory().createCachedRowSet();
            rowSet.populate(rs);
            // Process the result set
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Connection FAILED IN JDBCPARTICIPANDAO LINE 43");
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                // nothing we can do
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return rowSet;
    }

    public void executeSQLUpdate(String sql){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Connection FAILED IN JDBCPARTICIPANDAO LINE 73");
        } finally {
            try {
              if(stmt!=null){
                  stmt.close();
              }
              if(conn!=null){
                  conn.close();
              }
            } catch (SQLException se){
                //nothing we can do
            }
        }
    }

    @Override
    public List<Participant> getAllParticipants() {
        ArrayList<Participant> participants = new ArrayList<Participant>();
        String sql = "SELECT * FROM participants";
        CachedRowSet rs = null;
        try {
            rs = executeSQLQuery(sql);
            while(rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int batchID = rs.getInt(4);
                participants.add(new Participant(id,name,batchID));
            }
        }
        catch (SQLException se){
            se.printStackTrace();
            System.out.println("Connection FAILED IN JDBCPARTICIPANDAO LINE 104");
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se2) {
                // nothing we can do
            }
        }
        return participants;
    }

    @Override
    public Participant getParticipantByID(int pid) {
        Participant participant = null;
        String sql = "SELECT p.* FROM participants p where p.id="+ pid;
        ResultSet rs = null;
        try {
            rs = executeSQLQuery(sql);
            // Process the result set
            if(rs.next()){
                participant = new Participant(rs.getInt("id"),rs.getString("name"), rs.getInt("batchID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection FAILED IN JDBCPARTICIPANDAO LINE 131");
        } finally {
            // finally block used to close resources
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se2) {
                // nothing we can do
            }
        }
        return participant;
    }

    @Override
    public void updateParticipant(Participant participant) {
        String sql = "UPDATE Participants " + "SET name = '"+ participant.getName() +"', batchID = "+ participant.getbatchID()  +
                " WHERE ID = "+ participant.getpid();
        //String sql = "UPDATE Participants SET name = 'karl', batchID = 2 WHERE id = 3";
        executeSQLUpdate(sql);
    }

    @Override
    public void addParticipant(Participant participant) {
        String sql = "insert into Participants (name,BatchName,batchID) VALUES ('"+ participant.getName() +"', '" + "randomBatch" + "', " + participant.getbatchID() + ")";
        executeSQLUpdate(sql);
    }

    @Override
    public void deleteParticipant(int pid) {
        String sql = "DELETE FROM Participants WHERE id = "+pid;
        executeSQLUpdate(sql);
    }
}
