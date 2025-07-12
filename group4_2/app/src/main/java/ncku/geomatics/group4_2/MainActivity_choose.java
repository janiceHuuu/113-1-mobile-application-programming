package ncku.geomatics.group4_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity_choose extends AppCompatActivity
implements MediaPlayer.OnPreparedListener, View.OnClickListener,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DialogInterface.OnClickListener
{

    MediaPlayer mp = null;
    SQLiteDatabase db_choose_cl=null,db_choose_fl=null;
    ImageButton btn_line,btn_jk,btn_fp,btn_ub;
    String restaurant = "";
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choose);

        Intent it_ch = getIntent();
        restaurant = it_ch.getStringExtra("food_name");
        mp = new MediaPlayer();
        mp.setOnPreparedListener(this);
        if(restaurant!=null) {
            Uri u_s = null;
            if (restaurant.contains("全家")) {
                u_s = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song_f);
                prepare_media(u_s);
            }
            else if(restaurant.contains("7-11")) {
                u_s = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song_s);
                prepare_media(u_s);
            }
        }
        TextView tv_name = findViewById(R.id.tv_name);
        tv_name.setText(restaurant);
        btn_line=findViewById(R.id.btnLinePay);
        btn_fp = findViewById(R.id.btnFoodPanda);
        btn_ub = findViewById(R.id.btnUberEats);
        btn_jk = findViewById(R.id.btnJKoPay);
        btn_line.setVisibility(View.GONE);
        btn_fp.setVisibility(View.GONE);
        btn_ub.setVisibility(View.GONE);
        btn_jk.setVisibility(View.GONE);
        btn_fp.setOnClickListener(this);
        btn_ub.setOnClickListener(this);
        btn_jk.setOnClickListener(this);
        btn_line.setOnClickListener(this);
        ImageView red = findViewById(R.id.iv_red);
        ImageView empty = findViewById(R.id.iv_empty);
        red.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        red.setOnClickListener(this);
        empty.setOnClickListener(this);

        db_choose_cl = openOrCreateDatabase("checklist", Context.MODE_PRIVATE,null);
        // 初始化資料庫
        db_choose_fl = openOrCreateDatabase("food", MODE_PRIVATE, null);

        Cursor cursor = db_choose_fl.rawQuery("SELECT * FROM testTable1 WHERE name=?", new String[]{restaurant});
        if (cursor.moveToFirst()) {
            if(cursor.getString(cursor.getColumnIndexOrThrow("panda")).equals("1")){
                btn_fp.setVisibility(View.VISIBLE);
            }
            if(cursor.getString(cursor.getColumnIndexOrThrow("uber")).equals("1")){
                btn_ub.setVisibility(View.VISIBLE);
            }
            if(cursor.getString(cursor.getColumnIndexOrThrow("line")).equals("1")){
                btn_line.setVisibility(View.VISIBLE);
            }
            if(cursor.getString(cursor.getColumnIndexOrThrow("jiekou")).equals("1")){
                btn_jk.setVisibility(View.VISIBLE);
            }
            if(cursor.getString(cursor.getColumnIndexOrThrow("heart")).equals("1")){
                red.setVisibility(View.VISIBLE);
            }
            if(!cursor.getString(cursor.getColumnIndexOrThrow("heart")).equals("1")){
                empty.setVisibility(View.VISIBLE);
            }
        }

        Button btnGoogleMap = findViewById(R.id.btnGoogleMap);
        btnGoogleMap.setOnClickListener(this);
        Button button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(this);
        Button bookButton = findViewById(R.id.book_button);
        bookButton.setOnClickListener(this);
        Button button_lok = findViewById(R.id.button_lo);
        button_lok .setOnClickListener(this);
        Button button_go = findViewById(R.id.button_go);
        button_go.setOnClickListener(this);
    }

    void prepare_media(Uri ui){
        try{
            mp.reset();
            mp.setDataSource(this,ui);
            mp.setLooping(false);
            mp.prepareAsync(); //音樂設定完成後，把音樂從load到記憶體

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG);
        }
    }
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) { //音樂load完成後執行
        mp.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }

    AlertDialog.Builder ad_final;
    AlertDialog.Builder ad_group;
    AlertDialog.Builder ad;
    AlertDialog.Builder ad_time;
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
            year_final = String.valueOf(year);
            month_final = String.format("%02d",(month+1));
            day_final = String.format("%02d",dayOfMonth);
            tpd.setCancelable(false);
            dpd_show=0;
            tpd_show=1;
            tpd.show();
        }
    }
    String date="",time="",real_date="",hour_final="",minute_final="",year_final="",month_final="",day_final="";
    int ad_show=0,ad_time_show=0,tpd_show=0,dpd_show=0;
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
            hour_final=String.format(String.valueOf(hourOfDay));
            minute_final=String.format(String.format("%02d", minute));
            ad = new AlertDialog.Builder(this);
            ad.setTitle("時間資訊");
            ad.setMessage("您選擇的時間為\n"+date+time+"\n您的人數選擇為"+people);
            ad.setCancelable(false);
            ad.setIcon(android.R.drawable.btn_star);
            ad.setPositiveButton("確定",this).setNegativeButton("取消",this);
            ad_show=1;
            ad.show();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Toast t = Toast.makeText(this,"取消預約",Toast.LENGTH_SHORT);

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
            else{
                add_data_cl(restaurant,year_final,month_final,day_final,hour_final,minute_final,people);
                Toast.makeText(this,"預約成功",Toast.LENGTH_SHORT).show();
            }
            ad_show=0;
        }
    }

    public void add_data_cl(String n,String y,String mon,String d,String h,String min,String p){
        ContentValues cv = new ContentValues(7);
        cv.put("name",n);
        cv.put("year",y);
        cv.put("month",mon);
        cv.put("day",d);
        cv.put("hour",h);
        cv.put("minute",min);
        cv.put("people",p);
        db_choose_cl.insert("testTable1",null,cv);
    }

    String people = "1"; // 預設人數為1
    @Override
    public void onClick(View v) {
        ImageView red = findViewById(R.id.iv_red);
        ImageView empty = findViewById(R.id.iv_empty);
        if (v.getId() == R.id.book_button) {
            // 開啟人數選擇對話框
            AlertDialog.Builder peopleDialog = new AlertDialog.Builder(this);
            peopleDialog.setTitle("選擇人數");

            final NumberPicker numberPicker = new NumberPicker(this);
            numberPicker.setMinValue(1); // 最少1人
            numberPicker.setMaxValue(20); // 最多20人
            numberPicker.setValue(2); // 預設為2人

            peopleDialog.setView(numberPicker);

            peopleDialog.setPositiveButton("確定", (dialog, which) -> {
                String selectedPeople = String.valueOf(numberPicker.getValue());
                people = selectedPeople; // 儲存人數到變數中

                // 開啟日期選擇器
                Calendar c = Calendar.getInstance();
                dpd = new DatePickerDialog(this, this,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dpd.setCancelable(false);
                tpd_show = 0;
                dpd_show = 1;
                dpd.show();

            });
            peopleDialog.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
            peopleDialog.show();
        }

        if (v.getId() == R.id.button_go) {
            Intent it_go = new Intent();
            it_go.setClass(this, MainActivity_go.class);
            it_go.putExtra("go_to_name",restaurant );//restaurant
            startActivity(it_go);
        }
        if (v.getId() == R.id.button_lo) {
            Intent it_look = new Intent();
            it_look.setClass(this, MainActivity_look.class);
            startActivity(it_look);
        }
        if (v.getId() == R.id.button_back) {
            finish();
        }
        if (v.getId() == R.id.btnGoogleMap) {
            openGoogleMap(restaurant);
        }
        if (v.getId() == R.id.btnLinePay) {
            //openLinePay();
        }
        if (v.getId() == R.id.btnJKoPay) {
            //openJKoPay();
        }
        if (v.getId() == R.id.btnFoodPanda) {
            openFoodPanda(restaurant);
        }
        if (v.getId() == R.id.btnUberEats) {
            openUberEats(restaurant);
        }
        if (v.getId() == R.id.iv_red) {
            // 更新 heart 欄位為 0
            ContentValues values = new ContentValues();
            values.put("heart", 0);
            db_choose_fl.update("testTable1", values, "name=?", new String[]{restaurant});
            red.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
        }

        if (v.getId() == R.id.iv_empty) {
            // 更新 heart 欄位為 1
            ContentValues values = new ContentValues();
            values.put("heart", 1);
            db_choose_fl.update("testTable1", values, "name=?", new String[]{restaurant});

            empty.setVisibility(View.GONE);
            red.setVisibility(View.VISIBLE);
            Toast.makeText(this, "已加入收藏", Toast.LENGTH_SHORT).show();
        }
    }
    private void openGoogleMap(String place) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(place));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "未安裝 Google 地圖", Toast.LENGTH_SHORT).show();
        }
    }

    private void openLinePay() {
        try {
            Uri uri = Uri.parse("line://pay");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "未安裝 LINE 或無法啟動", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace(); // 幫助診斷問題
            Toast.makeText(this, "無法打開 LINE Pay", Toast.LENGTH_SHORT).show();
        }
    }

    private void openJKoPay() {
        try {
            Uri uri = Uri.parse("jkopay://");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            // intent.setPackage("tw.com.jkos.pay");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "未安裝 街口支付 或無法啟動", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "無法打開 街口支付", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFoodPanda(String restaurantUrl) {
        try {
            if (restaurantUrl != null && !restaurantUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setPackage("com.global.foodpanda.android"); // FoodPanda 的包名
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "無法打開 FoodPanda，將跳轉至瀏覽器", Toast.LENGTH_SHORT).show();
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.foodpanda.com"));
                    startActivity(webIntent);
                }
            } else {
                Toast.makeText(this, "店家資訊無效", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "無法打開 FoodPanda 應用程式", Toast.LENGTH_SHORT).show();
        }
    }


    private void openUberEats(String place) {
        try {
            Uri uri = Uri.parse("ubereats://search?query=" + Uri.encode(place));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.ubercab.eats");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ubereats.com/"));
                startActivity(webIntent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "無法打開 UberEats", Toast.LENGTH_SHORT).show();
        }
    }


}