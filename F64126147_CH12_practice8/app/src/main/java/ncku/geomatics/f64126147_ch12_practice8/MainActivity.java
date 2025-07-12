package ncku.geomatics.f64126147_ch12_practice8;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
implements SensorEventListener, View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE); //取得系統服務
        Sensor s1 = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //取得「感測器加速度計」
        sm.registerListener(this, s1, SensorManager.SENSOR_DELAY_GAME);

        ImageView iv_star = findViewById(R.id.iv_star);
        ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) iv_star.getLayoutParams();
        p.horizontalBias = 1.0f;
        p.verticalBias=0.0f;
        iv_star.setLayoutParams(p); //回存給iv

        ImageView iv_pika = findViewById(R.id.iv_pika);
        ConstraintLayout.LayoutParams p3 = (ConstraintLayout.LayoutParams) iv_pika.getLayoutParams();
        p.horizontalBias = 0.0f;
        p.verticalBias=0.8f;
        iv_pika.setLayoutParams(p3); //回存給iv

        TextView tv_show = findViewById(R.id.tv_show);
        ConstraintLayout.LayoutParams p2 = (ConstraintLayout.LayoutParams)tv_show.getLayoutParams();
        p2.horizontalBias = 0.5f;
        p2.verticalBias=0.9f;
        tv_show.setLayoutParams(p2); //回存給iv

        ImageView iv_bomb = findViewById(R.id.bomb);
        ConstraintLayout.LayoutParams p4 = (ConstraintLayout.LayoutParams) iv_bomb.getLayoutParams();
        p4.horizontalBias = 0.5f;
        p4.verticalBias=0.8f;
        iv_bomb.setLayoutParams(p4); //回存給iv

        Button again = findViewById(R.id.again);
        Button next = findViewById(R.id.nextto);
        again.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        again.setOnClickListener(this);
        next.setOnClickListener(this);

        score = 0;
        tv_show.setText("分數："+score);
    }
    float velocityX = 0f;
    float velocityY = 0f;
    long lastUpdateTime = -1;
    int score = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        ImageView iv_pika = findViewById(R.id.iv_pika);
        TextView tv_show = findViewById(R.id.tv_show);
        ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) iv_pika.getLayoutParams();
        float x = event.values[0];
        float y = (float) ((-1.0)*event.values[1]);

        // 計算時間間隔
        long currentTime = System.currentTimeMillis();
        if (lastUpdateTime != -1 || (currentTime-lastUpdateTime)>100) {
            float deltaTime = (currentTime - lastUpdateTime) / 1000f; // 秒為單位

            // 利用加速度計算速度變化量 (v = u + at)
            velocityX += x * deltaTime;
            velocityY += y * deltaTime;

            // 計算位置變化量 (d = vt)
            float deltaX = velocityX * deltaTime/100;
            float deltaY = velocityY * deltaTime/70;


            if(p.horizontalBias + deltaX>=1.0f || p.horizontalBias + deltaX<=0.0f){
                velocityX=0f;
            }
            if(p.verticalBias + deltaY>=0.8f || p.verticalBias + deltaY<=0.0f){
                velocityY=0f;
            }
            // 調整 bias 來控制 ImageView 的位置
            p.horizontalBias = Math.max(0.0f,Math.min(1.0f,p.horizontalBias + deltaX));
            p.verticalBias = Math.max(0.0f,Math.min(0.8f,p.verticalBias + deltaY));
            iv_pika.setLayoutParams(p);
            eating(1);
        }
        else{
            score = 0;
            tv_show.setText("分數："+score);
        }
        // 更新上次時間
        lastUpdateTime = currentTime;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void eating(int a){
        ImageView iv_pika = findViewById(R.id.iv_pika);
        ImageView iv_star = findViewById(R.id.iv_star);
        ImageView iv_bomb = findViewById(R.id.bomb);
        TextView tv_show = findViewById(R.id.tv_show);

        int[] location1 = new int[2];
        iv_pika.getLocationOnScreen(location1);
        int x_p = location1[0];
        int y_p = location1[1];

        int[] location2 = new int[2];
        iv_star.getLocationOnScreen(location2);
        int x_s = location2[0];
        int y_s = location2[1];

        int[] location3 = new int[2];
        iv_bomb.getLocationOnScreen(location3);
        int x_b = location3[0];
        int y_b = location3[1];

        if(abs(x_p-x_s) <= 100 && abs(y_p-y_s) <= 100){
            score++;
            move(iv_star);
        }
        if(iv_bomb.getVisibility()==View.VISIBLE){
            if(abs(x_p-x_b) <= 100 && abs(y_p-y_b) <= 100){
                score--;
                move(iv_bomb);
            }
        }
        String s="";
        if(score>=4){
            s="。恭喜通關，請選擇去到另一關或重新遊玩這一關";
            Button again = findViewById(R.id.again);
            Button next = findViewById(R.id.nextto);
            again.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            score =4;
        }

        tv_show.setText("分數："+score+s);
    }

    public void move(ImageView iv){
        ImageView iv_star = findViewById(R.id.iv_star);
        ImageView iv_bomb = findViewById(R.id.bomb);

        if(iv.getId() == R.id.iv_star){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) iv_star.getLayoutParams();
            boolean isOverlap=true;
            // 重複檢查直到 iv_bomb 不與 iv_star 重疊
            while (isOverlap) {
                // 隨機位置
                params.horizontalBias = (float) Math.random();
                params.verticalBias = (float) Math.random() * 0.8f;

                // 取得 iv_bomb 和 iv_star 的位置
                float x_star = params.horizontalBias;
                float y_star = params.verticalBias;
                int[] locationBomb = new int[2];
                iv_bomb.getLocationOnScreen(locationBomb);
                int x_bomb = locationBomb[0];
                int y_bomb = locationBomb[1];

                // 檢查 iv_bomb 是否與 iv_star 重疊
                if (abs(x_star - x_bomb) > 100 && abs(y_star - y_bomb) > 100) {
                    iv_star.setLayoutParams(params);
                    isOverlap = false; // 不重疊，跳出循環
                }
            }
        }

        if(iv.getId() == R.id.bomb){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) iv_bomb.getLayoutParams();

            boolean isOverlap=true;
            // 重複檢查直到 iv_bomb 不與 iv_star 重疊
            while (isOverlap) {
                // 隨機位置
                params.horizontalBias = (float) Math.random();
                params.verticalBias = (float) Math.random() * 0.8f;

                // 取得 iv_bomb 和 iv_star 的位置
                int[] locationStar = new int[2];
                iv_star.getLocationOnScreen(locationStar);
                int x_star = locationStar[0];
                int y_star = locationStar[1];

                float x_bomb = params.horizontalBias;
                float y_bomb = params.verticalBias;

                // 檢查 iv_bomb 是否與 iv_star 重疊
                if (abs(x_star - x_bomb) > 100 && abs(y_star - y_bomb) > 100) {
                    iv_bomb.setLayoutParams(params);
                    isOverlap = false; // 不重疊，跳出循環
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        score = 0;
        velocityX = 0f;
        velocityY = 0f;
        lastUpdateTime = -1;
        Button again = findViewById(R.id.again);
        Button next = findViewById(R.id.nextto);
        ImageView iv_bomb = findViewById(R.id.bomb);
        again.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        if(v.getId() == R.id.again){
            iv_bomb.setVisibility(View.GONE);
        }
        if(v.getId() == R.id.nextto){
            iv_bomb.setVisibility(View.VISIBLE);
        }
    }
}