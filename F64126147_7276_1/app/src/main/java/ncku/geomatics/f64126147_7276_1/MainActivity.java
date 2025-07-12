package ncku.geomatics.f64126147_7276_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener,
        View.OnClickListener,DatePickerDialog.OnDateSetListener, CompoundButton.OnCheckedChangeListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner sp =findViewById(R.id.spinner);
        ArrayList<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("棒球");
        spinnerItems.add("藍球");
        spinnerItems.add("羽球");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinnerAdapter);
        sp.setOnItemSelectedListener(this);

        ImageView iv_bs = findViewById(R.id.iv_bs);
        ImageView iv_bk = findViewById(R.id.iv_bk);
        ImageView iv_fe = findViewById(R.id.iv_fe);
        iv_bs.setVisibility(View.INVISIBLE);
        iv_bk.setVisibility(View.INVISIBLE);
        iv_fe.setVisibility(View.INVISIBLE);

        ImageView iv_ja = findViewById(R.id.iv_ja);
        ImageView iv_ta = findViewById(R.id.iv_ta);
        ImageView iv_us = findViewById(R.id.iv_us);
        iv_ja.setVisibility(View.INVISIBLE);
        iv_ta.setVisibility(View.GONE);
        iv_us.setVisibility(View.GONE);

        ImageView iv_su = findViewById(R.id.iv_su);
        ImageView iv_ha = findViewById(R.id.iv_ham);
        ImageView iv_bo = findViewById(R.id.iv_bo);
        iv_su.setVisibility(View.INVISIBLE);
        iv_ha.setVisibility(View.INVISIBLE);
        iv_bo.setVisibility(View.INVISIBLE);

        RadioGroup rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);

        Button day = findViewById(R.id.day);
        day.setOnClickListener(this);
        Button con = findViewById(R.id.confirm);
        con.setOnClickListener(this);

        CompoundButton cb_su = findViewById(R.id.cb_su);
        CompoundButton cb_ha = findViewById(R.id.cb_ham);
        CompoundButton cb_bo = findViewById(R.id.cb_bo);
        cb_bo.setOnCheckedChangeListener(this);
        cb_ha.setOnCheckedChangeListener(this);
        cb_su.setOnCheckedChangeListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ImageView iv_bs = findViewById(R.id.iv_bs);
        ImageView iv_bk = findViewById(R.id.iv_bk);
        ImageView iv_fe = findViewById(R.id.iv_fe);
        if(position == 0){
            iv_bs.setVisibility(View.VISIBLE);
            iv_bk.setVisibility(View.GONE);
            iv_fe.setVisibility(View.GONE);
        }
        if(position == 1){
            iv_bs.setVisibility(View.GONE);
            iv_bk.setVisibility(View.VISIBLE);
            iv_fe.setVisibility(View.GONE);
        }
        if(position == 2){
            iv_bs.setVisibility(View.GONE);
            iv_bk.setVisibility(View.GONE);
            iv_fe.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ImageView iv_ja = findViewById(R.id.iv_ja);
        ImageView iv_ta = findViewById(R.id.iv_ta);
        ImageView iv_us = findViewById(R.id.iv_us);
        if(checkedId == R.id.rb_ja){
            iv_ja.setVisibility(View.VISIBLE);
            iv_ta.setVisibility(View.GONE);
            iv_us.setVisibility(View.GONE);
        }
        if(checkedId == R.id.rb_ta){
            iv_ta.setVisibility(View.VISIBLE);
            iv_ja.setVisibility(View.GONE);
            iv_us.setVisibility(View.GONE);
        }
        if(checkedId == R.id.rb_us){
            iv_us.setVisibility(View.VISIBLE);
            iv_ta.setVisibility(View.GONE);
            iv_ja.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.day){
            DatePickerDialog dpd = new DatePickerDialog(this,this,2024,11,11);
            dpd.setCancelable(false);
            dpd.show();
        }
        if(v.getId() == R.id.confirm){

            TextView tv_bmi = findViewById(R.id.tv_bmi);
            TextView tv_name = findViewById(R.id.tv_name);
            EditText ed_name = findViewById(R.id.ed_name);
            EditText ed_he = findViewById(R.id.ed_he);
            EditText ed_we = findViewById(R.id.ed_we);
            float we = Float.parseFloat(ed_we.getText().toString());
            float he = Float.parseFloat(ed_he.getText().toString()) / 100;  // 转换为米
            float bmi = we / (he * he);  // 正确的 BMI 计算公式
            tv_bmi.setText("BMI："+String.format("%.2f",bmi));
            tv_name.setText(ed_name.getText().toString());
        }
    }

    int peopleyear;
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        TextView tv_age = findViewById(R.id.tv_age);
        TextView tv_day = findViewById(R.id.tv_day);
        String s = year+"/"+String.format("%02d",(month+1))+"/"+String.format("%02d",dayOfMonth);
        tv_day.setText(s);
        //未過今年生日
        if(month>c.get(Calendar.MONTH) || (month==c.get(Calendar.MONTH) && dayOfMonth>c.get(Calendar.DAY_OF_MONTH))){
            peopleyear = c.get(Calendar.YEAR)-year-1;
        }
        //已過今年生日
        else{
            peopleyear = c.get(Calendar.YEAR)-year;
        }
        tv_age.setText(String.valueOf(peopleyear)+"歲");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CompoundButton cb_su = findViewById(R.id.cb_su);
        CompoundButton cb_ha = findViewById(R.id.cb_ham);
        CompoundButton cb_bo = findViewById(R.id.cb_bo);
        ImageView iv_su = findViewById(R.id.iv_su);
        ImageView iv_ha = findViewById(R.id.iv_ham);
        ImageView iv_bo = findViewById(R.id.iv_bo);
        if(!cb_su.isChecked()){
            iv_su.setVisibility(View.GONE);
        }
        if(!cb_ha.isChecked()){
            iv_ha.setVisibility(View.GONE);
        }
        if(!cb_bo.isChecked()){
            iv_bo.setVisibility(View.GONE);
        }
        if(cb_su.isChecked()){
            iv_su.setVisibility(View.VISIBLE);
        }
        if(cb_ha.isChecked()){
            iv_ha.setVisibility(View.VISIBLE);
        }
        if(cb_bo.isChecked()){
            iv_bo.setVisibility(View.VISIBLE);
        }
    }
}