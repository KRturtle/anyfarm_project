<?php
    //데이터베이스 접속
    $con = mysqli_connect("localhost","spstlfdl","Ejdhfmsms7023", "spstlfdl" );
    if (mysqli_connect_errno())
    {
         echo "MySQL 연결 오류: " . mysqli_connect_error();
         exit;
    }
     else
    {
         $modelNumber = $_GET["modelNumber"];
         $tvalue = $_GET["tvalue"];
         $avalue = $_GET["avalue"];
         $svalue = $_GET["svalue"];
         $lux_value = $_GET["lux_value"];

         //get으로 받은 센서 값을 데이터베이스에 업데이트
         $sql = "UPDATE INFO SET tvalue='$tvalue', avalue='$avalue', svalue='$svalue', lux_value='$lux_value' WHERE modelNumber='$modelNumber'";
         $result = mysqli_query($con, $sql);

         $sql2 = mysqli_query($con, "SELECT * FROM INFO WHERE modelNumber = '$modelNumber'");
         $response = array();

         while ($row = mysqli_fetch_array($sql2)) { //해당 열의 7,8번째 데이터를 배열상태로 저장
           array_push($response, array("led"=>$row[7], "w_pump"=>$row[8]));
         }
         echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
         mysqli_close($con);
    }
 ?>
