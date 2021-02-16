<?php
    //php에서 발생하는 에러메세지 출력
    error_reporting(E_ALL);
    ini_set('display_errors',1);
    //데이터베이스 접속
    $con = mysqli_connect("localhost","spstlfdl","Ejdhfmsms7023", "spstlfdl" );

    //문자 인코딩 utf8
    mysqli_set_charset($con,"utf8");

    //6개의 매개변수
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userName = $_POST["userName"];
    $userPhone = $_POST["userPhone"];
    $userGender = $_POST["userGender"];
    $userEmail = $_POST["userEmail"];

    //입력받은 매개변수를 데이터베이스에 입력
    $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssssss", $userID, $userPassword, $userName, $userPhone, $userGender ,$userEmail);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    //json데이터를 반환
    echo json_encode($response);
 ?>
