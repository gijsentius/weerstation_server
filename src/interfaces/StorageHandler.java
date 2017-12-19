package interfaces;

import models.DataFrame;

import java.sql.SQLException;

public interface StorageHandler {

    public void update(DataFrame[] dataFrames) throws SQLException;
}
