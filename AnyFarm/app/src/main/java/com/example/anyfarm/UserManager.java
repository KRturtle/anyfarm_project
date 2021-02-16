package com.example.anyfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
//싱글톤 패턴
//아이디를 저장 후 인스턴스로 호출하여 사용
//따로 아이디를 인증할 필요 없이 편리하게 사용 가능
public class UserManager extends Application {
    private String globalUserId;

    public String getGlobalUserId() {
        return globalUserId;
    }

    public void setGlobalUserId(String globalUserId) {
        this.globalUserId = globalUserId;
    }

    private static UserManager instance = null;

    public static synchronized UserManager getInstance()
    {
        if (null==instance)
        {
            instance = new UserManager();
        }

        return instance;
    }
}
