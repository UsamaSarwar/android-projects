<?php
include('../admin/connection.php');


$choice = $_POST['choice'];

if($choice == 1){
	$sql = "select * from blog ORDER BY blog_id DESC limit 10;";
	
	
	}else{
		
		$sql = "select * from blog ORDER BY blog_id ASC;";
		}

//$doc_id = 1;


$result = mysqli_query($conn,$sql);

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response,array("blog_id"=>$row[0],"heading"=>$row[1],"short_desc"=>$row[2],
	
								"photo"=>"http://tabeeb.com.pk/admin/blogimages/".$row[4],
								"datetime"=>$row[5]));
	
}

echo json_encode($response);

mysqli_close($conn);


?>