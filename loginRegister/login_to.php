<?php 
	$servername = 'localhost';
    $name = 'root';
    $password = '';
    $databasename = 'checkCalculatorDB';

    $con = mysqli_connect($servername, $name, $password, $databasename);

	//Creating sql query
	$sql = "SELECT * FROM users_table";

	
	//getting result 
	$r = mysqli_query($con,$sql);
	
	//creating a blank array 
	$result = array();

	//looping through all the records fetched
	while($row = mysqli_fetch_array($r)){
        //Pushing name and id in the blank array created
		array_push($result, array(
			    "id"=>$row['id'],
			    "name"=>$row['name'],
                "country"=>$row['country'],
                "password"=>$row['password'],
			    "email"=>$row['email']
		));
        
		
	}
	
	//Displaying the array in json format 
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>