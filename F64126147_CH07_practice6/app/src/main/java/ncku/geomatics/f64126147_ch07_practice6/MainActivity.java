package ncku.geomatics.f64126147_ch07_practice6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
implements View.OnClickListener, DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, DialogInterface.OnClickListener,
        TextView.OnEditorActionListener, CompoundButton.OnCheckedChangeListener
{
    AlertDialog.Builder ad_final;
    AlertDialog.Builder ad_group;
    AlertDialog.Builder ad;
    AlertDialog.Builder ad_time;
    DatePickerDialog dpd;
    TimePickerDialog tpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buy = findViewById(R.id.buy);
        Button confirm = findViewById(R.id.confirm);
        buy.setOnClickListener(this);
        confirm.setOnClickListener(this);
        CompoundButton regular = findViewById(R.id.regular);
        CompoundButton student = findViewById(R.id.student);
        CompoundButton group = findViewById(R.id.group);
        CompoundButton challenge = findViewById(R.id.challenge);
        CompoundButton invite = findViewById(R.id.invite);
        regular.setOnCheckedChangeListener(this);
        student.setOnCheckedChangeListener(this);
        group.setOnCheckedChangeListener(this);
        challenge.setOnCheckedChangeListener(this);
        invite.setOnCheckedChangeListener(this);
        EditText ed_group = findViewById(R.id.ed_group);
        ed_group.setOnEditorActionListener(this);
    }

    String date="",time="";
    int i=0,ad_show=0,ad_final_show=0,ad_group_show=0,ad_time_show=0,tpd_show=0,dpd_show=0;
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buy){
            Calendar c = Calendar.getInstance();
            dpd = new DatePickerDialog(this,this,
                    c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            dpd.setCancelable(false);
            tpd_show=0;
            dpd_show=1;
            dpd.show();
        }
        if(view.getId() == R.id.confirm){
            i=0;
            EditText ed_regular = findViewById(R.id.ed_regular);
            EditText ed_student = findViewById(R.id.ed_student);
            EditText ed_group = findViewById(R.id.ed_group);
            EditText ed_challenge = findViewById(R.id.ed_challenge);
            EditText ed_invite = findViewById(R.id.ed_invite);

            ArrayList<String> ticket = new ArrayList<>();
            ArrayList<String> number = new ArrayList<>();

            if(!ed_regular.getText().toString().isEmpty()){
                ticket.add("原價票");
                number.add(ed_regular.getText().toString());
                i++;
            }
            if(!ed_challenge.getText().toString().isEmpty()){
                ticket.add("身障票");
                number.add(ed_challenge.getText().toString());
                i++;
            }
            if(!ed_student.getText().toString().isEmpty()){
                ticket.add("學生票");
                number.add(ed_student.getText().toString());
                i++;
            }
            if(!ed_group.getText().toString().isEmpty()){
                ticket.add("團體票");
                number.add(ed_group.getText().toString());
                i++;
            }

            if(!ed_invite.getText().toString().isEmpty()){
                ticket.add("優待票");
                number.add(ed_invite.getText().toString());
                i++;
            }
            ad_final = new AlertDialog.Builder(this);
            ad_final.setTitle("購票資訊").setCancelable(false);
            if (i == 0) {
                ad_final.setMessage("您未購買任何票，\n是否要再次選擇票別？");
                ad_final.setPositiveButton("是",this).setNegativeButton("否",this);
            }
            else if(i != 0){
                String answer1 = "您的購票資訊為：\n"+date+time+"\n";
                String answer2 = "";
                for(int j=0;j<i;j++){
                    answer2 +=ticket.get(j)+"共"+number.get(j)+"張\n";
                }
                ad_final.setMessage(answer1+answer2);
                ad_final.setPositiveButton("確定",this).setNegativeButton("取消",this)
                        .setNeutralButton("回前頁",this);
            }
            ad_final_show=1;
            ad_final.show();
        }

    }
    String real_date ="";
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar d = Calendar.getInstance();
        int real_year = d.get(Calendar.YEAR);
        int real_month = d.get(Calendar.MONTH);
        int real_dayOfMonth = d.get(Calendar.DAY_OF_MONTH);
        if(real_year>year || (real_year==year && real_month>month) ||
                (real_year==year && real_month==month && real_dayOfMonth > dayOfMonth)){
            ad_time = new AlertDialog.Builder(this);
            ad_time.setTitle("輸入錯誤").setMessage("您的時間輸入小於當前時間，\n請重新輸入");
            ad_time.setCancelable(false);
            ad_time.setIcon(android.R.drawable.stat_sys_warning);
            ad_time.setPositiveButton("確定",this);
            ad_time_show=1;
            dpd_show=1;
            tpd_show=0;
            ad_time.show();
        }
        else{
            real_date = real_year+"年"+(real_month+1)+"月"+real_dayOfMonth+"日";
            date = year+"年"+String.format("%02d",(month+1))+"月"+String.format("%02d",dayOfMonth)+"日";
            tpd = new TimePickerDialog(this,this,
                    d.get(Calendar.HOUR_OF_DAY),d.get(Calendar.MINUTE), true);
            tpd.setCancelable(false);
            dpd_show=0;
            tpd_show=1;
            tpd.show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int real_hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int real_minute = Calendar.getInstance().get(Calendar.MINUTE);
        if(real_date.equals(date) && (hourOfDay<real_hour || (real_hour == hourOfDay && minute<real_minute))){
            ad_time = new AlertDialog.Builder(this);
            ad_time.setTitle("輸入錯誤").setMessage("您的時間輸入小於當前時間，\n請重新輸入");
            ad_time.setCancelable(false);
            ad_time.setIcon(android.R.drawable.stat_sys_warning);
            ad_time.setPositiveButton("確定",this);
            ad_time_show=1;
            tpd_show=1;
            ad_time.show();
        }
        else{
            time = hourOfDay+"時"+String.format("%02d", minute)+"分";
            ad = new AlertDialog.Builder(this);
            ad.setTitle("時間資訊");
            ad.setMessage("您選擇的時間為\n"+date+time);
            ad.setCancelable(false);
            ad.setIcon(android.R.drawable.btn_star);
            ad.setPositiveButton("確定",this).setNegativeButton("取消",this);
            ad_show=1;
            ad.show();
        }
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        Toast t = Toast.makeText(this,"取消購票",Toast.LENGTH_SHORT);
        Snackbar s = Snackbar.make(findViewById(R.id.root),"時間選擇成功",Snackbar.LENGTH_SHORT);
        Snackbar s2 = Snackbar.make(findViewById(R.id.root),"購票成功",Snackbar.LENGTH_SHORT);
        LinearLayout tk = findViewById(R.id.tk);
        Button confirm = findViewById(R.id.confirm);

        if(ad_time_show==1){
            if(which == DialogInterface.BUTTON_POSITIVE){
                if(dpd_show==1){
                    dpd.show();
                }
                if(tpd_show==1){
                    tpd.show();
                }
            }
            tpd_show=0;
            dpd_show=0;
            ad_time_show=0;
        }
        if(ad_show==1){
            if(which == DialogInterface.BUTTON_NEGATIVE){
                t.show();
            }
            if(which == DialogInterface.BUTTON_POSITIVE){
                s.show();
                tk.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);
            }
            ad_show=0;
        }
        if(ad_group_show==1){
            if(which == DialogInterface.BUTTON_POSITIVE){
            }
            ad_group_show=0;
        }
        if(ad_final_show==1){
            CompoundButton regular = findViewById(R.id.regular);
            CompoundButton student = findViewById(R.id.student);
            CompoundButton group = findViewById(R.id.group);
            CompoundButton challenge = findViewById(R.id.challenge);
            CompoundButton invite = findViewById(R.id.invite);
            if(i==0 && which == DialogInterface.BUTTON_NEGATIVE){
                tk.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                regular.setChecked(false);
                student.setChecked(false);
                group.setChecked(false);
                challenge.setChecked(false);
                invite.setChecked(false);
                onCheckedChanged(regular,true);
                t.show();
            }
            if(i!=0 && which == DialogInterface.BUTTON_POSITIVE){
                tk.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                s2.show();
                regular.setChecked(false);
                student.setChecked(false);
                group.setChecked(false);
                challenge.setChecked(false);
                invite.setChecked(false);
                onCheckedChanged(regular,true);
            }
            if(i!=0 && which == DialogInterface.BUTTON_NEUTRAL){
            }
            if(i!=0 && which == DialogInterface.BUTTON_NEGATIVE){
                tk.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                t.show();
                regular.setChecked(false);
                student.setChecked(false);
                group.setChecked(false);
                challenge.setChecked(false);
                invite.setChecked(false);
                onCheckedChanged(regular,true);
            }
            ad_final_show=0;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CompoundButton regular = findViewById(R.id.regular);
        CompoundButton student = findViewById(R.id.student);
        CompoundButton group = findViewById(R.id.group);
        CompoundButton challenge = findViewById(R.id.challenge);
        CompoundButton invite = findViewById(R.id.invite);
        EditText ed_regular = findViewById(R.id.ed_regular);
        EditText ed_student = findViewById(R.id.ed_student);
        EditText ed_group = findViewById(R.id.ed_group);
        EditText ed_challenge = findViewById(R.id.ed_challenge);
        EditText ed_invite = findViewById(R.id.ed_invite);

        if(regular.isChecked()){
            ed_regular.setFocusableInTouchMode(true);
            ed_regular.setFocusable(true);
            ed_regular.requestFocus();
        }
        if(regular.isChecked()==false){
            ed_regular.setText("");
            ed_regular.setFocusableInTouchMode(false);
            ed_regular.setFocusable(false);
        }
        if(student.isChecked()){
            ed_student.setFocusableInTouchMode(true);
            ed_student.setFocusable(true);
            ed_student.requestFocus();
        }
        if(student.isChecked()==false){
            ed_student.setText("");
            ed_student.setFocusableInTouchMode(false);
            ed_student.setFocusable(false);
        }
        if(group.isChecked()){
            ed_group.setFocusableInTouchMode(true);
            ed_group.setFocusable(true);
            ed_group.requestFocus();
        }
        if(group.isChecked()==false){
            ed_group.setText("");
            ed_group.setFocusableInTouchMode(false);
            ed_group.setFocusable(false);
        }
        if(challenge.isChecked()){
            ed_challenge.setFocusableInTouchMode(true);
            ed_challenge.setFocusable(true);
            ed_challenge.requestFocus();
        }
        if(challenge.isChecked()==false){
            ed_challenge.setText("");
            ed_challenge.setFocusableInTouchMode(false);
            ed_challenge.setFocusable(false);
        }
        if(invite.isChecked()){
            ed_invite.setFocusableInTouchMode(true);
            ed_invite.setFocusable(true);
            ed_invite.requestFocus();
        }
        if(invite.isChecked()==false){
            ed_invite.setText("");
            ed_invite.setFocusableInTouchMode(false);
            ed_invite.setFocusable(false);
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        EditText ed_group = findViewById(R.id.ed_group);
        if(!ed_group.getText().toString().isEmpty()){
            if(Integer.parseInt(ed_group.getText().toString())<11){
                ed_group.setText("");
                ad_group = new AlertDialog.Builder(this);
                ad_group.setTitle("輸入錯誤").setMessage("團體票最少需購買11張").setCancelable(false);
                ad_group.setIcon(android.R.drawable.sym_action_email)
                                .setPositiveButton("確定",this);
                ad.setCancelable(false);
                ad_group_show=1;
                ad_group.show();
            }
        }
        return false;
    }
}