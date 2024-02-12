<?php

$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}


$sql = "select * from category";

$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("cat_id"=>$row[0],"cat_title"=>$row[1],"image"=>$row[2]));
	
}

echo json_encode(array("server_response"=>$response));

mysqli_close($con);


?>