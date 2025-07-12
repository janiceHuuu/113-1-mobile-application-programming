package ncku.geomatics.f64126147_ch08_09_practice7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity
implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button con = findViewById(R.id.confirm);
        Button can = findViewById(R.id.cancel);
        con.setOnClickListener(this);
        can.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.confirm){
            EditText ed_name = findViewById(R.id.name);
            String restaurant = ed_name.getText().toString();
            if(restaurant.length()==0){
                Toast t = Toast.makeText(this,"輸入欄為空，請重新輸入或按下取消", Toast.LENGTH_SHORT);
                t.show();
            }
            else{
                Intent it = new Intent();
                it.putExtra("new_re",restaurant);
                setResult(RESULT_OK,it);
                finish();
            }
        }
        if(v.getId() == R.id.cancel){
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}