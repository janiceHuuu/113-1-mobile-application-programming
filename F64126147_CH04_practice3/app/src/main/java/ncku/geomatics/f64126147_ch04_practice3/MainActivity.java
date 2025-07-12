package ncku.geomatics.f64126147_ch04_practice3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button p = findViewById(R.id.plus);
        p.setOnClickListener(this);
        p.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                plus(v);
                plus(v);
                quake(v);
                return true;
            }
        });
        Button m = findViewById(R.id.minus);
        m.setOnClickListener(this);
        m.setOnLongClickListener((new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                minus(v);
                minus(v);
                quake(v);
                return true;
            }
        }));
    }

    public void plus(View v){
        TextView n = findViewById(R.id.number);
        EditText i = findViewById(R.id.mult);
        String s = n.getText().toString();
        TextView qk = findViewById(R.id.quaketest);
        int b = i.getText().toString().length();
        if(b>0){
            int a = Integer.parseInt(s)+1;
            n.setText(""+a);
            qk.setText("");
        }
        else{
            qk.setText("請輸入倍數");
        }
    }

    public void minus(View v){
        TextView n = findViewById(R.id.number);
        EditText i = findViewById(R.id.mult);
        String s = n.getText().toString();
        TextView qk = findViewById(R.id.quaketest);
        int b = i.getText().toString().length();
        if(b>0){
            int a = Integer.parseInt(s)-1;
            n.setText(""+a);
        }
        else{
            qk.setText("請輸入倍數");
        }
    }

    public void quake(View v){
        TextView n = findViewById(R.id.number);
        EditText i = findViewById(R.id.mult);
        TextView qk = findViewById(R.id.quaketest);
        String s = n.getText().toString();
        String s2 = i.getText().toString();
        if(i.getText().toString().length()>0) {
            int a = Integer.parseInt(s);
            int b = Integer.parseInt(s2);
            Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (a % b == 0) {
                vi.vibrate(3000);
                //qk.setText("震動發生");
            }
            else {
                vi.cancel();
                //qk.setText("不震動");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.plus){
            plus(v);
            quake(v);
        }
        else if(v.getId() == R.id.minus){
            minus(v);
            quake(v);
        }
    }
}