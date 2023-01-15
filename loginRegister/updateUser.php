<?php 

    $servername = 'localhost';
    $name = 'root';
    $password = '';
    $databasename = 'checkCalculatorDB';

    $con = mysqli_connect($servername, $name, $password, $databasename);

	if($_SERVER['REQUEST_METHOD']=='POST'){
		$id = $_POST['id'];
		$name = $_POST['name'];
        $email = $_POST['email'];
        $country = $_POST['country'];
        $about = $_POST['about'];
		$image = $_POST['image'];
		
		$path = "images/$name$id.png";
		$actualpath = "http://192.168.1.65/loginRegister/$path";

		//if($image == ''){
		//	$sql = "UPDATE users_table SET name = '$name', country = '$country', email = '$email', about = '$about', image = 'http://192.168.1.65/loginRegister/images/male.png' WHERE id = $id;";
		//} else {
			$sql = "UPDATE users_table SET name = '$name', country = '$country', email = '$email', about = '$about', image = '$actualpath' WHERE id = $id;";
		//}
		
		
		if(mysqli_query($con,$sql)){
			file_put_contents($path, base64_decode($image));	
			echo 'User Updated Successfully';
		}else{
			echo 'Could Not Update User Try Again';
		}
		
		mysqli_close($con);
	}
?>    