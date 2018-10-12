<?php
    $con = mysqli_connect("localhost", "id7007652_appackers_user", "appackers", "id7007652_appackers_user");
    
    $username = $_POST["username"];
    $password = $_POST["password"];
    
    // statement to select the userModels from the database when username and password match with the given on above line
    $statement = mysqli_prepare($con, "SELECT * FROM userModels WHERE username = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $username, $password);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $email, $username, $password, $full_name, $nationality, $age, $about, $gender);
    
    $response = array();
    $response["success"] = false;  
    
    // get the details from the database
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["email"] = $email;
        $response["username"] = $username;
        $response["password"] = $password;
        $response["full_name"] = $full_name;
        $response["nationality"] = $nationality;
        $response["age"] = $age;
        $response["about"] = $about;
        $response["gender"] = $gender;
    }
    
    // encode in json format and send back to the app
    echo json_encode($response);
?>