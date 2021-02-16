#include <String.h>
#include <SoftwareSerial.h>
#include <DHT.h>
#include <ArduinoJson.h>

//환경변화시 ssid, paswword 변경 후 업로드 할 것
////////////////////////////////////////
#define SSID        "x1"
#define PASSWORD    "1q2w3e4r5"
#define SERVERIP   "203.245.44.20"
////////////////////////////////////////

SoftwareSerial mySerial(2, 3); /* RX:D10, TX:D9 */

//센서 4종 핀 할당
#define DHTPIN 6 //DHT센서 핀 
#define DHTTYPE DHT22 //센서모델 정의
int cds = A4; //조도센서
int soil = A5; //토양습도센서

String led_state;
String pump_state;
String arr[5];

//제어 모듈 핀 할당
int led = 9;
int pump = 13;

//제품번호 할당
String modelNumber = "0001";

//다비아스 주소를 저장할 배열 선언
DHT dht(DHTPIN, DHTTYPE);


void setup(void)
{
  //시리얼 포트 초기화
  Serial.begin(9600);
  /////////////////////////////////////////////////////////////////////////
  Serial.setTimeout(5000);
  mySerial.begin(9600);
  Serial.println("ESP8266 connect");

  boolean connected = false;
  for (int i = 0; i < 10; i++)
  {
    if (connectWiFi())
    {
      connected = true;
      break;
    }
  }
  //연결이 될 때까지 반복요청
  if (!connected) {
    while (1);
  }
  delay(5000);

  mySerial.println("AT+CIPMUX=0");
  ///////////////////////////////////////////////////////////////////////////
  dht.begin();
  pinMode(led, OUTPUT);
  delay(1000); //1초 대기
}


void loop(void)
{
  int t = dht.readTemperature(); //기온 값
  int a = dht.readHumidity(); //대기습도 값
  int s = analogRead(soil); //토양습도 값
  int l = analogRead(cds); //조도센서 값

  //정수, 실수형을 문자열로 캐스팅
  String tvalue = String(t);
  String svalue = String(s);
  String avalue = String(a);
  String lux_value = String(l);


  //테스트 시리얼 모니터 출력
  Serial.print("기온 센서 값 : ");
  Serial.println(tvalue);
  Serial.print("대기습도 센서 값 : ");
  Serial.println(avalue);
  Serial.print("토양습도 센서 값 : ");
  Serial.println(svalue);
  Serial.print("조도센서 값 : ");
  Serial.println(lux_value);

  String cmd = "AT+CIPSTART=\"TCP\",\"";
  cmd += SERVERIP;
  cmd += "\",80";
  Serial.println(cmd);
  mySerial.println(cmd);
  if (mySerial.find("Error"))
  {
    Serial.println( "TCP connect error" );
    return;
  }

  cmd = "GET http://spstlfdl.cafe24.com/anyfarm_update.php?modelNumber=" + modelNumber + "&tvalue=" + tvalue + "&avalue=" + avalue + "&svalue=" + svalue + "&lux_value=" + lux_value + "\r\n";
  mySerial.print("AT+CIPSEND=");
  mySerial.println(cmd.length());

  Serial.println(cmd);

  if (mySerial.find(">"))
  {
    Serial.print(">");
  } else
  {
    mySerial.println("AT+CIPCLOSE");
    Serial.println("connect timeout");
    delay(1000);
    return;
  }

  mySerial.print(cmd);
  delay(2000);
  //Serial.find("+IPD");
  while (Serial.available())
  {
    char c = Serial.read();
    mySerial.write(c);
    if (c == '\r') mySerial.print('\n');
  }

  while (mySerial.available())
  {
    String rcv = mySerial.readStringUntil('/r');
    Serial.println(rcv);
  }
  Serial.println("====");
  led_state = arr[0];
  pump_state = arr[1];

  Serial.println();
  Serial.println("led상태:" + led_state);
  Serial.println("펌프상태:" + pump_state);
  Serial.println();
  delay(1000);
}


boolean connectWiFi()
{
  //mySerial.println("AT+CWMODE=1");

  String cmd = "AT+CWJAP=\"";
  cmd += SSID;
  cmd += "\",\"";
  cmd += PASSWORD;
  cmd += "\"";
  mySerial.println(cmd);
  Serial.println(cmd);
  delay(3000);

  if (mySerial.find("OK"))
  {
    Serial.println("OK, Connected to WiFi.");
    return true;
  }
  else
  {
    Serial.println("Can not connect to the WiFi.");
    return false;
  }
}
