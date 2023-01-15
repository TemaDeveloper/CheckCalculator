<?php

    $servername = 'localhost';
    $name = 'root';
    $password = '';
    $databasename = 'checkCalculatorDB';

    $con = mysqli_connect($servername, $name, $password, $databasename);

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

        $name = $_POST['name'];
        $country = $_POST['country'];
        $about = $_POST['about'];
        $image = $_POST['image'];
        $my_id = $_POST['my_id'];
        $user_id = $_POST['user_id'];
        $request = $_POST['request'];
        $friendName = $_POST['friendName'];
        $friendImage = $_POST['friendImage'];


        $sql = "INSERT INTO friends_table (name, country, about, image, my_id, user_id, request, friendName, friendImage) VALUES ('$name', '$country', '$about', '$image', '$my_id', '$user_id', '$request', '$friendName', '$friendImage')";

        if(mysqli_query($con, $sql)){	
            echo 'Data Submit Successfully';
        }
        else{
            echo 'Try Again';
        }
        mysqli_close($con);
    }
?>