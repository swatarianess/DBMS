import java.sql.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.Statement;

/**
 * Created by Reall_blue on 30/03/2016.
 * Project: DBMS
 */
class Testing {

    private String url = "jdbc:mysql://localhost:3306/";
    private String dbName = "weather";
    private String driver = "com.mysql.jdbc.Driver";
    private String userName = "root";
    private String password = "password123";
    private static String sqlStatement = "SELECT DISTINCT city, firstname, sex, w.stationid, measured, winddirection, windspeedhighest - windspeedlowest as wind,\n" +
            "temperaturemax - temperaturemin AS temperature, sunshine, rain, pressuremax-pressuremin as pressure,\n" +
            "clouds, humiditymax - humiditymin AS humidity\n" +
            "FROM weather w INNER JOIN person p ON w.measured = p.dateofbirth\n" +
            "  INNER JOIN station s ON w.stationid = s.stationid\n" +
            "WHERE monthname(p.dateofbirth)='may' AND year(p.dateofbirth)=1995\n" +
            "ORDER BY DAY(p.dateofbirth) LIMIT 2;";
    private Statement statement = null;
    private ResultSet resultSet = null;

    private static Testing myObj;
    private static Connection con;


    private Testing(){
        System.out.println("Hello");
        con = createConnection();
    }

    private Connection createConnection(){
        Connection connection = null;
        try {
            Class driver_class = Class.forName(driver);
            Driver driver = (Driver) driver_class.newInstance();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(url + dbName,userName,password);
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Testing getInstance(){
        if(myObj == null){
            myObj = new Testing();
        }
        return myObj;
    }

    public static void main(String a[]) throws Exception {
        Testing st = Testing.getInstance();
        st.createConnection();

        Statement s = con.createStatement();
        ResultSet r = s.executeQuery(st.sqlStatement);
        printTable(r);
        System.out.println("Done bitch");
    }

    private static void printTable(ResultSet r) throws SQLException {
        System.out.print("| ");
        for(int m = 1; m <= r.getMetaData().getColumnCount(); m++){
            System.out.printf("%19s | ",r.getMetaData().getColumnName(m));
        }
        System.out.println();

        while (r.next()){
            for(int i = 1; i <= r.getMetaData().getColumnCount();i++) {
                System.out.printf("%20s", r.getString(i));
            }
            System.out.println();
        }

    }



}
