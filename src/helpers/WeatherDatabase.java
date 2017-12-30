package helpers;

import interfaces.DBConnector;
import interfaces.DataItem;
import interfaces.StorageHandler;

import java.sql.DriverManager;
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
     * @param
     * @throws SQLException
     */
    @Override
    public void update(LinkedList<DataItem> data) {
        StringBuilder query = new StringBuilder("INSERT INTO `measurements` ");
        StringBuilder query_item_names = new StringBuilder("(");
        StringBuilder query_item_values = new StringBuilder("(");
            for (DataItem item : data) {
                int counter = 0;
                for (Object o : item.getData().entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    if (counter == 0) {
                        query_item_names.append("`" + pair.getKey() + "`");
                        query_item_values.append("'" + pair.getValue() + "'");
                        counter++;  // increment counter so this part will only run once
                    } else {
                        query_item_names.append(", " + '`' + pair.getKey() + '`');
                        query_item_values.append(", " + '\'' + pair.getValue() + '\'');
                    }
                }
                query_item_names.append(")");
                query_item_values.append(")");
                query.append(query_item_names);
                query.append(" VALUES ");
                query.append(query_item_values);
//                System.out.println(query.toString());
//            ResultSet rs = statement.executeQuery(query.toString()); // just an example
//            rs.close();
            }
        }
            /*
            INSERT INTO `measurements` (`STN`, `TIMESTAMP`, `TEMP`, `DEWP`, `STP`, `SLP`, `VISIB`, `WDSP`, `PRCP`, `SNDP`, `FRSHTT`, `CLDC`, `WNDDIR`) VALUES ('3456', CURRENT_TIMESTAMP, '456', '4365', '456', '3465', '4365', '3465', '3465', '3465', '3465', '345', '3465')
             */
}
