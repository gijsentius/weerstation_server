package helpers;

import enums.SQLCommand;
import interfaces.DBConnector;
import interfaces.DBItem;
import models.DataFrame;
import models.WeatherData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;

public class WeatherDB extends DBConnector {
    public WeatherDB() throws SQLException {
        super();
    }

    @Override
    public void connectDB(String user, String password, String server) throws SQLException {
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(server);
        connection = dataSource.getConnection();  // maybe do this in the update DB section
        statement = connection.createStatement();
    }

    public void updateDB(DataFrame[] dataFrames) throws SQLException {
        for (DataFrame frame: dataFrames) {
            for (DBItem item : frame.getItems()) {
                Dictionary d = item.getData();

            }
        }
        ResultSet rs = statement.executeQuery(""); // just an example
    }
}
