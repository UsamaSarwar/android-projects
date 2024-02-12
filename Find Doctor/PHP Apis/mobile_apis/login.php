<?php

$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

	$user_name = $_POST['user_name'];
	$password = $_POST['password'];
	
	
	//$name = "aamir";
	//$user_name = "Aamir40";
	//$emaili = "aamir@gmail.com";
	//$password = "12a";
	

$sql = "select * from user where user_name like '$user_name' and password like '$password';";

	$result = mysqli_query($con,$sql) or die("MySQL error: " . mysqli_error($con) . ": $sql");
	$check = mysqli_fetch_array($result);
	if(isset($check)){
                $name = $check['name'];
		$json= 'Login Success';
		echo json_encode($json.",".$name);
		}
	else{
		$json = 'Login Failed';
		echo json_encode($json);
	}
	mysqli_close($con);

?>