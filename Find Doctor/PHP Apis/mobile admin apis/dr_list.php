<?php
// local host , user name db , user db password , db name
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$prof_id = $_POST['prof_id'];
$city_id = $_POST['city_id'];
$sql = "select * from doctors where city_id = $city_id AND prof_id = $prof_id";
$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("doc_id"=>$row[0],"doc_name"=>$row[3],
								"doc_address"=>$row[9],
								"hospital"=>$row[13]));
	
}

echo json_encode($response);

mysqli_close($con);


?>