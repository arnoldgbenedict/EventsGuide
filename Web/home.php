<?php
		session_start();
		if(array_key_exists("id", $_SESSION))
		{
			//echo "<div><p>Welcome user <a href='logoutt.php' style='float:right;'>Log Out</a></p></div>";

		}else{
			header("location:login1.php");
	}
?>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" type="text/css" href="w3.css">
	<link rel="stylesheet" href="w3-theme-teal.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lobster">
	<style>
	.w3-lobster {
	    font-family: "Lobster", serif;
	}
	</style>
</head>
<body>
	<nav class="w3-sidebar w3-bar-block w3-collapse w3-animate-left w3-card" style="z-index:3;width:250px;" id="mySidebar">
			<a class="w3-bar-item w3-button w3-border-bottom w3-large" href="#"><img src="https://christuniversity.in/images/logo.jpg" style="width:80%;"></a>
			<a class="w3-bar-item w3-button w3-hide-large w3-large" href="javascript:void(0)" onclick="w3_close()">Close <i class="fa fa-remove"></i></a>
		    <a class="w3-bar-item w3-button w3-teal" href="home.php">Home</a>
			<a class="w3-bar-item w3-button" href="insert_venue.php">Add venue</a>
			<a class="w3-bar-item w3-button" href="insert_faculty.php">Adds faculty</a>
			<a class="w3-bar-item w3-button" href="insert_event_details.php">Add event</a>
			<a class="w3-bar-item w3-button w3-dark-grey" href="logoutt.php">LogOut!</a>
	</nav>
	<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" id="myOverlay"></div>

	<div class="w3-main" style="margin-left:250px;">
	    <div id="myTop" class="w3-container w3-top w3-theme w3-large">
  <p><i class="fa fa-bars w3-button w3-teal w3-hide-large w3-xlarge" onclick="w3_open()"></i>
  <span id="myIntro" class="w3-hide">Book-E: Adding an event</span></p>
</div>
		<header class="w3-container w3-theme" style="padding:64px 32px">
  			<h1 class="w3-xxxlarge">Welcome</h1>
		</header>
		<div class="w3-container" style="padding:32px">
			<div class="w3-container w3-half w3-margin-top ">
				<div class="w3-container w3-center">
					<div class="w3-container w3-lobster">
						<p class="w3-xxxlarge">One place to add all Events!</p>
					</div>
				</div>
			</div>
		</div>
</body>
<script>
    window.onscroll = function() {myFunction()};
function myFunction() {
    if (document.body.scrollTop > 80 || document.documentElement.scrollTop > 80) {
        document.getElementById("myTop").classList.add("w3-card-4", "w3-animate-opacity");
        document.getElementById("myIntro").classList.add("w3-show-inline-block");
    } else {
        document.getElementById("myIntro").classList.remove("w3-show-inline-block");
        document.getElementById("myTop").classList.remove("w3-card-4", "w3-animate-opacity");
    
</script>

</html>