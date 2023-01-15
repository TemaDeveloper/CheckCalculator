<?php 

    $servername = 'localhost';
    $name = 'root';
    $password = '';
    $databasename = 'checkCalculatorDB';
    
    $con = mysqli_connect($servername, $name, $password, $databasename);

	$id = $_GET['id'];
	
	$sql = "SELECT * FROM users_table WHERE id=$id";
	$r = mysqli_query($con,$sql);
	$row = mysqli_fetch_array($r);
	$arr = array(
			"id"=>$row['id'],
			"name"=>$row['name'],
            "country"=>$row['country'],
			"password"=>$row['password'],
			"email"=>$row['email'],
            "about"=>$row['about'],
			"image"=>$row['image']
		);

	echo json_encode($arr);
	
	mysqli_close($con);
    ?>