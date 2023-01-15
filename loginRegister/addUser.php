<?php 

    $servername = 'localhost';
    $name = 'root';
    $password = '';
    $databasename = 'checkCalculatorDB';

    $con = mysqli_connect($servername, $name, $password, $databasename);

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Getting values
		$name = $_POST['name'];
		$country = $_POST['country'];
		$password = $_POST['password'];
		$email = $_POST['email'];
        $about = $_POST['about'];
		$image = $_POST['image'];
		
		//Creating an sql query
		$sql = "INSERT INTO users_table(name, country, password, email, about, image) VALUES ('$name', '$country', '$password', '$email', '$about', '$image')";
		
		//Importing our db connection script
		
		//Executing query to database
		if(mysqli_query($con,$sql)){
			echo 'User Register Successfully';
		}else{
			echo 'Could Not Register User Try Again';
		}
		
		//Closing the database 
		mysqli_close($con);
	}
?>    