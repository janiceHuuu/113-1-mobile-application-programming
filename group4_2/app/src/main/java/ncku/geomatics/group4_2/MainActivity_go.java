package ncku.geomatics.group4_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.RenderScript;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity_go extends AppCompatActivity
        implements LocationListener, DialogInterface.OnClickListener, SensorEventListener,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener
{
    int zoom_level=0;
    String go_name="";
    Geocoder geo = null;
    LatLng me = null;
    String answer=null;
    LatLng search = null, center = null;
    double sea_lat = 0, sea_lon = 0;
    Marker me_marker=null,sea_marker=null;
    float first_b = 0;
    double me_lat = 0, me_lon = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_go);

        Button b_end = findViewById(R.id.button_end);
        b_end.setOnClickListener(this);

        //取得授權
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            initMap();
            initLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

        // 初始化地圖
        SupportMapFragment smf_go = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_go);
        if (smf_go != null) {
            smf_go.getMapAsync(this);
        } else {
            Toast.makeText(this, "無法加載地圖", Toast.LENGTH_LONG).show();
        }

        // 感測器初始化
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        if (rotationSensor != null) {
            sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(this, "無法使用方向感測器", Toast.LENGTH_SHORT).show();
        }

        // 從 Intent 取得目標名稱
        Intent it_go_a = getIntent();
        go_name = it_go_a.getStringExtra("go_to_name");

        // 背景執行 Geocoder 解析地址
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        TextView tv_length = findViewById(R.id.tv_length);
        new Thread(() -> {
            try {
                geo = new Geocoder(this, Locale.getDefault());
                List<Address> add_list = geo.getFromLocationName(go_name, 10);
                handler.post(() -> {
                    if (add_list == null || add_list.isEmpty()) {
                        AlertDialog.Builder ad = new AlertDialog.Builder(this);
                        ad.setTitle("輸入錯誤");
                        ad.setMessage("找不到您所查詢的地址，請重新輸入店家");
                        ad.setCancelable(false);
                        ad.setIcon(android.R.drawable.btn_star);
                        ad.setPositiveButton("確定",this);
                        ad.show();
                    } else {
                        AlertDialog.Builder ad_go = new AlertDialog.Builder(this);
                        ad_go.setTitle("使用說明");
                        ad_go.setMessage("1.點擊店家的指針，即可進入該店家的google搜尋頁面\n2.箭頭的指向，是店家的方向" +
                                "\n註：非常抱歉，準備地圖（尤其是初次準備）需要花費些許時間");
                        ad_go.setCancelable(false);
                        ad_go.setIcon(android.R.drawable.btn_star);
                        ad_go.setPositiveButton("確定",this);
                        ad_go.show();
                        Address c = add_list.get(0);
                        sea_lat = c.getLatitude();
                        sea_lon = c.getLongitude();
                        tv_length.setText("請稍候，正在準備地圖，以前往 " + go_name);
                    }
                });
            } catch (IOException e) {
                handler.post(() -> tv_length.append("地理編碼錯誤: " + e.getMessage()));
            }
        }).start();
    }

    private void initMap() {
        if (map_go == null) {
            SupportMapFragment smf_go = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_go);
            if (smf_go != null) {
                smf_go.getMapAsync(this);
            } else {
                Toast.makeText(this, "無法加載地圖", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initLocationUpdates() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            return;
        }
        // 使用 GPS 提供者進行定位
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000, // 5 秒更新一次
                5,    // 每 5 公尺更新一次
                this
        );
        // 嘗試獲取最後已知的位置
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null) {
            onLocationChanged(lastKnownLocation);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (map_go == null) {
            return;
        }
        TextView tv_length = findViewById(R.id.tv_length);
        ImageView iv_arrow = findViewById(R.id.iv_arrow);
        me_lat = location.getLatitude();
        me_lon = location.getLongitude();
        double distance = calculateDistance(me_lat, me_lon, sea_lat, sea_lon);
        if(me_lat !=0 && me_lon!=0 && sea_lat!=0 && sea_lon!=0){
            //設定兩地的中心
            if(distance<10){
                zoom_level = 12;
            }
            else if(distance<100){
                zoom_level = 10;
            }
            else if(distance<1000){
                zoom_level = 6;
            }
            else{
                zoom_level = 3;
            }
            double center_lat = (me_lat + sea_lat) / 2;
            double center_lon = (me_lon + sea_lon) / 2;

            if(map_go!=null){
                me = new LatLng(me_lat, me_lon);
                search = new LatLng(sea_lat, sea_lon);
                if (me_marker == null) {
                    me_marker = map_go.addMarker(new MarkerOptions().position(me).title("我的位置"));
                } else {
                    me_marker.setPosition(me);
                }

            }
            if(map_go!=null) {
                if (sea_marker == null) {
                    sea_marker = map_go.addMarker(new MarkerOptions().position(search).title(go_name).zIndex(2));
                    center = new LatLng(center_lat, center_lon);
                    map_go.moveCamera(CameraUpdateFactory.newLatLngZoom(center, zoom_level));
                }
                if (sea_marker != null){
                    sea_marker.remove();
                    sea_marker=map_go.addMarker(new MarkerOptions().position(search).title(go_name).zIndex(2));
                }
            }
            //計算箭頭的旋轉角度
            float bearing = calculateBearing(me_lat, me_lon, sea_lat, sea_lon);
            //iv_arrow.setRotation(bearing-90); // 設置箭頭旋轉角度
            answer = String.format("兩地的距離為：\n%.3f 公里\n兩地的方位角為：\n%d 度",distance,Math.round(bearing));
            first_b = bearing;
            tv_length.setText(answer);
        }
    }

    GoogleMap map_go = null;
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map_go = googleMap; //map準備好時，googleMap接到map上
        //camera高度
        map_go.moveCamera(CameraUpdateFactory.zoomTo(10));
        map_go.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        String markerTitle = marker.getTitle();

        if (markerTitle.equals(go_name)) {
            // 打開 Google 搜尋
            String googleSearchUrl = "https://www.google.com/search?q=" + markerTitle;
            Intent it_net = new Intent(Intent.ACTION_VIEW);
            it_net.setData(Uri.parse(googleSearchUrl));
            startActivity(it_net);
        }

        return false;
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

    public static float calculateBearing(double lat1, double lon1, double lat2, double lon2) {
        // 將經緯度從度轉為弧度
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // 計算方位角
        double deltaLon = lon2Rad - lon1Rad;
        double y = Math.sin(deltaLon) * Math.cos(lat2Rad);
        double x = Math.cos(lat1Rad) * Math.sin(lat2Rad) -
                Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(deltaLon);
        double bearingRad = Math.atan2(y, x);

        // 將結果轉換為度，並確保範圍在 0° 到 360° 之間
        float bearing = (float) Math.toDegrees(bearingRad);
        return (bearing + 360) % 360;  // 確保角度範圍在 0° 到 360° 之間
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMap();
                initLocationUpdates();
            } else {
                Toast.makeText(this, "權限被拒絕，請到設定中開啟定位權限。", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_end){
            finish();
        }
    }

    private SensorManager sensorManager;
    private Sensor rotationSensor;
    private float currentAzimuth = 0f; // 當前手機方位角
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotationMatrix = new float[9];
            float[] orientation = new float[3];

            // 將旋轉向量轉換為旋轉矩陣
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            SensorManager.getOrientation(rotationMatrix, orientation);

            // 方位角（azimuth），以北為0度，順時針遞增
            float azimuth = (float) Math.toDegrees(orientation[0]);
            azimuth = (azimuth + 360) % 360; // 確保範圍為 0-360 度

            currentAzimuth = azimuth;

            // 計算箭頭應該旋轉的角度
            updateArrowRotation();
        }
    }

    private void updateArrowRotation() {
        ImageView iv_arrow = findViewById(R.id.iv_arrow);

        // 計算箭頭應該指向的角度
        float bearingToTarget = calculateBearing(me_lat, me_lon, sea_lat, sea_lon);
        float rotation = (bearingToTarget - currentAzimuth + 360) % 360;

        iv_arrow.setRotation(rotation-90);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 感測器準確度變化時可處理，但這裡可以留空
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI);
    }

}