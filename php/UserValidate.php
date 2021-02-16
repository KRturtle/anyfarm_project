<?php
    //php에서 발생하는 에러메세지 출력
    error_reporting(E_ALL);
    ini_set('display_errors',1);
    //데이터베이스 접속
    $con = mysqli_connect("localhost","spstlfdl","Ejdhfmsms7023", "spstlfdl" );

    //문자 인코딩 utf8
    mysqli_set_charset($con,"utf8");

    //매개변수
    $userID = $_POST["userID"];

    $statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);

    //일치하는 아이디가 없을 경우 true 반환
    $response = array();
    $response["success"] = true;

    //일치하는 아이디가 있을 경우 false 반환
    while(mysqli_stmt_fetch($statement)){
      $response["success"] = false;
      $response["userID"] = $userID;
    }

    //json데이터를 반환
    echo json_encode($response);

 ?>
