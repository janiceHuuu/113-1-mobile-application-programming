package ncku.geomatics.f64126147_ch02_practice1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //JAVA函式寫在主程式之前和之後都沒差別
    //on在JAVA中是動作
    int frontsize = 30;
    int b=0;

    public int test1(int a){
        TextView t = findViewById(R.id.word);
        if(a==0){
            t.setText("請輸入姓名");
            return 0;
        }
        else{
            return 1;
        }
    }

    public int test2(int c){
        TextView t = findViewById(R.id.word);
        if(c==0){
            t.setText("請按下確定");
            return 0;
        }
        else{
            return 1;
        }
    }

    public void confirm(View v){
        TextView t = findViewById(R.id.word);
        EditText et = findViewById(R.id.edit);
        if(test1(et.getText().toString().length())==1){
            String s = et.getText().toString();
            t.setText(s+"，您好！"); //JAVA字串相加同C++
            b=1;
        }
        else{
            b=0;
        }
    }

    public void bigger(View v){
        TextView t = findViewById(R.id.word);
        EditText et = findViewById(R.id.edit);
        if(test1(et.getText().toString().length())==1 && test2(b)==1) {
            frontsize += 5;
            t.setTextSize(frontsize);
        }
    }

    public void smaller(View v){
        TextView t = findViewById(R.id.word);
        EditText et = findViewById(R.id.edit);
        if(test1(et.getText().toString().length())==1 && test2(b)==1){
            frontsize -= 5;
            t.setTextSize(frontsize);
        }
    }

    public void red(View V){
        TextView t = findViewById(R.id.word);
        EditText et = findViewById(R.id.edit);
        if(test1(et.getText().toString().length())==1 && test2(b)==1){
            t.setTextColor(Color.RED);
        }
    }

    public void blue(View V){
        TextView t = findViewById(R.id.word);
        EditText et = findViewById(R.id.edit);
        if(test1(et.getText().toString().length())==1 && test2(b)==1){
            t.setTextColor(Color.BLUE);
        }
    }

    public void bold(View V){
        TextView t = findViewById(R.id.word);
        EditText et = findViewById(R.id.edit);
        if(test1(et.getText().toString().length())==1 && test2(b)==1){
            t.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }

    public void italic(View V){
        TextView t = findViewById(R.id.word);
        EditText et = findViewById(R.id.edit);
        if(test1(et.getText().toString().length())==1 && test2(b)==1) {
            t.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
    }

    public void clear(View v){
        EditText et = findViewById(R.id.edit);
        if(test1(et.getText().toString().length())==1) {
            et.setText("");
            b=0;
        }

    }

    public void zero(View v){
        TextView t = findViewById(R.id.word);
        EditText et = findViewById(R.id.edit);
        if(test1(et.getText().toString().length())==1 && test2(b)==1) {
            String s = et.getText().toString();
            t.setText(s+"，您好！");
            t.setTextColor(Color.BLACK);
            t.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            t.setTextSize(30);
            b=0;
        }
    }

}
