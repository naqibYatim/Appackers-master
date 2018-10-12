<?php
    $con = mysqli_connect("localhost", "id7007652_appackers_user", "appackers", "id7007652_appackers_user");

    $email = $_POST["email"];
    $username = $_POST["username"];
    $password = $_POST["password"];
    $full_name = $_POST["full_name"];
    $nationality = $_POST["nationality"];
    $age = $_POST["age"];
    $about = $_POST["about"];
    $gender = $_POST["gender"];

    // statements these details into the userModels table in db
    $statement = mysqli_prepare($con, "INSERT INTO userModels (email, username, password, full_name, nationality, age, about, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "siss", $email, $username, $password, $full_name, $nationality, $age, $about, $gender);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    // registered successfully
    echo json_encode($response);
?>