package helpers;

import interfaces.DBConnector;
import interfaces.DataItem;
import interfaces.StorageHandler;
import models.DataFrame;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * https://www.youtube.com/watch?v=BCqW5XwtJxY
 */
public class WeatherDatabase extends DBConnector implements StorageHandler{
    public WeatherDatabase() throws SQLException {
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

    /**
     * source: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
     * @param dataFrames
     * @throws SQLException
     */
    @Override
    public void update(DataFrame[] dataFrames) throws SQLException {
        for (DataFrame frame: dataFrames) {
            for (DataItem item : frame.getItems()) {
                HashMap dataMap = item.getData();
                int station = Integer.parseInt((String) dataMap.get("stn"));  // station is primary key and necessary for inserting data
                for (Object o : dataMap.entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
//                    System.out.println(pair.getKey() + " = " + pair.getValue());
                }
            }
        }
        ResultSet rs = statement.executeQuery(""); // just an example
        rs.close();
    }
}
