package com.example.anyfarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class EditInformation extends AppCompatActivity {
    //회원정보 수정 액티비티

    //매개변수
    private String userID;
    private String userPassword;
    private String userName;
    private String userPhone;
    private String userGender;
    private String userEmail;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        //텍스트 뷰
        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText1);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText phoneText = (EditText) findViewById(R.id.phoneText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);

        //성별 확인 버튼
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

        //싱글톤 인스턴스로 아이디 값 호출
        final String userID = UserManager.getInstance().getGlobalUserId();
        //아이디는 DB에서 PK로 사용됨으로 수정이 불가능
        //수정 불가능 및 회색처리
        idText.setText(UserManager.getInstance().getGlobalUserId());
        idText.setEnabled(false);
        idText.setBackgroundColor(getResources().getColor(R.color.colorGray));

        //회원정보 수정 버튼에 대한 이벤트 처리
        Button editBtn = (Button) findViewById(R.id.editBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userPassword = passwordText.getText().toString();
                String userName = nameText.getText().toString();
                String userPhone = phoneText.getText().toString();
                String userEmail = emailText.getText().toString();

                //로그출력
                Log.d("test", "입력값 확인: " + userID);
                Log.d("test", "입력값 확인: " + userPassword);
                Log.d("test", "입력값 확인: " + userName);
                Log.d("test", "입력값 확인: " + userPhone);
                Log.d("test", "입력값 확인: " + userEmail);
                Log.d("test", "입력값 확인: " + userGender);

                //입력칸에 빈칸 유무 확인
                if (userID.equals("") || userPassword.equals("") || userName.equals("") || userPhone.equals("") ||  userEmail.equals("") || userGender.equals(""))
                {


                    AlertDialog.Builder builder= new AlertDialog.Builder(EditInformation.this);

                    dialog = builder.setMessage("빈 칸이 없도록 확인해주세요!")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                //회원정보 수정이 정상적으로 진행될 때
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            Log.d("test", "onResponse: "+success);
                            if(success)
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(EditInformation.this);
                                dialog = builder.setMessage("회원정보 수정이 완료되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intent = new Intent();
                                                intent.setClass(EditInformation.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }
                            //회원정보 수정 실패
                            else
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(EditInformation.this);
                                dialog = builder.setMessage("회원정보 수정에 실패했습니다.")

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
                //volley api를 사용하여 http 통신
                EditUserRequest editUserRequest = new EditUserRequest(userID, userPassword, userName, userPhone, userGender, userEmail,responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditInformation.this);
                queue.add(editUserRequest);
            }
        });
    }
    //종료
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
