<?php

$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

	$name = $_POST['name'];
	$user_name = $_POST['user_name'];
	$emaili = $_POST['email'];
	$password = $_POST['password'];
	
	
	//$name = "aamir";
	//$user_name = "aamir123";
	//$emaili = "aamir@gmail.com";
	//$password = "123";
	

$sql = "select * from user where user_name like '$user_name' and
 email like '$emaili';";

	$result = mysqli_query($con,$sql) or die("MySQL error: " . mysqli_error($con) . ": $sql");
	$check = mysqli_fetch_array($result);
	if(isset($check)){
		$json= 'User already exists';
		echo json_encode($json);
		}
	else{
		$sql = "INSERT INTO user (name,user_name,email,password) VALUES ('$name','$user_name','$emaili','$password')";
		
		if(mysqli_query($con,$sql)){
			$json = 'Registration Success';
			echo json_encode($json);
		}
		else{
			$json = 'Registration unsuccess';
			echo json_encode($json);
			}
	}
	mysqli_close($con);

?>