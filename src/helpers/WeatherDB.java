package helpers;

import interfaces.DBConnector;
import interfaces.DataItem;
import models.DataFrame;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;

/**
 * https://www.youtube.com/watch?v=BCqW5XwtJxY
 */
public class WeatherDB extends DBConnector {
    public WeatherDB() throws SQLException {
        super();
    }

    @Override
    public void connectDB(String user, String password, String server) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db","root","");

//        dataSource.setUser(user);
//        dataSource.setPassword(password);
//        dataSource.setServerName(server);
//        connection = dataSource.getConnection();  // maybe do this in the update DB section
//        statement = connection.createStatement();
        statement = connection.createStatement();
    }

    @Override
    public void closeDB() throws SQLException {
        statement.close();
        connection.close();
    }

    public void updateDB(DataFrame[] dataFrames) throws SQLException {
        for (DataFrame frame: dataFrames) {
            for (DataItem item : frame.getItems()) {
                Dictionary d = item.getData();
                // do stuff with the db
            }
        }
        ResultSet rs = statement.executeQuery(""); // just an example
        rs.close();
    }
}
