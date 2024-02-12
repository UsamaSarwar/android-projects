<?php
// local host , user name db , user db password , db name
$con = mysqli_connect("localhost","id1300943_myjson","aamir","id1300943_myjson");
if(mysqli_connect_errno()){
echo mysqli_connect_errno();
exit();
}


$choice = $_POST['choice'];

if($choice == 1){
	$sql = "select * from blog ORDER BY blog_id DESC limit 10;";
	
	
	}else{
		
		$sql = "select * from blog ORDER BY blog_id ASC;";
		}

//$doc_id = 1;


$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("blog_id"=>$row[0],"heading"=>$row[1],"short_desc"=>$row[2],
	
								"photo"=>"http://developme.comli.com/blogimages/".$row[4],
								"datetime"=>$row[5]));
	
}

echo json_encode($response);

mysqli_close($con);


?>