package interfaces;

import java.util.HashMap;

public interface DataItem {
    /**
     * all the methods necessary for the db insertion
     */
    String getIdentifier();
    HashMap getData();
}
