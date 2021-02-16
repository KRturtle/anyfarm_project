<?php
    //php에서 발생하는 에러메세지 출력
    error_reporting(E_ALL);
    ini_set('display_errors',1);
    //문자 인코딩 utf8

    //데이터베이스 접속
    $con = mysqli_connect("localhost","spstlfdl","Ejdhfmsms7023", "spstlfdl" );
    mysqli_set_charset($con,"utf8");
    //아이디를 요청받음
    $userID = $_GET["userID"];

    //해당 userID에서 조회한 데이터를 오름차순으로 가져옴
    $result = mysqli_query($con, "SELECT * FROM INFO WHERE userID = '$userID' ORDER BY modelNumber ASC;");
    $response = array();

    //데이터를 배열에 저장
    while ($row = mysqli_fetch_array($result)) {
      // code...
      array_push($response, array("userID"=>$row[0], "modelNumber"=>$row[1],
        "plantKind"=>$row[2], "tvalue"=>$row[3], "avalue"=>$row[4], "svalue"=>$row[5],
        "lux_value"=>$row[6], "led"=>$row[7], "w_pump"=>$row[8]));
    }

    //json형태로 데이터를 반환
    echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
    mysqli_close($con);
?>
