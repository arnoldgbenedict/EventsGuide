<?php
		session_start();
	if(array_key_exists("id", $_SESSION)){
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
				FROM event_details';
		$sql1='SELECT * FROM place';
		$sql2='SELECT * FROM faculty_table';
				
		$query = mysqli_query($conn, $sql);
		$query1 = mysqli_query($conn, $sql1);
		$query2 = mysqli_query($conn, $sql2);
		if (!$query || !$query1 ||!$query2) {
			die ('SQL Error: ' . mysqli_error($conn));
		}
		?>
<html>
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
					text-align: center;
				}
				.dropdown {
			    position: relative;
			    display: inline-block;
				}

				.dropdown-content {
				    display: none;
				    position: absolute;
				    background-color: #f9f9f9;
				    min-width: 260px;
				    overflow: auto;
				    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
				    padding: 12px 16px;
				    z-index: 1;
				    max-height:100px;
				}
				.dropdown-content p:hover{
				    background-color: grey;
				}
		</style>
	</head>
		
	<body>
		<nav class="w3-sidebar w3-bar-block w3-collapse w3-animate-left w3-card" style="z-index:3;width:250px;" id="mySidebar">
			<a class="w3-bar-item w3-button w3-hide-large w3-large" href="javascript:void(0)" onclick="w3_close()">Close <i class="fa fa-remove"></i></a>
			<a class="w3-bar-item w3-button w3-border-bottom w3-large" href="#"><img src="https://christuniversity.in/images/logo.jpg" style="width:80%;"></a>
			
		    <a class="w3-bar-item w3-button " href="home.php">Home</a>
			<a class="w3-bar-item w3-button" href="insert_venue.php">Add venue</a>
			<a class="w3-bar-item w3-button" href="insert_faculty.php">Add faculty</a>
			<a class="w3-bar-item w3-button w3-teal" href="#">Add event</a>
			<a class="w3-bar-item w3-button w3-dark-grey" href="logoutt.php">LogOut!</a>
		</nav>
		<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" id="myOverlay"></div>

<div class="w3-main" style="margin-left:250px;">

<div id="myTop" class="w3-container w3-top w3-theme w3-large">
  <p><i class="fa fa-bars w3-button w3-teal w3-hide-large w3-xlarge" onclick="w3_open()"></i>
  <span id="myIntro" class="w3-hide">Book-E: Adding an event</span></p>
</div>

<header class="w3-container w3-theme" style="padding:64px 32px">
  <h1 class="w3-xxxlarge">Add Event </h1>
</header>
<div class="w3-container" style="padding:32px">
		<div class="w3-container w3-half w3-margin-top  ">
		<form name="f1" action="insertintoeventdetails.php", method="post" id="f2" class="w3-container w3-card-4 w3-padding-16">
				<table>
					<tr>
						<td>Event Name:</td>
						<td><input type="text" class="w3-input" name="event_name" size=45 required></td>
					</tr>
					<tr>
						<td>Start date</td>
						<td><input type="date" name="start_date" class="w3-input" required></td>
					</tr>
					<tr>
						<td>End date:</td>
						<td><input type="date" class="w3-input" name="end_date"></td>
					</tr>
					<tr>
						<td>Start time:</td>
						<td><input type="time" class="w3-input" name="start_time"></td>
					</tr>
					<tr>
						<td>End time:</td>
						<td><input type="time" class="w3-input" name="end_time"></td>
					</tr>
					<tr>
						<td>Venue:</td>
						<div class="dropdown">
          <td><input type="text" class="w3-input" name="venue" id="tb">
          <div class="dropdown-content" id="dd">
            <?php
            $i=0;
                while ($row = mysqli_fetch_array($query1))
                {
                    
                    echo '<p onclick="fsel(' .$i. ')" style="cursor: pointer;">B'.$row['block'].'F'.$row['floor'].'R'.$row['room'].'</p>';
                    $i++;
                }
                ?>
            </div>
            </td>
        </div>
					</tr>
					<tr>
						<td>Type:</td>
						<td><input type="text" class="w3-input" name="type"></td>
					</tr>
					<tr></tr>
					<tr>
						<td>Description:</td>
						<td><textarea rows="4" cols="50"  class="w3-input w3-border" name="description" size=45 wrap="hard">Enter the description here </textarea></td>
					</tr>
					<tr>
						<td>Faculty-Incharge:</td>
						<div class="dropdown"><td>
  <input type="text" class="w3-input" name="faculty_incharge" id="tb1">
  <div class="dropdown-content" id="dd1">
    <?php
    $i=0;
        while ($row = mysqli_fetch_array($query2))
        {
            
            echo '<p onclick="fsel1(' .$i. ')" style="cursor: pointer;">'.$row['Faculty_name'].'</p>';
            $i++;
        }
        ?>
  </div>
