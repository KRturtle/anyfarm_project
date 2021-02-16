<?php
    //php에서 발생하는 에러메세지 출력
    error_reporting(E_ALL);
    ini_set('display_errors',1);
    //데이터베이스 접속
    $con = mysqli_connect("localhost","spstlfdl","Ejdhfmsms7023", "spstlfdl" );

    //문자 인코딩 utf8
    mysqli_set_charset($con,"utf8");

    //9개의 매개변수
    $userID = $_POST["userID"];
    $modelNumber = $_POST["modelNumber"];
    $plantKind = $_POST["plantKind"];
    $tvalue = $_POST["tvalue"];
    $avalue = $_POST["avalue"];
    $svalue = $_POST["svalue"];
    $lux_value = $_POST["lux_value"];
    $led = $_POST["led"];
    $w_pump = $_POST["w_pump"];

    //해당 아이디와 비밀번호가 데이터베이스에 존재하는지 조회
    $statement = mysqli_prepare($con, "SELECT * FROM INFO WHERE modelNumber = ?");
    mysqli_stmt_bind_param($statement, "s", $modelNumber);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);

    $response = array();
    $response["success"] = true;

    //일치하는 경우가 있을 경우
    while (mysqli_stmt_fetch($statement))
    {
      $response["success"] = false;
    }

    if($response["success"] == true)
    {
        $statement = mysqli_prepare($con, "INSERT INTO INFO VALUES (?,?,?,?,?,?,?,?,?)");
        mysqli_stmt_bind_param($statement, "sssssssss", $userID, $modelNumber, $plantKind, $tvalue, $avalue,
         $svalue, $lux_value, $led, $w_pump);
        mysqli_stmt_execute($statement);
    }

    echo json_encode($response);
 ?>
