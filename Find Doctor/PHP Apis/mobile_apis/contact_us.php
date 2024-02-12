<?php
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$name = $_POST['name'];
$phone = $_POST['phone'];
$subject = $_POST['subject'];
$message = $_POST['message'];


//$dr_id = 1;
//$feedback = 'aamir';



$sql = "INSERT INTO contact_us (name,phone,subject,message,datetime) VALUES ('$name','$phone','$subject','$message',now())";

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