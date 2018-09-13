<?php
require_once('database.php');

//venue table
$FName=$_POST["fname"];
$ImageLink=$_POST["imglink"];

$sql="INSERT INTO faculty_table(Faculty_name,imgLink) values('".$FName."','".$ImageLink."')";

//checking connection
if(mysqli_query($connection,$sql)){
	header("location:insert_faculty.php");
}
else{
	echo mysqli_error($connection);
}
?>
