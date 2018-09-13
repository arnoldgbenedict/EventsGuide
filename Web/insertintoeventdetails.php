<?php
require_once('database.php');

//event details
$Event_name=$_POST["event_name"];
$Start_date=$_POST["start_date"];
$End_date=$_POST["end_date"];
$Start_time=$_POST["start_time"];
$End_time=$_POST["end_time"];
$Venue=$_POST["venue"];
$Type=$_POST["type"];
$Description=$_POST["description"];
$Faculty_incharge=$_POST["faculty_incharge"];
$Tag=implode(',',$_POST['tag']);



$sql="INSERT INTO event_details(Event_name,Start_date,End_date,Start_time,End_time,Venue,Type,Description,Faculty_incharge,Tags) values('".$Event_name."','".$Start_date."','".$End_date."','".$Start_time."','".$End_time."','".$Venue."','".$Type."','".$Description."','".$Faculty_incharge."','".$Tag."')";
//checking connection
if(mysqli_query($connection,$sql)){
	header("location:insert_event_details.php");
}
else{
	echo mysqli_error($connection);
}

?>