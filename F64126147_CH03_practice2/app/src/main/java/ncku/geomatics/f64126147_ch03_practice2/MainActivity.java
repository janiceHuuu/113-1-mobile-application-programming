package ncku.geomatics.f64126147_ch03_practice2;

import static java.lang.Integer.parseInt;
import static java.lang.Math.pow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void zero(View v){
        TextView t = findViewById(R.id.question);
        TextView l = findViewById(R.id.answer);
        String s = t.getText().toString();
        //防呆
        int a;
        a=s.length();
        if(a>0){
            t.setText(s+"0");
        }
        else{
            l.setText("第一個字元不可為0！");
        }
    }

    public void one(View v){
        TextView t = findViewById(R.id.question);
        String s = t.getText().toString();
        t.setText(s+"1");
    }

    public void clear(View v){
        TextView t = findViewById(R.id.question);
        t.setText("");
    }
    public void back(View v) {
        TextView t = findViewById(R.id.question);
        String s = t.getText().toString();
        int a;
        a=s.length();
        if(a>0){
            t.setText(s.substring(0,a-1));
        }
        else{
            TextView l = findViewById(R.id.answer);
            l.setText("顯示欄無數字，無法退格");
        }
    }

    public void calculate(View v) {
        TextView t = findViewById(R.id.question);
        TextView l = findViewById(R.id.answer);
        String s = t.getText().toString();
        int a,ten = 0;
        String sixteen;
        a = s.length();

        if(a>0){
            ten = Integer.parseInt(s,2);
            sixteen = Integer.toHexString(ten).toUpperCase();
            l.setText("十進位："+ten+"\n十六進位："+sixteen);
        }

        else{
            l.setText("顯示欄無數字，無法計算");
        }

    }
}