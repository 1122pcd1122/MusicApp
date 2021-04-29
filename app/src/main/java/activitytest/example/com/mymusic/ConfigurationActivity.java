package activitytest.example.com.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ConfigurationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_configer );

        //开启线程 初始化操作
        Thread configurationThread = new Thread ( () -> {
            //睡眠三秒
            try {
                Thread.sleep ( 3000 );
                //进入主activity
                Intent intent = new Intent (this,MainActivity.class);
                startActivity ( intent );
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        } );
        //开启线程
        configurationThread.start ();
    }

    @Override
    protected void onStop() {
        super.onStop ();
        this.finish ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
    }
}