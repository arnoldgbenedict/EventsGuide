<?php
define("HOST_NAME","localhost");
define("USER_NAME","id5720309_tempusername");
define("PASSWORD","qwertyuiop");
define("Database","id5720309_eventbase");
$connection=new mysqli(HOST_NAME,USER_NAME,PASSWORD);

if($connection->connect_error) {
	die("Connection Failed:" . $connection->connect_error);
}

mysqli_select_db($connection,Database);
?>