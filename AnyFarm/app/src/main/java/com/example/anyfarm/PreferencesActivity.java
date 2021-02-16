package com.example.anyfarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class PreferencesActivity extends AppCompatActivity {

    String[] list_menu = {"회원정보 수정", "개발자 정보"};
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        ListView listView = (ListView) findViewById(R.id.window_list);
        ListViewAdapter adapter;
        adapter = new ListViewAdapter();
        listView.setAdapter(adapter);

        Button homeBtn = (Button) findViewById(R.id.pre_homeButton);
        Button plantRegBtn = (Button) findViewById(R.id.pre_add_AnyFarm_Button);
        Button monitorBtn = (Button) findViewById(R.id.pre_monitorButton);
        Button windowsBtn = (Button) findViewById(R.id.pre_windowsButton);

        final ImageButton dongyangBtn = (ImageButton) findViewById(R.id.dongyang);

        windowsBtn.setEnabled(false);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_list_userdata_black_36dp), "회원정보 수정");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_developer_black_36dp),"개발자 정보");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);
                Drawable iconDr = item.getIcon();
                String titlestr = item.getTitleStr();

                if(position == 0)
                {
                    Intent intent = new Intent(
                            getApplicationContext(), // 현재 화면의 제어권자
                            EditInformation.class); // 다음 넘어갈 클래스 지정
                    startActivity(intent); // 다음 화면으로 넘어간다
                }
                else if(position == 1)
                {
                    Intent intent = new Intent(
                            getApplicationContext(), // 현재 화면의 제어권자
                            DeveloperGuide.class); // 다음 넘어갈 클래스 지정
                    startActivity(intent); // 다음 화면으로 넘어간다
                }
            }
        });


        //하단 홈버튼에 대한 이벤트 처리
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(PreferencesActivity.this);
                dialog = builder.setMessage("메인 홈으로 돌아갑니다.")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(PreferencesActivity.this, MainActivity.class);
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

        //하단 화분추가 버튼에 대한 이벤트 처리
        plantRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(PreferencesActivity.this);
                dialog = builder.setMessage("화분추가로 이동합니다")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(PreferencesActivity.this, AnyFarm_Register.class);
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
                AlertDialog.Builder builder= new AlertDialog.Builder(PreferencesActivity.this);
                dialog = builder.setMessage("모니터링 화면으로 이동합니다")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(PreferencesActivity.this, MonitorActivity.class);
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
