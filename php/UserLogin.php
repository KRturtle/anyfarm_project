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
    $userPassword = $_POST["userPassword"];

    //해당 아이디와 비밀번호가 데이터베이스에 존재하는지 조회
    $statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);

    $response = array();
    $response["success"] = false;

    //일치하는 경우가 있을 경우
    while (mysqli_stmt_fetch($statement))
    {
      $response["success"] = true;
      $response["userID"] = $userID;
    }

    //json데이터를 반환
    echo json_encode($response);
?>
