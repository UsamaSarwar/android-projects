<?php
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$prof_id = $_POST['prof_id'];
$city_id = $_POST['city_id'];
$doc_name = $_POST['doc_name'];
$doc_edu = $_POST['doc_edu'];
$doc_timing = $_POST['doc_timing'];
$doc_fee = $_POST['doc_fee'];
$experience = $_POST['experience'];
$doc_phone = $_POST['doc_phone'];
$doc_address = $_POST['doc_address'];
$Services = $_POST['Services'];
//$doc_photo = $_POST['doc_photo'];
$doc_photo = "";
$category = $_POST['category'];
$hospital = $_POST['hospital'];




$sql = "INSERT INTO doctors (prof_id,city_id,doc_name,doc_edu,doc_timing,doc_fee,experience,
							 doc_phone,doc_address,Services,doc_photo,category,hospital) VALUES ('$prof_id','$city_id','$doc_name'
							 ,'$doc_edu','$doc_timing','$doc_fee','$experience',
							 '$doc_phone','$doc_address','$Services','$doc_photo','$category','$hospital')";

if(mysqli_query($con,$sql)){
	$json = 'Successfully added';
	echo json_encode($json);
}
else{
	$json = 'Unsuccessfully added';
	echo json_encode($json);
	}
	
	mysqli_close($con);


?>