<?php

$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

	$blog_id = $_POST['blog_id'];
	//$blog_id = 1;
	
	
	

$sql = "select long_desc from blog where blog_id like '$blog_id';";

	$result = mysqli_query($con,$sql) or die("MySQL error: " . mysqli_error($con) . ": $sql");
	$check = mysqli_fetch_array($result);
	if(isset($check)){
                $long_desc = $check['long_desc'];
		
		echo json_encode($long_desc);
		}
	
	mysqli_close($con);

?>