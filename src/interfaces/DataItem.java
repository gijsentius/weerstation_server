package interfaces;

import java.util.ArrayList;
import java.util.Dictionary;

public interface DataItem {
    /**
     * all the methods necessary for the db insertion
     */
    String[] getHeaders();
    Dictionary getData();
}
