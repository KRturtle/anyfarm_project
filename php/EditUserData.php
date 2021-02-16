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

    //입력받은 매개변수로 데이터베이스를 수정
    $statement = mysqli_query($con, "UPDATE USER SET userPassword='$userPassword', userName='$userName', userPhone='$userPhone', userGender='$userGender', userEmail='$userEmail' WHERE userID='$userID'");

    $response = array();
    $response["success"] = false;

    //올바르게 실행됬을 경우
    if ($statement == true )
    {
        $response["success"] = true;
    }

    //json데이터를 반환
    echo json_encode($response);
 ?>
