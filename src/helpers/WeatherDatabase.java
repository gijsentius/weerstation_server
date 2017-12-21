package helpers;

import interfaces.DBConnector;
import interfaces.DataItem;
import interfaces.StorageHandler;
import loggers.ExceptionLogger;
import models.DataFrame;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","");
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
    public void update(DataFrame[] dataFrames) {
        try {
            StringBuilder query = new StringBuilder("INSERT INTO `measurements` ");
            StringBuilder query_item_names = new StringBuilder("(");
            StringBuilder query_item_values = new StringBuilder("(");
            for (DataFrame frame : dataFrames) {
                for (DataItem item : frame.getItems()) {
//                    HashMap dataMap = item.getData();
//                    int station = Integer.parseInt((String) dataMap.get("stn"));  // station is primary key and necessary for inserting data
                    for (Object o : item.getData().entrySet()) {
                        Map.Entry pair = (Map.Entry) o;
                        query_item_names.append(", " + '`' + pair.getKey() + '`');
                        query_item_values.append(", " + '\'' + pair.getValue() + '\'');
                    }
                }
            }
            /*
            INSERT INTO `measurements` (`STN`, `TIMESTAMP`, `TEMP`, `DEWP`, `STP`, `SLP`, `VISIB`, `WDSP`, `PRCP`, `SNDP`, `FRSHTT`, `CLDC`, `WNDDIR`) VALUES ('3456', CURRENT_TIMESTAMP, '456', '4365', '456', '3465', '4365', '3465', '3465', '3465', '3465', '345', '3465')
             */
            query_item_names.append(")");
            query_item_values.append(")");
            query.append(query_item_names);
            query.append(" VALUES ");
            query.append(query_item_values);
            ResultSet rs = statement.executeQuery(query.toString()); // just an example
            rs.close();
        } catch (SQLException e) {
            ExceptionLogger.logException(e);
        }
    }
}
