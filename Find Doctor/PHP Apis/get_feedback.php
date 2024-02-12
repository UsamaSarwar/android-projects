<?php
// local host , user name db , user db password , db name
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}


$doc_id = $_POST['doc_id'];
//$doc_id = 1;
$sql = "select * from feedback where doc_id = $doc_id;";

$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("fb_id"=>$row[0],"doc_id"=>$row[1],"user"=>$row[2],

								"feedback"=>$row[3],
								"time"=>$row[4]));
	
}

echo json_encode($response);

mysqli_close($con);


?>