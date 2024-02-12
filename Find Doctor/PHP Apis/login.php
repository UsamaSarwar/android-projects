<?php

$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

	$name = $_POST['name'];
	$emaili = $_POST['email'];
	$password = $_POST['password'];
	
	
	//$name = "aamir";
	//$user_name = "aamir123";
	//$emaili = "aamir@gmail.com";
	//$password = "123";
	

$sql = "select * from user where email like '$emaili' and password like '$password';";

	$result = mysqli_query($con,$sql) or die("MySQL error: " . mysqli_error($con) . ": $sql");
	$check = mysqli_fetch_array($result);
	if(isset($check)){
		$json= 'Login Success';
		echo json_encode($json);
		}
	else{
		$json = 'Login Failed';
		echo json_encode($json);
	}
	mysqli_close($con);

?>