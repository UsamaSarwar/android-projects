<?php
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$city_id = $_POST['city_id'];

//$dr_id = 1;
//$feedback = 'aamir';



$sql = "SELECT * FROM labs WHERE city_id = '$city_id'" ;

$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("lab_id"=>$row[0],"lab_name"=>$row[2],"lab_phone"=>$row[3],"lab_address"=>$row[4]));
	
}

echo json_encode($response);

mysqli_close($con);


?>