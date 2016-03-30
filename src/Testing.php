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
$result = $conn->query($sql);

if(mysqli_num_rows($result)) {
    while ($row = mysqli_fetch_assoc($result)) {
        echo "<p>" . "city: " . $row["city"] . " Firstname: " . $row["firstname"]
            . " Sex: " . $row["sex"] . " StationID: " . $row["stationid"]
            . " Measured: " . $row["measured"] . " WindDirection: " . $row["winddirection"] . "</p>";
    }
} else {
    echo "0 results";
}
mysqli_close($conn);
