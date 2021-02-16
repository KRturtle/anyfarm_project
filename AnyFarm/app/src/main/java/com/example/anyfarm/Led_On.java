package com.example.anyfarm;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Led_On extends StringRequest {
    //웹서버 주소
    final static private String URL = "https://spstlfdl.cafe24.com/led_on.php";
    private Map<String, String> parameters;

    //생성자
    public Led_On(String modelNumber, Response.Listener<String> listener) {
        //웹서버로 POST 방식으로 요청
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("modelNumber", modelNumber);
    }

    //생성자에 입력된 데이터를 통해 led 작동
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
