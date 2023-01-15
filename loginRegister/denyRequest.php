<?php 
	
    $servername = 'localhost';
    $name = 'root';
    $password = '';
    $databasename = 'checkCalculatorDB';
     
    // connect the database with the server
    $conn = new mysqli($servername, $name, $password, $databasename);
    
    $id = $_GET['id'];
	
	$sql = "DELETE FROM friends_table WHERE id = $id AND request = 1;";
	
	if(mysqli_query($conn,$sql)){
		echo 'User Deleted Successfully';
	}else{
		echo 'Could Not Delete User Try Again';
	}
	
	mysqli_close($conn);

?>