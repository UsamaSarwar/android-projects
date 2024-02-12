<?php
// local host , user name db , user db password , db name
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}


$sql = "select * from doctor_profession";

$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("prof_id"=>$row[0],"prof_title"=>$row[1]));
	
}

echo json_encode($response);

mysqli_close($con);


?>