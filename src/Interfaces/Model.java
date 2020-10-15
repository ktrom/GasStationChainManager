package Interfaces;

import java.sql.SQLException;

/**
 * Interface for all classes to be stored in the database
 */
public interface Model {

    /**
     * Creates a row in the database with this object's instance variables' values
     * @return true if successful creation, false otherwise
     */
    public boolean create();

    /**
     * Pushes updates to this object's instance variables' values to the database
     * @return true if successful update, false otherwise
     */
    public boolean push();

    /**
     * Pulls an already existing object from the database with the same ID as this object and stores the new data in this object's instance variables
     * @return true if successful population, false otherwise
     */
    public boolean pull();
}
