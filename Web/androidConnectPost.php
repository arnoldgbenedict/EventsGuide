<?php
   $con=mysqli_connect("localhost","id5720309_tempusername","qwertyuiop","id5720309_eventbase");

   if (mysqli_connect_errno($con)) {
      echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
	
   $nextTimestamp = $_GET["ts"];
   $result = mysqli_query($con,"SELECT * FROM event_details WHERE srcTime > '".$nextTimestamp."'");

    $first_row = true;
    while ($row = mysqli_fetch_assoc($result)) {
        foreach($row as $key => $field) {
            echo htmlspecialchars($field) . '`';
        }
        echo '^';
    }
	
   mysqli_close($con);
?>