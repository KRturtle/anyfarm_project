package com.example.anyfarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    //회원가입 액티비티

    //매개변수 선언
    //다이얼로그 선언
    //아이디 중복 확인 boolean 변수 선언
    private String userID;
    private String userPassword;
    private String userName;
    private String userPhone;
    private String userGender;
    private String userEmail;
    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //텍스트뷰
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText phoneText = (EditText) findViewById(R.id.phoneText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        //성별선택 버튼
        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton) findViewById(i);
                userGender = genderButton.getText().toString();
            }
        });

        //아이디 중복체크 버튼에 대한 이벤트 처리
        final Button validateButton = (Button) findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이디텍스트의 문자열 값 가져옴
                String userID = idText.getText().toString();
                //중복 값 확인
                if(validate)
                {
                    return;
                }
                //아이디 입력 유무 확인
                if(userID.equals(""))
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다!")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                //웹서버 통신을 통해 중복 확인
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //아이디 중복 값이 없을 경우
                            if(success)
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디 입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();

                                //중복에 대한 이벤트(아이디 값 고정, 회색처리, 중복변수 true설정)
                                idText.setEnabled(false);
                                validate = true;
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            //아이디 중복 값이 있을 경우
                            else
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                //volley api를 통한 http통신
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });
        //회원가입 버튼에 대한 이벤트 처리
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                String userPhone = phoneText.getText().toString();
                String userEmail = emailText.getText().toString();

                if (!validate)
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("중복 체크를 완료해주십시오!")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                //입력칸에 빈칸 유무 확인
                if (userID.equals("") | userPassword.equals("") || userName.equals("") || userPhone.equals("") ||  userEmail.equals("") || userGender.equals(""))
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸이 없도록 확인해주세요!")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                //회원가입이 정상적으로 진행될 때
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원등록이 완료되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                //인텐트를 사용하여 로그인 액티비티로 화면 전환
                                                Intent intent = new Intent();
                                                intent.setClass(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }
                            //회원가입 실패
                            else
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("회원등록에 실패했습니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                //volley api를 사용한 http 통신
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userName, userPhone, userGender, userEmail,responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
    //종료 후 다이얼로그 종료
    @Override
    protected void onStop()
    {
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
