<?php
/**
 * Created by IntelliJ IDEA.
 * User: Reall_blue
 * Date: 30/03/2016
 * Time: 08:57 PM
 */

$server = 'localhost';
$databaseName = 'weather';
$username = 'root';
$password = 'password123';

$conn = new mysqli($server,$username,$password,$databaseName);

if(!$conn){
    die("Connection failed: " . mysqli_connect_error());
}

echo "<p>Connected successfully</p>";

$sql = "SELECT DISTINCT city, firstname, sex, w.stationid, measured, winddirection,
windspeedhighest - windspeedlowest as wind,
temperaturemax - temperaturemin AS temperature,
sunshine, rain, pressuremax-pressuremin AS pressure,
clouds, humiditymax - humiditymin AS humidity
FROM weather w
INNER JOIN person p ON w.measured = p.dateofbirth
INNER JOIN station s ON w.stationid = s.stationid
WHERE monthname(p.dateofbirth)='may'
AND year(p.dateofbirth) = 1995
ORDER BY DAY(p.dateofbirth) LIMIT 2;";
$result = mysqli_query($conn,$sql);

if(mysqli_num_rows($result)) {
// Print the column names as the headers of a table
    echo "<table style='100%' align='center'><tr>";
    for($i = 0; $i < mysqli_num_fields($result); $i++) {
        $field_info = mysqli_fetch_field($result);
        echo "<th>{$field_info->name}</th>";
    }

// Print the data
    while($row = mysqli_fetch_row($result)) {
        echo "<tr>";
        foreach($row as $_column) {
            echo "<td>{$_column}</td>";
        }
        echo "</tr>";
    }

    echo "</table>";
} else {
    echo "0 results";
}
mysqli_close($conn);
