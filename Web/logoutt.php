<?php

	
    header("location:login1.php");
    session_start();
	session_destroy();
	//setcookie(PHPSESSID,session_id(),time()-1);

?>