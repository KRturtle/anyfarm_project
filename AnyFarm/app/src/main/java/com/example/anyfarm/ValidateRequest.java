package com.example.anyfarm;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {
    //웹서버 주소
    final static private String URL = "https://spstlfdl.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    //생성자
    public ValidateRequest(String userID, Response.Listener<String> listener) {
        //웹서버로 POST 방식으로 요청
        super(Method.POST,URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    //생성자에 입력된 아이디 값으로 중복 확인 요청
    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
