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
    private String sqlStatement = "select * from weather w" +
            " inner join person p on w.measured = p.dateofbirth" +
            " inner join zipcode z on p.zipcode = z.zipcode" +
            " WHERE p.sex ='F' and z.cityname like '%rdam' LIMIT 5";

    private static Testing myObj;
    private static Connection con;


    private Testing(){
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
    }

    private static void printTable(ResultSet r) throws SQLException {
        int ColumnCount = r.getMetaData().getColumnCount();

        System.out.print("| ");
        for(int m = 1; m <= ColumnCount; m++){
            System.out.printf("%30s |" , r.getMetaData().getColumnName(m));
        }

        System.out.println();

        while (r.next()){

            System.out.print("| ");
            for(int i = 1; i <= r.getMetaData().getColumnCount();i++) {
                System.out.printf("%30s |", r.getString(i));

            }
            System.out.println();
        }

    }



}
