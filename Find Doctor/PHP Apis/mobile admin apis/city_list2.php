<?php
// local host , user name db , user db password , db name
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$id = $_POST['city_id'];

$sql = "SELECT * FROM city WHERE city_id = $id" ;

$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("city_id"=>$row[0],"city_name"=>$row[1]));
	
}

echo json_encode($response);

mysqli_close($con);


?>