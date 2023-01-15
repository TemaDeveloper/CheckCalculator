<?php 

    $servername = 'localhost';
    $name = 'root';
    $password = '';
    $databasename = 'checkCalculatorDB';

    $con = mysqli_connect($servername, $name, $password, $databasename);

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$id = $_POST['id'];
        $request = $_POST['request'];
	
		$sql = "UPDATE friends_table SET request = '$request' WHERE id = $id;";
		
		if(mysqli_query($con,$sql)){
			echo 'FRIEND Updated Successfully';
		}else{
			echo 'Could Not Update FRIEND Try Again';
		}
		
		mysqli_close($con);
	}
?>    