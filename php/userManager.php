<<?php
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

  //아이디와 비밀번호
  $statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userID = ? AND userPassword = ?");
  mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
  mysqli_stmt_execute($statement);
  mysqli_stmt_store_result($statement);

  //로그인에 성공할 때 유저의 정보를 저장
  while (mysqli_stmt_fetch($statement))
  {
    $response["userID"] = $userID;
    $response["userPassword"] = $userPassword;
    $response["userName"] = $userPassword;
    $response["userPhone"] = $userName;
    $response["userGender"] = $userGender;
    $response["userEmail"] = $userEmail;
  }

  //json데이터를 반환
  echo json_encode($response);
 ?>
