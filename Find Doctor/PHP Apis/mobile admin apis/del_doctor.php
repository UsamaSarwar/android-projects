<?php

$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$dr_id = $_POST['doc_id'];
//$dr_id = 1;

$sql = "DELETE FROM doctors WHERE doc_id = '$dr_id'";
$sql_feedback = "DELETE FROM feedback WHERE doc_id = '$dr_id'";
if(mysqli_query($con,$sql)){
	
	if(mysqli_query($con,$sql_feedback)){
		$json = 'Successfully Deleted';
		echo json_encode($json);
		
		}
	else{
		$json = 'Error...While deleting feedback!!!';
		echo json_encode($json);
		}
	//$json = 'Successfully Deleted';
//	echo json_encode($json);
}
else{
	$json = 'Error...Try again!!';
	echo json_encode($json);
	}
	
	mysqli_close($con);


?>