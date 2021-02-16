package com.example.anyfarm;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//화분 등록
public class AnyFarm_Register_Request extends StringRequest {
    //웹서버 주소
    final static private String URL = "https://spstlfdl.cafe24.com/AnyFarm_Register.php";
    private Map<String, String> parameters;

    //생성자
    public AnyFarm_Register_Request(String userID, String modelNumber, String plantKind,
            String tvalue, String avalue, String svalue, String lux_value, String led, String w_pump, Response.Listener<String> listener) {
        //웹서버로 POST 방식으로 요청
        super(Method.POST,URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("modelNumber", modelNumber);
        parameters.put("plantKind", plantKind);
        parameters.put("tvalue", tvalue);
        parameters.put("avalue", avalue);
        parameters.put("svalue", svalue);
        parameters.put("lux_value", lux_value);
        parameters.put("led", led);
        parameters.put("w_pump", w_pump);
    }

    //생성자에 입력된 데이터를 통해 회원가입 요청
    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
