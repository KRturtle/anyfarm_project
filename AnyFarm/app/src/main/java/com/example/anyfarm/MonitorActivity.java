package com.example.anyfarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MonitorActivity extends AppCompatActivity {

    private AlertDialog dialog;

    //싱글톤 객체로 저장된 아이디 변수
    private String userID = UserManager.getInstance().getGlobalUserId();
    private String modelNumber;
    private String plantKind;
    ArrayAdapter<String> adapter;
    ArrayList<String> plantList = new ArrayList<>();
    //String[] data = new String[9];
    PlantData[] plantData = new PlantData[9];

    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        sp = (Spinner) findViewById(R.id.plantList);
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_layout, plantList);
        sp.setAdapter(adapter);

        //텍스트뷰
        final TextView tvalue_text = (TextView) findViewById(R.id.tvalue_text);
        final TextView avalue_text = (TextView) findViewById(R.id.avalue_text);
        final TextView svalue_text = (TextView) findViewById(R.id.svalue_text);
        final TextView lux_text = (TextView) findViewById(R.id.lux_text);
        final TextView led_text = (TextView) findViewById(R.id.led_text);
        final TextView pump_text = (TextView) findViewById(R.id.pump_text);

        //이미지뷰
        final ImageView img_tvalue = (ImageView) findViewById(R.id.img_tvalue);
        final ImageView img_avalue = (ImageView) findViewById(R.id.img_avalue);
        final ImageView img_svalue = (ImageView) findViewById(R.id.img_svalue);
        final ImageView img_lux = (ImageView) findViewById(R.id.img_lux);
        final ImageView img_led = (ImageView) findViewById(R.id.img_led);
        final ImageView img_pump = (ImageView) findViewById(R.id.img_pump);

        //버튼
        Button homeBtn = (Button) findViewById(R.id.mon_homeButton);
        Button plantRegBtn = (Button) findViewById(R.id.mon_add_AnyFarm_Button);
        Button monitorBtn = (Button) findViewById(R.id.mon_monitorButton);
        Button windowsBtn = (Button) findViewById(R.id.mon_windowsButton);

        LinearLayout ledBtn = (LinearLayout) findViewById(R.id.ledBtn);
        LinearLayout pumpBtn = (LinearLayout) findViewById(R.id.pumpBtn);

        //모니터 버튼 비활성화
        monitorBtn.setEnabled(false);

        final String[] ledStatus = new String[1];
        final String[] pumpStatus = new String[1];

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("test","클릭");

                modelNumber = plantData[position].getModelNumber().toString();
                int t1 = Integer.parseInt(plantData[position].getTvalue());
                int t2 = Integer.parseInt(plantData[position].getAvalue());
                int t3 = Integer.parseInt(plantData[position].getSvalue());
                int t4 = Integer.parseInt(plantData[position].getLux_value());
                Log.d("test", "t1: "+ t1);
                Log.d("test", "t2: "+ t2);
                Log.d("test", "t3: "+ t3);
                Log.d("test", "t4: "+ t4);

                //기온 조건문
                //기온이 10도 보다 높거나 40도 보다 낮은 경우 - 정상
                if(10 < t1 || t1 > 40)
                {
                    tvalue_text.setText(plantData[position].getTvalue() + " 도");
                    img_tvalue.setColorFilter(null); //초기화
                }
                else
                {
                    img_tvalue.setColorFilter(Color.parseColor("#CC0000"), PorterDuff.Mode.SRC_IN);
                    tvalue_text.setText(plantData[position].getTvalue() + " 도");
                    Log.d("t1_if_test_tvalue", "테스트, 색깔변경");
                }
                //밑에 3개는 조건문에 따른 이미지랑, 텍스트 설정 필요
                //대기습도는 70%보다 크거나 40%보다 낮으면 이미지 색을 적색으로 변경

                //대기습도 센서 조건부 처리

                if(40 < t2 || t1 > 80)
                {
                    avalue_text.setText(plantData[position].getAvalue() + " %");
                    img_avalue.setColorFilter(null); //초기화
                }
                else
                {
                    img_avalue.setColorFilter(Color.parseColor("#CC0000"), PorterDuff.Mode.SRC_IN);
                    avalue_text.setText(plantData[position].getAvalue() + " %");
                    Log.d("t2_if_test_avalue", "테스트, 색깔변경");
                }

                //토양습도 센서 조건부 처리
                //t3(토양습도 값)가 낮으면 낮을수록 축축한 것, 조정이 필요
                if (t3 < 700)
                {
                    svalue_text.setText("Good!");
                    img_svalue.setColorFilter(null); //초기화
                }
                else
                {
                    svalue_text.setText("Bad...");
                    img_svalue.setColorFilter(Color.parseColor("#CC0000"), PorterDuff.Mode.SRC_IN);
                    Log.d("t3_if_test_svalue", "테스트, 색깔변경");
                }

                //조도센서 조건부 처리
                //t4(조도 센서)가 60보다 높으면 밝음, 조정이 필요
                lux_text.setText(plantData[position].getLux_value());
                if (t4 < 60)
                {
                    lux_text.setText("Good!");
                    img_lux.setColorFilter(null); //초기화
                }
                else
                {
                    lux_text.setText("Bad...");
                    img_lux.setColorFilter(Color.parseColor("#CC0000"), PorterDuff.Mode.SRC_IN);
                    Log.d("t4_if_test_lux_value", "테스트, 색깔변경");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //디바이스 제어 버튼(LED, 워터펌프)에 대한 이벤트 처리
        ledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LED가 작동하지 않을 때
                if (led_text.getText().toString().equals("LED OFF"))
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(MonitorActivity.this);
                    dialog = builder.setMessage("LED를 작동합니다.")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    ledStatus[0] = "ledOn";
                                    Response.Listener<String> reStringListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try
                                            {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                                Log.d("test","통신성공");

                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    Led_On led_on = new Led_On(modelNumber,reStringListener);
                                    Log.d("test",modelNumber);
                                    RequestQueue queue = Volley.newRequestQueue(MonitorActivity.this);
                                    queue.add(led_on);
                                    led_text.setText("LED ON");
                                    img_led.setColorFilter(Color.parseColor("#99CC00"), PorterDuff.Mode.SRC_IN);
                                    //클릭하면 http request
                                    String serverAdress = "192.168.43.233:80";//서버의 ip와 port를 적어 준다.
                                    HttpRequestTask requestTask = new HttpRequestTask(serverAdress);
                                    requestTask.execute(ledStatus[0]);

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
                //LED가 작동할 때
                else
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(MonitorActivity.this);
                    dialog = builder.setMessage("LED를 중단합니다.")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    ledStatus[0] = "ledOff";
                                    Response.Listener<String> reStringListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try
                                            {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                                Log.d("test","통신성공");

                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    Led_Off led_off = new Led_Off(modelNumber,reStringListener);
                                    Log.d("test",modelNumber);
                                    RequestQueue queue = Volley.newRequestQueue(MonitorActivity.this);
                                    queue.add(led_off);
                                    led_text.setText("LED OFF");
                                    img_led.setColorFilter(null); //색 초기화
                                    //클릭하면 http request
                                    String serverAdress = "192.168.43.233:80";//서버의 ip와 port를 적어 준다.
                                    HttpRequestTask requestTask = new HttpRequestTask(serverAdress);
                                    requestTask.execute(ledStatus[0]);
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

            }
        });
        //펌프 작동 여부 버튼에 대한 이벤트 처리
        pumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //펌프가 작동하지 않을 때
                if (pump_text.getText().toString().equals("워터펌프 OFF"))
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(MonitorActivity.this);
                    dialog = builder.setMessage("워터펌프를 작동합니다.")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    pumpStatus[0] = "pumpOn";
                                    Response.Listener<String> reStringListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try
                                            {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                                Log.d("test","통신성공");

                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    Pump_On pump_on = new Pump_On(modelNumber,reStringListener);
                                    Log.d("test",modelNumber);
                                    RequestQueue queue = Volley.newRequestQueue(MonitorActivity.this);
                                    queue.add(pump_on);
                                    pump_text.setText("워터펌프 ON");
                                    img_pump.setColorFilter(Color.parseColor("#99CC00"), PorterDuff.Mode.SRC_IN);
                                    //클릭하면 http request
                                    String serverAdress = "192.168.43.233:80";//서버의 ip와 port를 적어 준다.
                                    HttpRequestTask requestTask = new HttpRequestTask(serverAdress);
                                    requestTask.execute(pumpStatus[0]);
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
                //펌프가 작동할 때
                else
                {
                    AlertDialog.Builder builder= new AlertDialog.Builder(MonitorActivity.this);
                    dialog = builder.setMessage("워터펌프를 중단합니다.")
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    pumpStatus[0] = "pumpOff";
                                    Response.Listener<String> reStringListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try
                                            {
                                                JSONObject jsonResponse = new JSONObject(response);
                                                boolean success = jsonResponse.getBoolean("success");
                                                Log.d("test","통신성공");

                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    Pump_off pump_off = new Pump_off(modelNumber,reStringListener);
                                    Log.d("test",modelNumber);
                                    RequestQueue queue = Volley.newRequestQueue(MonitorActivity.this);
                                    queue.add(pump_off);
                                    pump_text.setText("워터펌프 OFF");
                                    img_pump.setColorFilter(null); //색 초기화
                                    //클릭하면 http request
                                    String serverAdress = "192.168.43.233:80";//서버의 ip와 port를 적어 준다.
                                    HttpRequestTask requestTask = new HttpRequestTask(serverAdress);
                                    requestTask.execute(pumpStatus[0]);
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

            }
        });


        //하단 홈버튼에 대한 이벤트 처리
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MonitorActivity.this);
                dialog = builder.setMessage("메인 홈으로 돌아갑니다.")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(MonitorActivity.this, MainActivity.class);
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
                AlertDialog.Builder builder= new AlertDialog.Builder(MonitorActivity.this);
                dialog = builder.setMessage("화분 추가로 이동합니다")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(MonitorActivity.this, AnyFarm_Register.class);
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
                AlertDialog.Builder builder= new AlertDialog.Builder(MonitorActivity.this);
                dialog = builder.setMessage("환경설정으로 이동합니다")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.setClass(MonitorActivity.this, PreferencesActivity.class);
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
    protected void onStart() {
        super.onStart();
        BackgroundTask bt = new BackgroundTask();
        bt.execute();
        Log.d("비동기","실행 됨");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("모니터링","종료 됨");
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }

    class BackgroundTask extends  AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "https://spstlfdl.cafe24.com/anyfarm_list.php?userID=" + URLEncoder.encode(userID, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try
            {
                Log.d("test","리스트 초기화" );
                plantList.clear();
                JSONObject jsonObject = new JSONObject(result);
                Log.d("test","json오브젝트 가져옴" + result );
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    plantData[i] = new PlantData();
                    jsonObject = jsonArray.getJSONObject(i);
                    userID = jsonObject.getString("userID");
                    plantData[i].setUserID(jsonObject.getString("userID"));
                    modelNumber = jsonObject.getString("modelNumber");
                    plantData[i].setModelNumber(jsonObject.getString("modelNumber"));
                    plantKind = jsonObject.getString("plantKind");
                    plantData[i].setPlantKind(jsonObject.getString("plantKind"));
                    plantData[i].setTvalue(jsonObject.getString("tvalue"));
                    plantData[i].setAvalue(jsonObject.getString("avalue"));
                    plantData[i].setSvalue(jsonObject.getString("svalue"));
                    plantData[i].setLux_value(jsonObject.getString("lux_value"));
                    String spItem = userID + " / " + modelNumber + " / " + plantKind;
                    plantList.add(spItem);
                }

                adapter.notifyDataSetChanged();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try
            {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            return null;
        }
    }
    public class HttpRequestTask extends AsyncTask<String, Void, String> {
        private String serverAdress;

        public HttpRequestTask(String serverAdress) {
            this.serverAdress = serverAdress;
        }

        @Override
        protected String doInBackground(String... params) {
            String val = params[0];
            final String url = "http://"+serverAdress + "/" + val;

            //okHttp 라이브러리를 사용한다.
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                okhttp3.Response response = client.newCall(request).execute();
                Log.d("test", response.body().string());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }


}

