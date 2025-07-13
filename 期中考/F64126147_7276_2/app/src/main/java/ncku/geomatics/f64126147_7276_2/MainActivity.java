package ncku.geomatics.f64126147_7276_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
implements View.OnClickListener, View.OnLongClickListener,
        AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener,
        DialogInterface.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add_minus = findViewById(R.id.add_minus);
        Button plus = findViewById(R.id.plus);
        Button minu = findViewById(R.id.minus);
        add_minus.setOnClickListener(this);
        add_minus.setOnLongClickListener(this);
        plus.setOnClickListener(this);
        minu.setOnClickListener(this);

        Spinner sp = findViewById(R.id.spinner);
        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("0");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinnerAdapter);
        sp.setOnItemSelectedListener(this);

        ListView lv = findViewById(R.id.lv);
        ArrayList<String> listItems= new ArrayList<>();
        listItems.add("現場付款");
        listItems.add("線上刷卡");
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);

    }
    int rock_number =0,look_number=0;
    @Override
    public void onClick(View v) {
        TextView tv_in = findViewById(R.id.tv_in);
        TextView tv_on = findViewById(R.id.tv_ou);
        if(v.getId() == R.id.add_minus){
            rock_number++;
        }
        if(v.getId() == R.id.plus){
            look_number++;
        }
        if(v.getId() == R.id.minus){
            look_number--;
            if(look_number<0){
                look_number=0;
            }
        }
        tv_in.setText(String.valueOf(rock_number));
        tv_on.setText(String.valueOf(look_number));
        if(look_number+rock_number>6 || look_number+rock_number<0){
            Toast t = Toast.makeText(this,"總張數小於0或大於6張",Toast.LENGTH_SHORT);
            t.show();
        }
        if (look_number+rock_number >= 0 && look_number+rock_number <= 6){
            Spinner sp = findViewById(R.id.spinner);
            ArrayList<String> spinnerItems = new ArrayList<>();
            spinnerItems.add("0");
            spinnerItems.add("1");
            spinnerItems.add("2");
            spinnerItems.add("3");
            spinnerItems.add("4");
            spinnerItems.add("5");
            spinnerItems.add("6");
            for(int i = spinnerItems.size() - 1; i > look_number+rock_number; i--){
                spinnerItems.remove(i);
            }
            if((look_number+rock_number)/3==1){
                spinnerItems.remove(0);
            }
            if((look_number+rock_number)/3==2){
                spinnerItems.remove(0);
                spinnerItems.remove(0);
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(spinnerAdapter);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        TextView tv_in = findViewById(R.id.tv_in);
        if(v.getId() == R.id.add_minus){
            rock_number--;
            if(rock_number<0){
                rock_number=0;
            }
        }
        tv_in.setText(String.valueOf(rock_number));
        if(look_number+rock_number>6 || look_number+rock_number<0){
            Toast t = Toast.makeText(this,"總張數小於0或大於6張",Toast.LENGTH_SHORT);
            t.show();
        }
        if (look_number+rock_number >= 0 && look_number+rock_number <= 6){
            Spinner sp = findViewById(R.id.spinner);
            ArrayList<String> spinnerItems = new ArrayList<>();
            spinnerItems.add("0");
            spinnerItems.add("1");
            spinnerItems.add("2");
            spinnerItems.add("3");
            spinnerItems.add("4");
            spinnerItems.add("5");
            spinnerItems.add("6");
            for(int i = spinnerItems.size() - 1; i >= look_number+rock_number; i--){
                spinnerItems.remove(i);
            }
            if((look_number+rock_number)/3==1){
                spinnerItems.remove(0);
            }
            if((look_number+rock_number)/3==2){
                spinnerItems.remove(0);
                spinnerItems.remove(0);
            }

            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(spinnerAdapter);
        }
        return true;
    }

    int bon =0;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){
            bon=0;
        }
        if(position==1){
            bon=1;
        }
        if(position==2){
            bon=2;
        }
        if(position==3){
            bon=3;
        }
        if(position==4){
            bon=4;
        }
        if(position==5){
            bon=5;
        }
        if(position==6){
            bon=6;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    int total,rock_money,look_money,bon_money;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CompoundButton cb = findViewById(R.id.cb_ch);
        ArrayList<String> listItems= new ArrayList<>();
        listItems.add("現場付款");
        listItems.add("線上刷卡");

        if (rock_number > 0) {
            int fullPriceTickets = rock_number / 2+rock_number % 2; // 正常价格票的数量
            int discountTickets = rock_number - fullPriceTickets; // 八折票的数量

            // 总金额计算: 正常票价为 2000，第二张八折为 2000 * 0.8
            rock_money = (int) ((fullPriceTickets * 2000) + (discountTickets * 2000 * 0.8));
        }
        if(rock_number == 0){
            rock_money=0;
        }

        if (look_number > 0) {
            int fullPriceTickets = look_number / 2 +look_number % 2; // 正常价格票的数量
            int discountTickets = look_number - fullPriceTickets; // 八折票的数量

            // 总金额计算: 正常票价为 2000，第二张八折为 2000 * 0.8
            look_money = (int) ((fullPriceTickets * 1000) + (discountTickets * 1000 * 0.8));
        }
        if(look_number == 0){
            look_money=0;
        }

        if(look_number+rock_number>=3){
            if(bon>=(look_number+rock_number)/3){
                bon_money = (bon-(look_number+rock_number)/3)*500;
            }
            else{
                bon_money = bon*500;
            }
        }
        if(look_number+rock_number<3){
            bon_money=bon*500;
        }

        total = rock_money+look_money+bon_money;
        if(cb.isChecked()){
            total *=0.5;
        }
        if(position ==0){
            if(total<8000){
                total+=50;
            }
        }
        if(position==1){
            if(rock_number!=0 && look_number !=0){
                total *=0.95;
            }
        }

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("購票訊息");
        ad.setMessage("付款方式為"+listItems.get(position)+"，總金額為$"+total+"。");
        ad.setPositiveButton("確定",this).setNegativeButton("取消",this);
        ad.setCancelable(false);
        ad.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        TextView tvshow = findViewById(R.id.tvshow);
        if(which == DialogInterface.BUTTON_POSITIVE){
            tvshow.setText("總金額為$"+total);
        }
        if(which == DialogInterface.BUTTON_NEGATIVE){
            tvshow.setText("取消訂單");
        }
    }
}