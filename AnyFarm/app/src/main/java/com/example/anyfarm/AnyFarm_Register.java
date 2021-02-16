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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class AnyFarm_Register extends AppCompatActivity {
    //화분등록 액티비티
    //문자열 변수 및 다이얼로그 객체 선언
    private String userID;
    private String modelNumber;
    private String plantKind;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyfarm_register);

        //텍스트 뷰 연동
        final EditText idText = (EditText) findViewById(R.id.addAnyFarm_idText);
        final EditText modelNumberText = (EditText) findViewById(R.id.modelNumber);
        final EditText plantKindText = (EditText) findViewById(R.id.plantKind);

        //버튼 뷰 연동
        Button addAnyFarmRegBtn = (Button) findViewById(R.id.anyFarmRegButton);
        Button homeBtn = (Button) findViewById(R.id.homeButton);
        Button plantRegBtn = (Button) findViewById(R.id.bottom_add_AnyFarm_Button);
        Button monitorBtn = (Button) findViewById(R.id.bottom_monitorButton);
        Button windowsBtn = (Button) findViewById(R.id.windowsButton);

        //아이디 인스턴트 호출
        //아이디텍스트의 값을 수정 할 수 없도록 설정
        //텍스트 뷰의 배경색을 회색으로 설정
        //화분등록 버튼을 클릭할 수 없도록 설정
        idText.setText(UserManager.getInstance().getGlobalUserId());
        idText.setEnabled(false);
        idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
        plantRegBtn.setEnabled(false);

        //등록완료 버튼에 대한 이벤트 처리
        addAnyFarmRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //텍스트 뷰에 입력된 문자열 값을 가져옴
                String userID = idText.getText().toString();
                String modelNumber = modelNumberText.getText().toString();
                String plantKind = plantKindText.getText().toString();
                //DB 컬럼이 NULL을 사용할 수 없기 때문에 0과 OFF을 입력해줌
                String tvalue = "0";
                String avalue = "0";
                String svalue = "0";
                String lux_value = "0";
                String led = "OFF";
                String w_pump = "OFF";

                //로그 출력
                Log.e("확인", userID);
                Log.e("확인", modelNumber);
                Log.e("확인", plantKind);
                Log.e("확인", tvalue);
                Log.e("확인", avalue);
                Log.e("확인", svalue);
                Log.e("확인", lux_value);
                Log.e("확인", led);
                Log.e("확인", w_pump);

                //입력칸에 빈칸 유무 확인
                if (userID.equals("") | modelNumber.equals("") || plantKind.equals(""))
                {
                    //빈칸이 있을 경우 다이얼로그 출력
                    AlertDialog.Builder builder= new AlertDialog.Builder(AnyFarm_Register.this);
                    dialog = builder.setMessage("빈 칸이 없도록 확인해주세요!")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                //화분등록이 정상적으로 진행될 때
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            //JSON 반환 값이 success일 경우 성공
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success)
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(AnyFarm_Register.this);
                                dialog = builder.setMessage("애니팜등록이 완료되었습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                //인텐트를 활용하여 메인메뉴 액티비티로 화면 전환
                                                Intent intent = new Intent();
                                                intent.setClass(AnyFarm_Register.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create();
                                dialog.show();
                            }
                            //회원가입 오류
                            else
                            {
                                AlertDialog.Builder builder= new AlertDialog.Builder(AnyFarm_Register.this);
                                dialog = builder.setMessage("애니팜 등록에 실패했습니다.\n다시 한번 확인해주세요!")
                                        .setNegativeButton("예",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        //예외처리 문
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                //volley 객체에 매개변수 입력 후 웹서버로 전송
                AnyFarm_Register_Request registerRequest = new AnyFarm_Register_Request(userID, modelNumber, plantKind,
                        tvalue, avalue, svalue, lux_value, led, w_pump,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AnyFarm_Register.this);
                queue.add(registerRequest);
            }
        });

        //하단 버튼들에 대한 이벤트 처리(화면전환 용)
        //하단 홈버튼에 대한 이벤트 처리
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(AnyFarm_Register.this);
                dialog = builder.setMessage("메인 홈으로 돌아갑니다.")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(AnyFarm_Register.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                dialog.show();
            }
        });

        //하단 모니터링 버튼에 대한 이벤트 처리
        monitorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(AnyFarm_Register.this);
                dialog = builder.setMessage("현재 작성 중인 내용을 모두 취소하고\n" +
                        "모니터링 화면으로 이동합니다")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(AnyFarm_Register.this, MonitorActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                dialog.show();
            }
        });

        //하단 환경설정 버튼에 대한 이벤트 처리
        windowsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(AnyFarm_Register.this);
                dialog = builder.setMessage("현재 작성 중인 내용을 모두 취소하고\n" +
                        "환경설정 화면으로 이동합니다")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(AnyFarm_Register.this, PreferencesActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                dialog.show();
            }
        });
    }
    //액티비티 종료에 대한 다이얼로그 종료
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
