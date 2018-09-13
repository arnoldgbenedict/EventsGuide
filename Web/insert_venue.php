<?php
		session_start();
		if(array_key_exists("id", $_SESSION))
		{
			//echo "<div><p>Welcome user <a href='logoutt.php' style='float:right;'>Log Out</a></p></div>";

		}else{
			header("location:login1.php");
	}	
$db_host = 'localhost'; // Server Name
		$db_user = 'id5720309_tempusername'; // Username
		$db_pass = 'qwertyuiop'; // Password
		$db_name = 'id5720309_eventbase'; // Database Name

$conn = mysqli_connect($db_host, $db_user, $db_pass, $db_name);
if (!$conn) {
	die ('Failed to connect to MySQL: ' . mysqli_connect_error());	
}

$sql = 'SELECT * 
		FROM place';
		
$query = mysqli_query($conn, $sql);

if (!$query) {
	die ('SQL Error: ' . mysqli_error($conn));
}
?>
<Html>
	<head>

		
<link rel="stylesheet" type="text/css" href="w3.css">
<link rel="stylesheet" href="w3-theme-teal.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

		<style type="text/css">
			body {
				font-size: 15px;
				color: #343d44;
				font-family: "segoe-ui", "open-sans", tahoma, arial;
				padding: 0;
				margin: 0;
			}
			table {
				margin: auto;
				font-family: "Lucida Sans Unicode", "Lucida Grande", "Segoe Ui";
				font-size: 12px;
			}

			h1 {
				margin: 25px auto 0;
				text-align: center;
				text-transform: uppercase;
				font-size: 17px;
			}

			table td {
				transition: all .5s;
			}
			
			/* Table */
			.data-table {
				border-collapse: collapse;
				font-size: 14px;
				min-width: 537px;
			}

			.data-table th, 
			.data-table td {
				border: 1px solid #e1edff;
				padding: 7px 17px;
			}
			.data-table caption {
				margin: 7px;
			}

			/* Table Header */
			.data-table thead th {
				background-color: #508abb;
				color: #FFFFFF;
				border-color: #6ea1cc !important;
				text-transform: uppercase;
			}

			/* Table Body */
			.data-table tbody td {
				color: #353535;
			}
			.data-table tbody td:first-child,
			.data-table tbody td:nth-child(4),
			.data-table tbody td:last-child {
				text-align: right;
			}

			.data-table tbody tr:nth-child(odd) td {
				background-color: #f4fbff;
			}
			.data-table tbody tr:hover td {
				background-color: #ffffa2;
				border-color: #ffff0f;
			}

			/* Table Footer */
			.data-table tfoot th {
				background-color: #e5f5ff;
				text-align: right;
			}
			.data-table tfoot th:first-child {
				text-align: left;
			}
			.data-table tbody td:empty
			{
				background-color: #ffcccc;
			}
		</style>
	</head>
	<body>
		<nav class="w3-sidebar w3-bar-block w3-collapse w3-animate-left w3-card" style="z-index:3;width:250px;" id="mySidebar">
			<a class="w3-bar-item w3-button w3-hide-large w3-large" href="javascript:void(0)" onclick="w3_close()">Close <i class="fa fa-remove"></i></a>
			<a class="w3-bar-item w3-button w3-border-bottom w3-large" href="#"><img src="https://christuniversity.in/images/logo.jpg" style="width:80%;"></a>
			
		    <a class="w3-bar-item w3-button " href="home.php">Home</a>
			<a class="w3-bar-item w3-button w3-teal" href="#">Add venue</a>
			<a class="w3-bar-item w3-button" href="insert_faculty.php">Add faculty</a>
			<a class="w3-bar-item w3-button" href="insert_event_details.php">Add event</a>
			<a class="w3-bar-item w3-button w3-dark-grey" href="logoutt.php">LogOut!</a>
		</nav>
		<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" id="myOverlay"></div>

<div class="w3-main" style="margin-left:250px;">

<div id="myTop" class="w3-container w3-top w3-theme w3-large">
  <p><i class="fa fa-bars w3-button w3-teal w3-hide-large w3-xlarge" onclick="w3_open()"></i>
  <span id="myIntro" class="w3-hide">Book-E: Adding venue</span></p>
</div>

<header class="w3-container w3-theme" style="padding:64px 32px">
  <h1 class="w3-xxxlarge">Add a venue</h1>
</header>
<div class="w3-container " style="padding:32px">

		<div id='some'></div>
		<div class="w3-container w3-half w3-margin-top ">
		<form action="insertvalues.php", method="post" id="f1" class="w3-container w3-card-4">
				<table>
					<tr>
						<td>Block:</td>
						<td><input type="text" name="block"></td>
					</tr>
					<tr>
						<td>Floor:</td>
						<td><input type="text" name="floor"></td>
					</tr>
					<tr>
						<td>Room:</td>
						<td><input type="text" name="room"></td>
					</tr>
					<tr>
						<td></td>
						<td><input class="w3-button w3-section w3-teal w3-ripple" type="Submit" form="f1" name = "place_submit" onclick="post"></td>
					</tr>
				</table>
		</form></div>
		<table class="data-table">
		<caption class="title">VENUE TABLE</caption>
		<thead>
			<tr>
				<th>ID</th>
				<th>BLOCK</th>
				<th>FLOOR</th>
				<th>ROOM</th>
			</tr>
		</thead>
		<tbody>
		<?php
		$no 	= 1;
		$total 	= 0;
		while ($row = mysqli_fetch_array($query))
		{
			
			echo '<tr><td>'.$row['id'].'</td>
					<td>'.$row['block'].'</td>
					<td>'.$row['floor'].'</td>
					<td>'.$row['room']. '</td>
					
				</tr>';
			$no++;
		}
		?>
		
		</tbody>
	</table>
	</div>
	</body>
	<script>
// Open and close the sidebar on medium and small screens
function w3_open() {
    document.getElementById("mySidebar").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}
function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}

// Change style of top container on scroll
window.onscroll = function() {myFunction()};
function myFunction() {
    if (document.body.scrollTop > 80 || document.documentElement.scrollTop > 80) {
        document.getElementById("myTop").classList.add("w3-card-4", "w3-animate-opacity");
        document.getElementById("myIntro").classList.add("w3-show-inline-block");
    } else {
        document.getElementById("myIntro").classList.remove("w3-show-inline-block");
        document.getElementById("myTop").classList.remove("w3-card-4", "w3-animate-opacity");
    }
}

// Accordions
function myAccordion(id) {
    var x = document.getElementById(id);
    if (x.className.indexOf("w3-show") == -1) {
        x.className += " w3-show";
        x.previousElementSibling.className += " w3-theme";
    } else { 
        x.className = x.className.replace("w3-show", "");
        x.previousElementSibling.className = 
        x.previousElementSibling.className.replace(" w3-theme", "");
    }
}
</script>
</Html>
