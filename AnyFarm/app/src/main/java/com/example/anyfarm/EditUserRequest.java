package com.example.anyfarm;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EditUserRequest extends StringRequest{
    //웹서버 주소
    final static private String URL = "https://spstlfdl.cafe24.com/EditUserData.php";
    private Map<String, String> parameters;

    //생성자
    public EditUserRequest(String userID, String userPassword, String userName, String userPhone, String userGender, String userEmail, Response.Listener<String> listener) {
        //웹서버로 POST 방식으로 요청
        super(Method.POST,URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userPhone", userPhone);
        parameters.put("userGender", userGender);
        parameters.put("userEmail", userEmail);
    }

    //생성자에 입력된 데이터를 통해 회원정보 수정 요청
    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }
}
