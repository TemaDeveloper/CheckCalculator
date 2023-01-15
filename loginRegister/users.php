<?php
    $servername = 'localhost';
    $name = 'root';
    $password = '';
    $databasename = 'checkCalculatorDB';
     
   // connect the database with the server
    $conn = new mysqli($servername,$name,$password,$databasename);

    $sql = "SELECT * FROM users_table";
    $result = ($conn->query($sql));
    $response = array();
    while($row = mysqli_fetch_array($result))
    {
        array_push($response, array('id' => $row['id'], 'name' => $row['name'], 'country' => $row['country'], 'email' => $row['email'], 'about' => $row['about'], 'image' => $row['image']));
    }
    echo json_encode($response);

?>

  
<?php   
    mysqli_close($conn);
?>