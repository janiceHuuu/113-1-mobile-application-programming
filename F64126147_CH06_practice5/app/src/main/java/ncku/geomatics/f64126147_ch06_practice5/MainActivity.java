package ncku.geomatics.f64126147_ch06_practice5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener,
        TextView.OnEditorActionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);
        Spinner sp = findViewById(R.id.sp_sta);
        sp.setOnItemSelectedListener(this);
        ListView lv = findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
        CompoundButton reg = findViewById(R.id.reg);
        CompoundButton old = findViewById(R.id.old);
        CompoundButton love = findViewById(R.id.love);
        CompoundButton fre = findViewById(R.id.fre);
        CompoundButton chi = findViewById(R.id.chi);
        CompoundButton gro = findViewById(R.id.gro);
        reg.setOnCheckedChangeListener(this);
        old.setOnCheckedChangeListener(this);
        love.setOnCheckedChangeListener(this);
        fre.setOnCheckedChangeListener(this);
        chi.setOnCheckedChangeListener(this);
        gro.setOnCheckedChangeListener(this);
        EditText reg_edit = findViewById(R.id.reg_text);
        EditText old_edit = findViewById(R.id.old_text);
        EditText love_edit = findViewById(R.id.love_text);
        EditText fre_edit = findViewById(R.id.fre_text);
        EditText chi_edit = findViewById(R.id.chi_text);
        EditText gro_edit = findViewById(R.id.gro_text);
        reg_edit.setOnEditorActionListener(this);
        old_edit.setOnEditorActionListener(this);
        love_edit.setOnEditorActionListener(this);
        fre_edit.setOnEditorActionListener(this);
        chi_edit.setOnEditorActionListener(this);
        gro_edit.setOnEditorActionListener(this);
    }
    //全域
    int begin,end;
    int total,reg_money,old_money,love_money,chi_money,gro_money,free_money;
    String s="";

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String[] n = {"台北站","桃園站","新竹站","台中站","嘉義站","台南站","高雄站"};
        switch (checkedId){
            case R.id.nor:
                n = new String[] {"桃園站","新竹站","台中站","嘉義站","台南站","高雄站"};
                break;

            case R.id.sou:
                n = new String[]{"台北站","桃園站","新竹站","台中站","嘉義站","台南站"};
                break;
        }
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, n);
        ad.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line); //讓List有行距
        Spinner sp = findViewById(R.id.sp_sta);
        sp.setAdapter(ad);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView ab = (TextView) view; //spinner不用轉
        String EndStation = ab.getText().toString();
        switch(EndStation){
            case "台北站":
                end=0;
                break;
            case "桃園站":
                end=1;
                break;
            case "新竹站":
                end=2;
                break;
            case "台中站":
                end=3;
                break;
            case "嘉義站":
                end=4;
                break;
            case "台南站":
                end=5;
                break;
            case "高雄站":
                end=6;
                break;
        }
        regular(begin,end);
        EditText fre_edit = findViewById(R.id.fre_text);
        int actionId = R.id.fre_text;
        onEditorAction(fre_edit, actionId, null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ListView lv = findViewById(R.id.lv);
        ArrayList<String> al = new ArrayList<>();
        RadioButton north = findViewById(R.id.nor);
        RadioButton south = findViewById(R.id.sou);
        TextView tv = findViewById(R.id.tvshow);
        if(north.isChecked()){
            al.add("台北站");
            al.add("桃園站");
            al.add("新竹站");
            al.add("台中站");
            al.add("嘉義站");
            al.add("台南站");
            al.add("高雄站");
            for(int i=6;i>position;i--){
                //tv.setText(""+position);
                al.remove(i);
            }
            begin = position+1;
        }
        if(south.isChecked()){
            al.add("台北站");
            al.add("桃園站");
            al.add("新竹站");
            al.add("台中站");
            al.add("嘉義站");
            al.add("台南站");
            al.add("高雄站");
            for(int i=position;i>=0;i--){
                al.remove(i);
            }
            begin = position;
        }
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,al);
        lv.setAdapter(ad);
    }

    int regular_money;
    public void regular(int a,int b){
        if(a>b){
            int temp = a;
            a = b;
            b = temp;
        }
        switch(a){
            case 0:
                switch(b){
                    case 1:
                        regular_money = 160;
                        break;
                    case 2:
                        regular_money = 290;
                        break;
                    case 3:
                        regular_money = 700;
                        break;
                    case 4:
                        regular_money = 1080;
                        break;
                    case 5:
                        regular_money = 1350;
                        break;
                    case 6:
                        regular_money = 1490;
                        break;
                }
                break;
            case 1:
                switch(b){
                    case 2:
                        regular_money = 130;
                        break;
                    case 3:
                        regular_money = 540;
                        break;
                    case 4:
                        regular_money = 920;
                        break;
                    case 5:
                        regular_money = 1190;
                        break;
                    case 6:
                        regular_money = 1330;
                        break;
                }
                break;
            case 2:
                switch(b){
                    case 3:
                        regular_money = 410;
                        break;
                    case 4:
                        regular_money = 790;
                        break;
                    case 5:
                        regular_money = 1060;
                        break;
                    case 6:
                        regular_money = 1200;
                        break;
                }
                break;
            case 3:
                switch(b){
                    case 4:
                        regular_money = 380;
                        break;
                    case 5:
                        regular_money = 650;
                        break;
                    case 6:
                        regular_money = 790;
                        break;
                }
                break;
            case 4:
                switch(b){
                    case 5:
                        regular_money = 280;
                        break;
                    case 6:
                        regular_money = 410;
                        break;
                }
                break;
            case 5:
                switch(b){
                    case 6:
                        regular_money = 140;
                        break;
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CompoundButton reg = findViewById(R.id.reg);
        CompoundButton old = findViewById(R.id.old);
        CompoundButton love = findViewById(R.id.love);
        CompoundButton fre = findViewById(R.id.fre);
        CompoundButton chi = findViewById(R.id.chi);
        CompoundButton gro = findViewById(R.id.gro);
        EditText reg_edit = findViewById(R.id.reg_text);
        EditText old_edit = findViewById(R.id.old_text);
        EditText love_edit = findViewById(R.id.love_text);
        EditText fre_edit = findViewById(R.id.fre_text);
        EditText chi_edit = findViewById(R.id.chi_text);
        EditText gro_edit = findViewById(R.id.gro_text);
        //全票的情況
        if(reg.isChecked()==true) {
            reg_edit.setFocusableInTouchMode(true);
            reg_edit.setFocusable(true);
            reg.requestFocus();

        }
        if(reg.isChecked()==false){
            reg_edit.setText("");
            reg_edit.setFocusable(false);
            reg_edit.setFocusableInTouchMode(false);
            reg_money=0;
        }
        //敬老票的情況（5折）
        if(old.isChecked()==true){
            old_edit.setFocusableInTouchMode(true);
            old_edit.setFocusable(true);
            old_edit.requestFocus();
        }
        if(old.isChecked()==false){
            old_edit.setText("");
            old_edit.setFocusable(false);
            old_edit.setFocusableInTouchMode(false);
            old_money =0;
        }
        //愛心票的情況（5折）
        if(love.isChecked()==true){
            love_edit.setFocusableInTouchMode(true);
            love_edit.setFocusable(true);
            love_edit.requestFocus();
        }
        if(love.isChecked()==false){
            love_edit.setText("");
            love_edit.setFocusable(false);
            love_edit.setFocusableInTouchMode(false);
            love_money =0;
        }
        //自由座的情況
        if(fre.isChecked()==true){
            fre_edit.setFocusableInTouchMode(true);
            fre_edit.setFocusable(true);
            fre_edit.requestFocus();
        }
        if(fre.isChecked()==false){
            fre_edit.setText("");
            fre_edit.setFocusable(false);
            fre_edit.setFocusableInTouchMode(false);
            free_money =0;
        }
        //兒童票的情況（5折）
        if(chi.isChecked()==true){
            chi_edit.setFocusableInTouchMode(true);
            chi_edit.setFocusable(true);
            chi_edit.requestFocus();
        }
        if(chi.isChecked()==false){
            chi_edit.setText("");
            chi_edit.setFocusable(false);
            chi_edit.setFocusableInTouchMode(false);
            chi_money=0;
        }
        //團體票的情況（95折）
        if(gro.isChecked()==true){
            gro_edit.setFocusableInTouchMode(true);
            gro_edit.setFocusable(true);
            gro_edit.requestFocus();
        }
        if(gro.isChecked()==false){
            gro_edit.setText("");
            gro_edit.setFocusable(false);
            gro_edit.setFocusableInTouchMode(false);
            gro_money=0;
            s="";
        }
        int actionId = R.id.fre_text;
        onEditorAction(fre_edit, actionId, null);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        regular(begin,end);
        EditText reg_edit = findViewById(R.id.reg_text);
        EditText old_edit = findViewById(R.id.old_text);
        EditText love_edit = findViewById(R.id.love_text);
        EditText fre_edit = findViewById(R.id.fre_text);
        EditText chi_edit = findViewById(R.id.chi_text);
        EditText gro_edit = findViewById(R.id.gro_text);//regular(begin,end);
        TextView tv = findViewById(R.id.tvshow);
        if(!reg_edit.getText().toString().isEmpty()){
            reg_money = (int)(regular_money*Integer.parseInt(reg_edit.getText().toString()));
        }
        if(reg_edit.getText().toString().isEmpty()){
            reg_money =0;
        }
        if(!old_edit.getText().toString().isEmpty()){
           old_money=(int) (regular_money*Integer.parseInt(old_edit.getText().toString())*0.5);
        }
        if(old_edit.getText().toString().isEmpty()){
            old_money =0;
        }
        if(!love_edit.getText().toString().isEmpty()){
            love_money =(int)(regular_money*Integer.parseInt(love_edit.getText().toString())*0.5);
        }
        if(love_edit.getText().toString().isEmpty()){
            love_money =0;
        }
        if(!chi_edit.getText().toString().isEmpty()){
            chi_money = (int)(regular_money*Integer.parseInt(chi_edit.getText().toString())*0.5);
        }
        if(chi_edit.getText().toString().isEmpty()){
            chi_money =0;
        }
        if(!gro_edit.getText().toString().isEmpty()){
            if(Integer.parseInt(gro_edit.getText().toString()) >= 11){
                gro_money = (int) (regular_money * Integer.parseInt(gro_edit.getText().toString())*0.95);
                s="";
            }
            else{
                s="團體票最少需購買11張";
                gro_edit.setText("");
            }
        }
        if(gro_edit.getText().toString().isEmpty()){
            gro_money =0;
        }
        if(fre_edit.getText().toString().isEmpty()){
            free_money =0;
        }
        if(!fre_edit.getText().toString().isEmpty()){
            int a = begin;
            int b = end;
            if(a>b){
                int temp = a;
                a = b;
                b = temp;
            }
            switch(a){
                case 0:
                    switch(b){
                        case 1:
                            free_money = 155;
                            break;
                        case 2:
                            free_money = 280;
                            break;
                        case 3:
                            free_money = 675;
                            break;
                        case 4:
                            free_money = 1045;
                            break;
                        case 5:
                            free_money = 1305;
                            break;
                        case 6:
                            free_money = 1445;
                            break;
                    }
                    break;
                case 1:
                    switch(b){
                        case 2:
                            free_money = 125;
                            break;
                        case 3:
                            free_money = 520;
                            break;
                        case 4:
                            free_money = 890;
                            break;
                        case 5:
                            free_money = 1150;
                            break;
                        case 6:
                            free_money = 1290;
                            break;
                    }
                    break;
                case 2:
                    switch(b){
                        case 3:
                            free_money = 395;
                            break;
                        case 4:
                            free_money = 765;
                            break;
                        case 5:
                            free_money = 1025;
                            break;
                        case 6:
                            free_money = 1160;
                            break;
                    }
                    break;
                case 3:
                    switch(b){
                        case 4:
                            free_money = 395;
                            break;
                        case 5:
                            free_money = 630;
                            break;
                        case 6:
                            free_money = 765;
                            break;
                    }
                    break;
                case 4:
                    switch(b){
                        case 5:
                            free_money = 270;
                            break;
                        case 6:
                            free_money = 395;
                            break;
                    }
                    break;
                case 5:
                    switch(b){
                        case 6:
                            free_money = 135;
                            break;
                    }
                    break;
            }
            total =free_money *Integer.parseInt(fre_edit.getText().toString());
        }
        ArrayList<String> station_name = new ArrayList<>();
        station_name.add("台北站");
        station_name.add("桃園站");
        station_name.add("新竹站");
        station_name.add("台中站");
        station_name.add("嘉義站");
        station_name.add("台南站");
        station_name.add("高雄站");
        total = reg_money+old_money+love_money+chi_money+gro_money+free_money;
        if(begin>end){
            tv.setText("北上"+station_name.get(begin)+
                    "至"+station_name.get(end)+"的票價總和為"+total+"元"+"\n"+s);
        }
        else{
            tv.setText("南下"+station_name.get(begin)+
                    "至"+station_name.get(end)+"的票價總和為"+total+"元"+"\n"+s);
        }
        return false;
    }
}