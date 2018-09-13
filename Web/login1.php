<?php

    session_start();
    if(array_key_exists("id", $_SESSION)){
        header("location:home.php");
    }


    $error = "";    

   /* if (array_key_exists("logout", $_GET)) {
        
        unset($_SESSION);
        //setcookie("id", "", time() - 60*60);
        //$_COOKIE["id"] = "";  
        
    } else if ((array_key_exists("id", $_SESSION) && $_SESSION['id'])) {
        
        header("Location: firstpage.php");
        
    }*/

    if (array_key_exists("submit", $_POST)) {
        
        $link = mysqli_connect("localhost","id5720309_tempusername","qwertyuiop","id5720309_eventbase");
        
        if (mysqli_connect_error()) {
            
            die ("Database Connection Error");
            
        }
        
        
        
        if (!$_POST['username']) {
            
            $error .= "A username is required<br>";
            
        } 
        
        if (!$_POST['password']) {
            
            $error .= "A password is required<br>";
            
        } 
        
        if ($error != "") {
            
            $error = "<p>There were error(s) in your form:</p>".$error;
            
        } else {
            
            /*if ($_POST['signUp'] == '1') {
            
                $query = "SELECT id FROM `user` WHERE username = '".mysqli_real_escape_string($link, $_POST['username'])."' LIMIT 1";

                $result = mysqli_query($link, $query);

                if (mysqli_num_rows($result) > 0) {

                    $error = "That username is taken.";

                } else {

                    $query = "INSERT INTO `user` (`username`, `password`) VALUES ('".mysqli_real_escape_string($link, $_POST['username'])."', '".mysqli_real_escape_string($link, $_POST['password'])."')";

                    if (!mysqli_query($link, $query)) {

                        $error = "<p>Could not sign you up - please try again later.</p>";

                    } else {

                        $query = "UPDATE `user` SET password = '".md5(md5(mysqli_insert_id($link)).$_POST['password'])."' WHERE id = ".mysqli_insert_id($link)." LIMIT 1";

                        mysqli_query($link, $query);

                        $_SESSION['id'] = mysqli_insert_id($link);

                        if ($_POST['stayLoggedIn'] == '1') {

                            setcookie("id", mysqli_insert_id($link), time() + 60*60*24*365);

                        } 

                        header("Location: firstpage.php");

                    }

                } 
                
            } */if ($_POST['signUp'] == '0') {
                    
                    $query = "SELECT * FROM `user` WHERE username = '". $_POST['username']."'";
                    $result = mysqli_query($link, $query);
                
                    $row = mysqli_fetch_array($result);
                
                    if (isset($row)) {
                        
                        $hashedPassword = md5(md5($row['id']).$_POST['password']);
                        
                        if ($hashedPassword == $row['password']) {
                            
                            $_SESSION['id'] = $row['id'];
                            
                            /*if ($_POST['stayLoggedIn'] == '1') {

                                setcookie("id", $row['id'], time() + 60*60*24*365);

                            } */

                            header("Location: home.php");
                                
                        } else {
                            
                            $error = "That email/password combination could not be found.".$row;
                            
                        }
                        
                    } else {
                        
                        $error = "That email/password combination could not be found.".$_POST['username'];
                        
                    }
                    
                }
            
        }
        
        
    }


?>
<head><link rel="stylesheet" type="text/css" href="w3.css"></head>
<body>
<div id="error"><?php echo $error; ?></div>
<!--
<form method="post">

    <input type="text" name="username" placeholder="Your username">
    
    <input type="password" name="password" placeholder="Password">
    
    <input type="checkbox" name="stayLoggedIn" value=1>
    
    <input type="hidden" name="signUp" value="1">
        
    <input type="submit" name="submit" value="Sign Up!">

</form>
-->
    <header class="w3-container w3-teal ">
  <h1>Administrator Login</h1>
</header>
<div class="w3-container w3-half w3-margin-top w3-display-middle">
<form method="post"  class="w3-container w3-card-4 w3-padding-16" >

    <input class="w3-input" type="text" name="username" placeholder="Your username">
    
    <input class="w3-input" type="password" name="password" placeholder="Password">
    
    <!<input type="checkbox" name="stayLoggedIn" value=1>
    
    <input type="hidden" name="signUp" value="0">
        
    <input class="w3-button w3-section w3-teal w3-ripple" type="submit" name="submit" value="Log In!">

</form></div></body>