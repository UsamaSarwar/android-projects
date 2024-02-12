<?php
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$city_id = $_POST['city_id'];
$lab_name = $_POST['lab_name'];
$lab_phone = $_POST['lab_phone'];
$lab_address = $_POST['lab_address'];
//$dr_id = 1;
//$feedback = 'aamir';



$sql = "INSERT INTO labs (city_id,lab_name,lab_phone,lab_address) VALUES ('$city_id','$lab_name','$lab_phone','$lab_address')";

if(mysqli_query($con,$sql)){
	$json = 'Successfully submited';
	echo json_encode($json);
}
else{
	$json = 'Error...Try again!!';
	echo json_encode($json);
	}
	
	mysqli_close($con);


?>