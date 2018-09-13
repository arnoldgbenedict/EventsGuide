<?php
require_once('database.php');

//venue table
$Block=$_POST["block"];
$Floor=$_POST["floor"];
$Room=$_POST["room"];

$sql="INSERT INTO place(Block,Floor,Room) values('".$Block."','".$Floor."','".$Room."')";

//checking connection
if(mysqli_query($connection,$sql)){
	header("location:insert_venue.php");
}
else{
	echo mysqli_error($connection);
}
?>
