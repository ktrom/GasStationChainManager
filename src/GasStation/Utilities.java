package GasStation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utilities {

    /**
     * Connect to the GasStation database.
     *
     * @return JDBC database connection instance
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://hsn.kwa.mybluehost.me:3306/hsnkwamy_GasStation?useLegacyDatetimeCode=false&serverTimezone=UTC",
                "hsnkwamy_station",
                "z+VrfWS-ch=7?z7Y");
    }
}
