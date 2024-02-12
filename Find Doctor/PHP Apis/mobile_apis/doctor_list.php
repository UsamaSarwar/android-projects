<?php
// local host , user name db , user db password , db name
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}

$prof_id = $_POST['prof_id'];
$city_id = $_POST['city_id'];
$doc_name = $_POST['doc_name'];
//$prof_id = 1;
//$city_id = 0;
//$cityName = 'sahi'; 
$name = 'la';
       if($prof_id == -1){
$sql = "select * from doctors where city_id = $city_id AND doc_name LIKE '%{$doc_name}%'";
}else{
$sql = "select * from doctors where city_id = $city_id AND prof_id = $prof_id";
}


$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("doc_id"=>$row[0],"prof_id"=>$row[1],
								"city_id"=>$row[2],
								"doc_name"=>$row[3],
								"doc_edu"=>$row[4],
								"doc_timing"=>$row[5],
								"doc_fee"=>$row[6],
								"experience"=>$row[7],
								"doc_phone"=>$row[8],
								"doc_address"=>$row[9],
								"Services"=>$row[10],
								"doc_photo"=>"http://developme.comli.com/images/".$row[11],
								"category"=>$row[12],
								"hospital"=>$row[13]));
	
}

echo json_encode($response);

mysqli_close($con);


?>