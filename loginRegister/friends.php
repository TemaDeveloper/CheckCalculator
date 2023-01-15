<?php
    $servername = 'localhost';
    $name = 'root';
    $password = '';
    $databasename = 'checkCalculatorDB';
     
    // connect the database with the server
    $conn = new mysqli($servername, $name, $password, $databasename);

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $user_id = $_POST['user_id'];
    $my_id = $_POST['my_id'];

    $sql = "SELECT * FROM friends_table WHERE (user_id = $user_id  OR my_id = $my_id) AND request = 0;";
    $result = ($conn->query($sql));
    $response = array();
    while($row = mysqli_fetch_array($result))
    {
        array_push($response, array('name' => $row['name'],
         'country' => $row['country'], 
         'about' => $row['about'], 
         'image' => $row['image'], 
         'my_id' => $row['my_id'], 
         'user_id' => $row['user_id'], 
         'request' => $row['request'], 
         'friendName' => $row['friendName'], 
         'friendImage' => $row['friendImage']));
    }
    echo json_encode($response);
}
?>

  
<?php   
    mysqli_close($conn);
?>