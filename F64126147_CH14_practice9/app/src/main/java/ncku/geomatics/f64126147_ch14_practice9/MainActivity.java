package ncku.geomatics.f64126147_ch14_practice9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.tv.TvContentRating;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity
implements TextView.OnEditorActionListener, LocationListener,
        OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        DialogInterface.OnClickListener
{
    //經緯度單位轉換器
    Geocoder geo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv_arrow = findViewById(R.id.iv_arrow);
        //iv_arrow.setRotation(180);
        EditText et_name = findViewById(R.id.et_name);
        et_name.setOnEditorActionListener(this);

        //取得授權
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //綁定監聽器，5000毫秒、5公尺更新一次位置
        lm.requestLocationUpdates("gps", 5000, 5, this);

        geo = new Geocoder(this, Locale.getDefault());
        SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        smf.getMapAsync(this);

    }

    LatLng me = null;
    String s = null,answer=null;
    LatLng search = null, center = null;
    double sea_lat = 0, sea_lon = 0;
    Marker me_marker=null,sea_marker=null;
    float first_b = 0;

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        EditText et_name = findViewById(R.id.et_name);
        TextView tv_show = findViewById(R.id.tv_show);
        ImageView iv_arrow = findViewById(R.id.iv_arrow);

        if (actionId == EditorInfo.IME_ACTION_DONE) { //輸入完成後
            s = et_name.getText().toString();
            List<Address> add_list = null;

            try {
                add_list = geo.getFromLocationName(s, 30);
                if (add_list.isEmpty()){
                    AlertDialog .Builder ad = new AlertDialog.Builder(this);
                    ad.setTitle("輸入錯誤");
                    ad.setMessage("找不到您所查詢的地址，請重新輸入");
                    ad.setCancelable(false);
                    ad.setIcon(android.R.drawable.btn_star);
                    ad.setPositiveButton("確定",this);
                    ad.show();
                }
                else {
                    Address c = add_list.get(0);
                    sea_lat = c.getLatitude();
                    sea_lon = c.getLongitude();
                    search = new LatLng(sea_lat, sea_lon);

                    if (sea_marker != null) {
                        sea_marker.remove();
                    }

                    if (map != null && !add_list.isEmpty()) {
                        sea_marker = map.addMarker(new MarkerOptions().position(search).title(s).zIndex(2));
                        // 計算中心點
                        double center_lat = (me_lat + sea_lat) / 2;
                        double center_lon = (me_lon + sea_lon) / 2;
                        center = new LatLng(center_lat, center_lon);
                        map.animateCamera(CameraUpdateFactory.newLatLng(center));
                    }

                    //設定兩地的中心
                    double distance = calculateDistance(me_lat, me_lon, sea_lat, sea_lon);

                    //動態設定相機高度
                    int zoom_level = 0;
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
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, zoom_level));

                    //計算箭頭的旋轉角度
                    float bearing = calculateBearing(me_lat, me_lon, sea_lat, sea_lon);
                    iv_arrow.setRotation(bearing - 90); // 設置箭頭旋轉角度
                    answer = "兩地的距離為：\n" + distance + "公里\n" + "兩地的夾角為：\n" + bearing + "度";
                    first_b = bearing;
                    tv_show.setText(answer);
                }
            } catch (IOException e) {
                e.printStackTrace();
                tv_show.setText(e + "");
            }
        }
        return false;
    }

    double me_lat = 0, me_lon = 0;

    @Override
    public void onLocationChanged(@NonNull Location location) {
        TextView tv_show = findViewById(R.id.tv_show);
        ImageView iv_arrow = findViewById(R.id.iv_arrow);
        me_lat = location.getLatitude();
        me_lon = location.getLongitude();
        me = new LatLng(me_lat, me_lon);
        if(me_marker != null){
            me_marker.remove();
        }
        me_marker = map.addMarker(new MarkerOptions().position(me).title("我的位置").zIndex(1));
        if(me_lat !=0 && me_lon!=0 && sea_lat!=0 && sea_lon!=0){
            //設定兩地的中心
            double distance = calculateDistance(me_lat, me_lon, sea_lat, sea_lon);
            tv_show.setText("兩地的距離為：\n"+distance+"公里");
            //計算箭頭的旋轉角度
            float bearing = calculateBearing(me_lat, me_lon, sea_lat, sea_lon);
            iv_arrow.setRotation(bearing-90); // 設置箭頭旋轉角度
            answer.replace((char) first_b, (char) bearing);
            first_b = bearing;
            tv_show.setText(answer);
        }
    }

    GoogleMap map = null;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap; //map準備好時，googleMap接到map上
        //camera高度
        map.moveCamera(CameraUpdateFactory.zoomTo(10));
        map.setOnMarkerClickListener(this);
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
    public boolean onMarkerClick(@NonNull Marker marker) {
        String markerTitle = marker.getTitle();

        if (markerTitle.equals(s)) {
            // 打開 Google 搜尋
            String googleSearchUrl = "https://www.google.com/search?q=" + markerTitle;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(googleSearchUrl));
            startActivity(intent);
        }

        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}