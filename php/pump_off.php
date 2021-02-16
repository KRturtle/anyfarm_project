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
           $modelNumber = $_POST["modelNumber"];

           //get으로 받은 센서 값을 데이터베이스에 업데이트
           $sql = "UPDATE INFO SET w_pump='OFF' WHERE modelNumber='$modelNumber'";
           $result = mysqli_query($con, $sql);

           $result = array();
           $result["success"] = false;

           //올바르게 실행됬을 경우
           if ($result == true )
           {
               $result["success"] = true;
           }

           //json데이터를 반환
           echo json_encode($response);

           mysqli_close($con);
    }
 ?>
