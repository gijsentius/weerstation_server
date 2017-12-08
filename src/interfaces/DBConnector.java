package interfaces;

import com.mysql.cj.jdbc.MysqlDataSource;
import enums.SQLCommand;
import models.DataFrame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Connects to a DB
 * Provides an interface to do insert operations on a database
 * Source: https://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database
 */
public abstract class DBConnector {
    protected MysqlDataSource dataSource;
    protected Connection connection;
    protected Statement statement;

    public  DBConnector() throws SQLException {
        this.dataSource = new MysqlDataSource();
    }

    public abstract void connectDB(String user, String password, String server) throws SQLException;
}
