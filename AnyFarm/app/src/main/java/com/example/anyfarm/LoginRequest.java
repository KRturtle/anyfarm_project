package com.example.anyfarm;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    //웹서버 주소
    final static private String URL = "https://spstlfdl.cafe24.com/UserLogin.php";
    private Map<String, String> parameters;

    //생성자
    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        //웹서버로 POST 방식으로 요청
        super(Method.POST,URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }

    //생성자에 입력된 데이터를 매핑 후 로그인 요청
    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
