<?php
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$dr_id = $_POST['doc_id'];
$user = $_POST['user'];
$feedback = $_POST['feedback'];

//$dr_id = 1;
//$feedback = 'aamir';



$sql = "INSERT INTO feedback (doc_id,user,feedback,time) VALUES ('$dr_id','$user','$feedback',now())";

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