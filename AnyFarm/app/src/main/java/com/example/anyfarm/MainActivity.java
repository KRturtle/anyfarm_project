package com.example.anyfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //텍스트뷰
        TextView nameText = (TextView) findViewById(R.id.mainNameText);
        TextView subText = (TextView) findViewById(R.id.subText);

        //버튼 뷰
        Button addBtn = (Button) findViewById(R.id.addAnyFarm);
        Button monitorBtn = (Button) findViewById(R.id.center_monitorButton);
        Button preBtn = (Button) findViewById(R.id.center_preferencesButton);

        //하단 이미지 버튼 뷰
        ImageButton dongyangBtn = (ImageButton) findViewById(R.id.dongyang);

        //싱글톤 인스턴스 호출
        nameText.setText("어서오세요!\n" + UserManager.getInstance().getGlobalUserId() + "님");
        subText.setText("애니팜은 당신의 관심을 필요로 합니다!");

        //화분 추가 버튼에 대한 이벤트 처리
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        AnyFarm_Register.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });

        //화분 모니터링 버튼에 대한 이벤트 처리
        monitorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        MonitorActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });

        //환경 설정 버튼에 대한 이벤트 처리
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        PreferencesActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });

        //하단 광고(학교 이미지)버튼에 대한 이벤트 처리
        //인텐트와 uri를 사용하여 액션 뷰 사용
        dongyangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dongyang.ac.kr/"));
                startActivity(intent);
            }
        });
    }

    //뒤로가기 버튼을 빠르게 2번 입력시 어플리케이션 종료
    private long lastTimeBackPressed;
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500)
        {
            finish();
            return;
        }
        Toast.makeText(this, "'돌아가기' 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();
    }
}