</td>
</div>
					</tr>
					<tr id="tagRow">
						<td>Tags:</td>
						<td><input type="text" class="w3-input" name="tag[]">
						<button type="button" id="addTag" class="w3-button w3-section w3-dark-grey w3-ripple">Add Tag!</button></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="Submit" form="f2" onclick="post" class="w3-button w3-section w3-teal w3-ripple" ></td>
					</tr>
				</table>
		</form></div>
		<!--<fieldset><legend>Delete an event</legend>
			<table class="data-table">
				<caption class="title">EVENT TABLE</caption>
				<thead>
					<tr>
						<th>EVENT NAME</th>
					</tr>
				</thead>
				<tbody>
				<?php
				$no 	= 1;
				$total 	= 0;
				while ($row = mysqli_fetch_array($query))
				{
					
					echo '<tr><td>'.$row['Event_name'].'</td>
						</tr>';
					$no++;
				}
				?>
				
				</tbody>
			</table>
		</fieldset>
	</div>-->
	</body>
	
	<script src="jquery-3.3.1.js"></script>
	<script type="text/javascript">
		function addTag(){
		  var input = document.createElement('input');
		  input.type = 'text';
		  input.name = 'tag[]';
		  return input;
		}
		
		var form = document.getElementById('tagRow');
		document.getElementById('addTag').addEventListener('click', function(e) {
		 // form.appendChild(addTag());
		 $("#tagRow").before('<tr><td></td><td><input type="text" class="w3-input" name="tag[]" id="tag[]"></td></tr>');
		 
		});
	</script>

    <script type="text/javascript">
    var flist = [];
$('#dd p').each(function (i, e) {
  flist.push($(e).text());
});
document.getElementById('tb').addEventListener('focus',function(){
    $('#dd').css("display","block");
});

document.getElementById('tb').addEventListener('keyup',function(){
    document.getElementById('dd').innerHTML = '';
    for (var i = 0; i < flist.length; i++) { 
        var j = flist[i].toLowerCase().indexOf(document.getElementById('tb').value.toLowerCase());
        if(j >= 0){
            document.getElementById('dd').innerHTML = document.getElementById('dd').innerHTML + '<p onclick="fsel(' + i + ')" style="cursor: pointer;">' + flist[i]  + '</p>';
        }
    }
});
function clickedOut(){
    $('#dd').css("display","none");
}
function fsel(i){
    $('#tb').attr("value", i+1);
    $('#tb').val(flist[i]);
    console.log($('#tb').attr("value"));
    $('#dd').css("display","none");
}
    </script>
<script type="text/javascript">
    var flist1 = [];
$('#dd1 p').each(function (i, e) {
  flist1.push($(e).text());
});
document.getElementById('tb1').addEventListener('focus',function(){
    $('#dd1').css("display","block");

});

document.getElementById('tb1').addEventListener('keyup',function(){
    document.getElementById('dd1').innerHTML = '';
    for (var i = 0; i < flist1.length; i++) { 
        var j = flist1[i].toLowerCase().indexOf(document.getElementById('tb1').value.toLowerCase());
        if(j >= 0){
            document.getElementById('dd1').innerHTML = document.getElementById('dd1').innerHTML + '<p onclick="fsel1(' + i + ')" style="cursor: pointer;">' + flist1[i]  + '</p>';
        }
    }
});
function clickedOut1(){
    $('#dd1').css("display","none");
}
function fsel1(i){
    $('#tb1').attr("value", i+1);
    $('#tb1').val(flist1[i]);
    console.log($('#tb1').attr("value"));
    $('#dd1').css("display","none");
}
    </script>

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
<script>
$(function() {
    // Find the form with id='well-form'
    $('#f2').submit(function() {
        $.ajax({
            url: this.action,
            type: this.method,
            data: $(this).serialize(),
            success: function(result) {
               alert('Post was successful! 1 row affected');
            },
            error: function(result) {
               alert('Post was not successful!');
            }
        });
        // return false to cancel the form post
        // since javascript will perform it with ajax
        return false;
    });
});
</script>
</html>

