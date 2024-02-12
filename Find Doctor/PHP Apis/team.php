<?php
// local host , user name db , user db password , db name
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

       
$sql = "select * from team;";



$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("person_id"=>$row[0],
								"person_image"=>"http://developme.comli.com/images/".$row[1],
								"person_name"=>$row[2],
								"person_prof"=>$row[3],
								"person_category"=>$row[4]));
	
}

echo json_encode($response);

mysqli_close($con);


?>