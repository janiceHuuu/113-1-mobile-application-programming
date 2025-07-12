package ncku.geomatics.group4_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
implements OnMapReadyCallback, View.OnClickListener
{

    SQLiteDatabase db = null,db_cl=null;
    Cursor cs = null;
    private GoogleMap mMap;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 加載地圖
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // 新增店家按鈕
        Button btnGoToAdd = findViewById(R.id.button);
        btnGoToAdd.setOnClickListener(this);
        Button b_2 = findViewById(R.id.button2);
        b_2.setOnClickListener(this);
        Button b_3 = findViewById(R.id.button3);
        b_3.setOnClickListener(this);
        Button b_4 = findViewById(R.id.button4);
        b_4.setOnClickListener(this);
        searchView = findViewById(R.id.searchView);


        db_cl = openOrCreateDatabase("checklist", Context.MODE_PRIVATE,null);
        String cmd_cl = "CREATE TABLE IF NOT EXISTS testTable1"+ "" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR(32),"+" year VARCHAR(32),"+" month VARCHAR(8),"+"day VARCHAR(8),"
                +"hour VARCHAR(8),"+"minute VARCHAR(8),"+"people VARCHAR(32))";
        //用PRIMARY KEY 可以避免儲存重複名字的資料
        db_cl.execSQL(cmd_cl);

        db = openOrCreateDatabase("food", Context.MODE_PRIVATE,null);
        String cmd = "CREATE TABLE IF NOT EXISTS testTable1"+ "" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR(32) UNIQUE,"+" panda VARCHAR(4),"+" uber VARCHAR(4),"
                +"line VARCHAR(4),"+"jiekou VARCHAR(4),"+"heart VARCHAR(4),"
                +"chinese VARCHAR(4),"+"western VARCHAR(4),"+"italian VARCHAR(4),"+"korean VARCHAR(4),"
                +"japanese VARCHAR(4),"+"thai VARCHAR(4)," +"drinks VARCHAR(4))";
        //用PRIMARY KEY 可以避免儲存重複名字的資料
        db.execSQL(cmd);
        // 插入所有餐廳資料
        insertAllData();
        // 測試顯示
        //displayData();
        setupSearchView();
    }

    private void insertAllData() {
        add_data("黑盤（成大店）","0","1","1","0","1","0","0","1","0","0","0","0");
        add_data("韓朝（成大店）","0","0","0","0","0","0","0","0","1","0","0","0");
        add_data("五銅號（台南成大店）","1","1","1","1","0","0","0","0","0","0","0","1");
        add_data("東寧路東洲","0","1","1","1","0","0","0","0","0","0","0","0");
        add_data("哈胖high胖（育樂店）","1","1","1","0","1","1","0","0","0","0","0","0");
        add_data("育樂街億品鍋","0","0","1","0","0","1","0","0","0","0","0","0");
        add_data("麻古茶坊（台南成大店）","1","1","1","1","0","0","0","0","0","0","0","1");
        add_data("辣訣（台南東寧店）","0","0","0","0","0","1","0","0","0","0","0","0");
        add_data("Mr.布魯（台南成大店）","1","0","0","0","0","1","0","0","0","0","0","0");
        add_data("Mr.Wish(台南成大店）","1","1","1","1","0","0","0","0","0","0","0","1");
        add_data("小裕樂","0","0","1","0","1","1","0","0","0","0","0","0");
        add_data("築間（台南成大店）","0","1","1","1","1","1","0","0","0","0","0","0");
        add_data("育樂街有泰拜三","0","0","0","0","1","0","0","0","0","0","1","0");
        add_data("麥當勞（台南大學店）","1","1","1","1","1","0","1","0","0","0","0","0");
        add_data("bitch fry fry 台南店","1","1","0","0","1","0","0","0","0","0","1","0");
        add_data("元味屋","0","0","0","0","1","1","0","0","0","0","0","0");
        add_data("福勝亭（成大店）","1","1","1","1","0","0","0","0","0","1","0","0");
        add_data("三商巧福（長榮店）","1","0","1","1","0","1","0","0","0","0","0","0");
    }
    public void add_data(String n,String p,String u,String l,String j,String h,String c,
                         String w,String i,String k,String jp,String t,String d){
        ContentValues cv = new ContentValues(13);
        cv.put("name",n);
        cv.put("panda",p);
        cv.put("uber",u);
        cv.put("line",l);
        cv.put("jiekou",j);
        cv.put("heart",h);
        cv.put("chinese",c);
        cv.put("western",w);
        cv.put("italian",i);
        cv.put("korean",k);
        cv.put("japanese",jp);
        cv.put("thai",t);
        cv.put("drinks",d);
        db.insert("testTable1",null,cv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String column = null;
        switch (item.getItemId()) {
            case R.id.action_thai:
                column = "thai";
                break;
            case R.id.action_japanese:
                column = "japanese";
                break;
            case R.id.action_italian:
                column = "italian";
                break;
            case R.id.action_chinese:
                column = "chinese";
                break;
            case R.id.action_korean:
                column = "korean";
                break;
            case R.id.action_american:
                column = "western";
                break;
            case R.id.action_favorite:
                column = "heart";
                break;
            case R.id.action_reset: // 新增一個重置選項
                mMap.clear(); // 清除所有標記
                loadMarkersFromDB(); // 重新加載所有標記
                Toast.makeText(this, "已重置篩選，顯示所有地標", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

        if (column != null) {
            displayFilteredData(column);
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String food = data.getStringExtra("foodpanda");
            String lpay = data.getStringExtra("linepay");
            String ub = data.getStringExtra("ubereats");
            String jiek = data.getStringExtra("jie");
            String ch = data.getStringExtra("chin");
            String we = data.getStringExtra("west");
            String ko = data.getStringExtra("kore");
            String jap = data.getStringExtra("japan");
            String itali = data.getStringExtra("ita");
            String thai = data.getStringExtra("tai");
            String drin = data.getStringExtra("dri");

            add_data(name, food, ub, lpay, jiek, "0", ch, we, itali, ko, jap, thai, drin);

            // 確保地圖標記被重新加載
            if (mMap != null) {
                mMap.clear();
                loadMarkersFromDB();
            }
        }
    }

    private void displayFilteredData(String column) {
        if (db == null || !db.isOpen()) {
            db = openOrCreateDatabase("food", Context.MODE_PRIVATE, null);
        }

        String cmd = "SELECT * FROM testTable1 WHERE " + column + "='1'";
        Cursor cursor = db.rawQuery(cmd, null);

        mMap.clear(); // 清除現有標記

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                geo_main = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> add_list_main = geo_main.getFromLocationName(name, 1);
                    if (add_list_main != null && !add_list_main.isEmpty()) {
                        Address c_main = add_list_main.get(0);
                        double lat = c_main.getLatitude();
                        double lng = c_main.getLongitude();
                        LatLng location = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(location).title(name));
                    } else {
                        Toast.makeText(this, "找不到地址：" + name, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(this, "地理編碼錯誤：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "無符合條件的地標", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

    private void displayData() {
        String cmd_a = "SELECT * FROM testTable1";
        db = openOrCreateDatabase("food", Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery(cmd_a, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                //String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));

                Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    private String selectedMarkerName = null; // 儲存選中的 Marker 名稱
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // 設置地圖初始位置 (台南成大)
        LatLng initialLocation = new LatLng(22.9997, 120.2270);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 15));

        // 從資料庫載入標記
        loadMarkersFromDB();

        // 監聽 Marker 點擊事件
        mMap.setOnMarkerClickListener(marker -> {
            selectedMarkerName = marker.getTitle(); // 儲存 Marker 的標題
            Toast.makeText(this, "選中：" + selectedMarkerName, Toast.LENGTH_SHORT).show();
            return false; // 返回 false，表示仍會顯示 InfoWindow
        });
    }

    Geocoder geo_main = null;

    private void loadMarkersFromDB() {
        if (db == null || !db.isOpen()) {
            db = openOrCreateDatabase("food", Context.MODE_PRIVATE, null);
        }

        Cursor cursor = db.rawQuery("SELECT * FROM testTable1", null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                geo_main = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> add_list_main = geo_main.getFromLocationName(name, 1);
                    if (add_list_main != null && !add_list_main.isEmpty()) {
                        Address c_main = add_list_main.get(0);
                        double lat = c_main.getLatitude();
                        double lng = c_main.getLongitude();
                        LatLng location = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(location).title(name));
                    } else {
                        // 如果找不到地點，跳過
                        Toast.makeText(this, "無法解析地址：" + name, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(this, "地理編碼錯誤：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            Intent intent = new Intent(MainActivity.this, MainActivity_add.class);
            startActivityForResult(intent, 1);
        }
        if(v.getId() == R.id.button2){
            if(selectedMarkerName != null){
                Intent it_choose = new Intent();
                it_choose.setClass(this,MainActivity_choose.class);
                it_choose.putExtra("food_name",selectedMarkerName);
                startActivity(it_choose);
            }
            else{
                Toast.makeText(this, "請先選擇一個地圖標記！", Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.button3){
            Intent it_foodlist = new Intent();
            it_foodlist.setClass(this,MainActivity_checklist.class);
            startActivity(it_foodlist);
        }
        if(v.getId() == R.id.button4){
            Intent it_foodlist = new Intent();
            it_foodlist.setClass(this,MainActivity_foodlist.class);
            startActivity(it_foodlist);
        }
    }
    // 設置 SearchView 監聽器
    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAndMark(query);  // 執行搜尋並在地圖標記
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.trim().isEmpty()) {
                    // 當搜尋框為空時，重新顯示所有標記
                    mMap.clear();
                    loadMarkersFromDB();
                }
                return false;
            }
        });
    }

    // 搜尋並在地圖上標記店家
    private void searchAndMark(String query) {
        if (query == null || query.trim().isEmpty()) {
            Toast.makeText(this, "請輸入有效的搜尋關鍵字", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db == null || !db.isOpen()) {
            db = openOrCreateDatabase("food", Context.MODE_PRIVATE, null);
        }

        try (Cursor cursor = db.rawQuery("SELECT * FROM testTable1 WHERE name=?", new String[]{query})) {
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                geo_main = new Geocoder(this, Locale.getDefault());
                List<Address> add_list_main = geo_main.getFromLocationName(name, 1);
                if (add_list_main != null && !add_list_main.isEmpty()) {
                    Address c_main = add_list_main.get(0);
                    double lat = c_main.getLatitude();
                    double lng = c_main.getLongitude();
                    LatLng location = new LatLng(lat, lng);

                    mMap.clear(); // 清除現有標記
                    mMap.addMarker(new MarkerOptions().position(location).title(name));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
                } else {
                    Toast.makeText(this, "找不到位置：" + name, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "未找到匹配結果", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "地理編碼錯誤：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(this, "發生錯誤：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.clear(); // 清除所有標記
            loadMarkersFromDB(); // 重新加載標記
        }
    }

}