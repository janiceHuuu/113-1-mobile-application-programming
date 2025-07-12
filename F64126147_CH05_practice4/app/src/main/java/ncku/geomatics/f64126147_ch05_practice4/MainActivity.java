package ncku.geomatics.f64126147_ch05_practice4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener
{
    int money=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg1 = findViewById(R.id.rg1);
        RadioGroup rg2 = findViewById(R.id.rg2);
        RadioGroup rg3_1 = findViewById(R.id.rg3_1);
        RadioGroup rg3_2 = findViewById(R.id.rg3_2);
        rg1.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
        rg3_1.setOnCheckedChangeListener(this);
        rg3_2.setOnCheckedChangeListener(this);

        CheckBox pot  = findViewById(R.id.pot);
        CheckBox ice  = findViewById(R.id.ice);
        pot.setOnCheckedChangeListener(this);
        ice.setOnCheckedChangeListener(this);

        money=0;
        TextView tv = findViewById(R.id.tv);
        String s="";
        ImageView pic_ham = findViewById(R.id.pic_ham);
        ImageView pic_chi = findViewById(R.id.pic_chi);
        ImageView pic_red = findViewById(R.id.pic_red);
        ImageView pic_cof = findViewById(R.id.pic_cof);
        RadioButton ham = findViewById(R.id.ham);
        RadioButton chi = findViewById(R.id.chi);
        RadioButton red = findViewById(R.id.red);
        RadioButton cof = findViewById(R.id.cof);
        RadioButton half = findViewById(R.id.half);
        RadioButton no = findViewById(R.id.no);
        RadioButton les = findViewById(R.id.les);
        RadioButton gone = findViewById(R.id.gone);
        if(ham.isChecked()){
            s="餐點為漢堡";
            pic_ham.setVisibility(View.VISIBLE);
            pic_chi.setVisibility(View.GONE);
            money+=40;
        }
        if(chi.isChecked()){
            s="餐點為炸雞";
            pic_chi.setVisibility(View.VISIBLE);
            pic_ham.setVisibility(View.GONE);
            money+=50;
        }
        if(red.isChecked()){
            s +="搭配紅茶";
            pic_red.setVisibility(View.VISIBLE);
            pic_cof.setVisibility(View.GONE);
            money+=20;
        }
        if(cof.isChecked()){
            s+="搭配咖啡";
            pic_cof.setVisibility(View.VISIBLE);
            pic_red.setVisibility(View.GONE);
            money+=30;
        }
        if(half.isChecked()){
            s+="（半糖";
        }
        if(no.isChecked()){
            s+="（無糖";
        }
        if(les.isChecked()){
            s+="、少冰）";
        }
        if(gone.isChecked()){
            s+="、去冰）";
        }
        if(pot.isChecked() == false && ice.isChecked() == false ){
            tv.setText(s+"，共計"+money+"元");
        }
        else{
            tv.setText(s);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        TextView tv = findViewById(R.id.tv);
        String s= tv.getText().toString();
        String r = Integer.toString(money);
        ImageView pic_ham = findViewById(R.id.pic_ham);
        ImageView pic_chi = findViewById(R.id.pic_chi);
        ImageView pic_red = findViewById(R.id.pic_red);
        ImageView pic_cof = findViewById(R.id.pic_cof);
        RadioButton ham = findViewById(R.id.ham);
        RadioButton chi = findViewById(R.id.chi);
        RadioButton red = findViewById(R.id.red);
        RadioButton cof = findViewById(R.id.cof);
        RadioButton half = findViewById(R.id.half);
        RadioButton no = findViewById(R.id.no);
        RadioButton les = findViewById(R.id.les);
        RadioButton gone = findViewById(R.id.gone);
        CompoundButton pot = findViewById(R.id.pot);
        CompoundButton ice = findViewById(R.id.ice);
        if(ham.isChecked()){
            s = s.replace("炸雞","漢堡");
        }
        if(chi.isChecked()){
            s = s.replace("漢堡","炸雞");
        }
        if(red.isChecked()){
            s = s.replace("咖啡","紅茶");

        }
        if(cof.isChecked()){
            s = s.replace("紅茶","咖啡");
        }
        if(half.isChecked()){
            s = s.replace("無糖","半糖");
        }
        if(no.isChecked()){
            s = s.replace("半糖","無糖");
        }
        if(les.isChecked()){
            s = s.replace("去冰","少冰");
        }
        if(gone.isChecked()){
            s = s.replace("少冰","去冰");
        }

        switch (i){
            case R.id.ham:
                pic_ham.setVisibility(View.VISIBLE);
                pic_chi.setVisibility(View.GONE);
                money+=40;
                money-=50;
                break;
            case R.id.chi:
                pic_chi.setVisibility(View.VISIBLE);
                pic_ham.setVisibility(View.GONE);
                money+=50;
                money-=40;
                break;
            case R.id.red:
                pic_red.setVisibility(View.VISIBLE);
                pic_cof.setVisibility(View.GONE);
                money+=20;
                money-=30;
                break;
            case R.id.cof:
                pic_cof.setVisibility(View.VISIBLE);
                pic_red.setVisibility(View.GONE);
                money+=30;
                money-=20;
                break;

        }
        s = s.replace(r,Integer.toString(money));
        tv.setText(s);
    }

    ArrayList<CompoundButton> selected = new ArrayList<>();
    @Override
    public void onCheckedChanged(CompoundButton ButtonView, boolean b) {
        ImageView pic_pot = findViewById(R.id.pic_pot);
        ImageView pic_ice = findViewById(R.id.pic_ice);
        TextView tv = findViewById(R.id.tv);
        String r = Integer.toString(money);
        CompoundButton pot = findViewById(R.id.pot);
        CompoundButton ice = findViewById(R.id.ice);
        if(b == true){
            selected.add(ButtonView);
            if(ButtonView.getText().toString().equals("薯條")){
                money+=20;
                pic_pot.setVisibility(View.VISIBLE);
            }
            if(ButtonView.getText().toString().equals("聖代")){
                money+=30;
                pic_ice.setVisibility(View.VISIBLE);
            }
        }
        else{
            selected.remove(ButtonView);
            if(ButtonView.getText().toString().equals("薯條")){
                money-=20;
                pic_pot.setVisibility(View.GONE);
            }
            if(ButtonView.getText().toString().equals("聖代")){
                money-=30;
                pic_ice.setVisibility(View.GONE);
            }
        }
        String s="";
        for(CompoundButton cb :selected){
            if(s.length()>0){
                s+="和"+cb.getText();
            }
            else{
                s+= cb.getText();
            }
        }
        String a = tv.getText().toString();
        a = a.replace("薯條","");
        a = a.replace("聖代","");
        a = a.replace("和","");
        a = a.replace("，加點","");
        a = a.replace("，共計","");
        a = a.replace("元","");
        a = a.replace(r,"");
        if(pot.isChecked() == false && ice.isChecked() == false){
            tv.setText(a+"，共計"+money+"元");
        }
        if(s.length()>0){
            tv.setText(a+"，加點"+s+"，共計"+money+"元");
        }
    }
}