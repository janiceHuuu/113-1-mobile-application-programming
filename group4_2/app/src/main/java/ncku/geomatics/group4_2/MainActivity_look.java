package ncku.geomatics.group4_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity_look extends AppCompatActivity
implements View.OnClickListener, LocationListener, DialogInterface.OnClickListener
{
    TextView tv_l_c = null,tv_l_w = null,tv_l_i = null,tv_l_k = null,tv_l_jp = null,tv_l_t = null,tv_l_rec = null;
    SQLiteDatabase db_look;
    Cursor cs_look = null;
    int look_total = 0,look_c = 0,look_w = 0,look_i = 0,look_k = 0,look_jp = 0,look_t = 0;
    Geocoder geo_look = null;
    ArrayList<String> heart_name = null;
    List<Address> add_list_look = null;
    ArrayList<Double> heart_lat = null,heart_lon = null;
    double t1=0,t2=0;
    boolean ready_go=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_look);
        //取得授權
        LocationManager lm_look = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 300);
        }
        lm_look.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 8, this);
        lm_look.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 8, this);
        geo_look = new Geocoder(this, Locale.getDefault());
        requery_look();

        Button b_look_rec = findViewById(R.id.button_look_choose);
        Button b_look_back = findViewById(R.id.button_look_back);
        b_look_rec.setOnClickListener(this);
        b_look_back.setOnClickListener(this);
    }

    private void requery_look(){
        heart_name = new ArrayList<>();
        heart_lat = new ArrayList<>();
        heart_lon = new ArrayList<>();
        tv_l_rec = findViewById(R.id.tv_look_rec);
        tv_l_rec.setText("請稍候，正在尋找推薦店家");
        ready_go=false;
        look_c=0;look_t=0;look_w=0;look_k=0;look_jp=0;look_i=0;
        db_look = openOrCreateDatabase("food", Context.MODE_PRIVATE, null);
        String cmd_look = "SELECT * FROM testTable1 where heart=='1'";
        cs_look = db_look.rawQuery(cmd_look,null);
        if(cs_look.moveToFirst()) {
            look_total = cs_look.getCount();
            do {
                if(cs_look.getString(7).equals("1")){
                    look_c++;
                }
                else if(cs_look.getString(8).equals("1")){
                    look_w++;
                }
                else if(cs_look.getString(9).equals("1")){
                    look_i++;
                }
                else if(cs_look.getString(10).equals("1")){
                    look_k++;
                }
                else if(cs_look.getString(11).equals("1")){
                    look_jp++;
                }
                else if(cs_look.getString(12).equals("1")){
                    look_t++;
                }
                else if(cs_look.getString(13).equals("1")){
                    look_total--;
                }
                heart_name.add(cs_look.getString(1));
                try {
                    add_list_look = geo_look.getFromLocationName(cs_look.getString(1), 10);
                    if (add_list_look.isEmpty()){
                        AlertDialog.Builder ad = new AlertDialog.Builder(this);
                        ad.setTitle("輸入錯誤");
                        ad.setMessage("找不到您所查詢的地址，請重新輸入");
                        ad.setCancelable(false);
                        ad.setIcon(android.R.drawable.btn_star);
                        ad.setPositiveButton("確定",this);
                        ad.show();
                    }
                    else {
                        Address c_look = add_list_look.get(0);
                        t1 = c_look.getLatitude();
                        t2 = c_look.getLongitude();
                        heart_lat.add(c_look.getLatitude());
                        heart_lon.add(c_look.getLongitude());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    tv_l_rec.setText(e+"");
                }
            } while (cs_look.moveToNext());
        }
        tv_l_c = findViewById(R.id.tv_look_c);
        tv_l_w = findViewById(R.id.tv_look_w);
        tv_l_i = findViewById(R.id.tv_look_i);
        tv_l_k = findViewById(R.id.tv_look_k);
        tv_l_jp = findViewById(R.id.tv_look_jp);
        tv_l_t = findViewById(R.id.tv_look_t);
        if(look_total>0){
            tv_l_c.setText(String.format("        中式佔百分之%.2f%%",((float)look_c/look_total*100)));
            tv_l_w.setText(String.format("        西式佔百分之%.2f%%",((float)look_w/look_total*100)));
            tv_l_i.setText(String.format("        義式佔百分之%.2f%%",((float)look_i/look_total*100)));
            tv_l_k.setText(String.format("        韓式佔百分之%.2f%%",((float)look_k/look_total*100)));
            tv_l_jp.setText(String.format("        日式佔百分之%.2f%%",((float)look_jp/look_total*100)));
            tv_l_t.setText(String.format("        泰式佔百分之%.2f%%",((float)look_t/look_total*100)));
        }
        else{
            tv_l_c.setText("        中式佔百分之0.00%");
            tv_l_w.setText("        西式佔百分之0.00%");
            tv_l_i.setText("        義式佔百分之0.00%");
            tv_l_k.setText("        韓式佔百分之0.00%");
            tv_l_jp.setText("        日式佔百分之0.00%");
            tv_l_t.setText("        泰式佔百分之0.00%");
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_look_back){
            finish();
        }
        if(v.getId() == R.id.button_look_choose){
            if(ready_go == true){
                Intent it_rec = new Intent();
                it_rec.setClass(this,MainActivity_choose.class);
                it_rec.putExtra("food_name",rec_name);//rec_name
                startActivity(it_rec);
            }
            else{
                Toast t = Toast.makeText(this,"推薦店家尚未計算完成",Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    String rec_name = "";
    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location == null) {
            runOnUiThread(() -> tv_l_rec.append("    無法獲取距離資訊，請確認資料是否完整"));
            return;
        }
        // 啟動新執行緒來處理距離計算，避免主執行緒被阻塞
        new Thread(() -> {
            double look_lat = location.getLatitude();
            double look_lon = location.getLongitude();
            double max_distance = Double.MAX_VALUE, new_distance;

            // 檢查 heart_lat 和 heart_lon 是否有資料
            if (heart_lat == null || heart_lon == null || heart_lat.isEmpty() || heart_lon.isEmpty()) {
                runOnUiThread(() -> tv_l_rec.setText("找不到距離最近的喜愛店家"));
                return;
            }
            else{
                // 計算最近的店家
                for (int look_i = 0; look_i < look_total; look_i++) {
                    new_distance = calculateDistance(look_lat, look_lon, heart_lat.get(look_i), heart_lon.get(look_i));
                    if (new_distance < max_distance) {
                        max_distance = new_distance;
                        rec_name = heart_name.get(look_i);
                    }
                }
            }

            // 回到主執行緒更新 UI
            runOnUiThread(() -> {
                if (rec_name != null && !rec_name.isEmpty()) {
                    tv_l_rec.setText("    推薦距離您最近的喜愛店家：\n    " + rec_name);
                    ready_go = true;
                } else {
                    tv_l_rec.setText("    找不到距離最近的喜愛店家");
                    ready_go = false;
                }
            });
        }).start();
    }


    static double EARTH_RADIUS = 6371.0;
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 將經緯度從度轉為弧度
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);
        // 計算經緯度的差值
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;
        // 使用 Haversine 公式
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // 計算距離
        return EARTH_RADIUS * c;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        requery_look();
    }
}