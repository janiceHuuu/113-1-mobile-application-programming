package ncku.geomatics.group4_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity_add extends AppCompatActivity
        implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LatLng selectedLocation;
    private EditText etName;
    private CheckBox cbLinePay, cbStreetPay, cbUberEats, cbFoodPanda;
    private CheckBox cbChinese, cbItalian, cbJapanese, cbThai, cbKorean, cbAmerican,cbDrink;
    private Button btnAddRestaurant;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add);

        etName = findViewById(R.id.editTextTextPersonName);
        cbLinePay = findViewById(R.id.checkBox);
        cbStreetPay = findViewById(R.id.checkBox2);
        cbUberEats = findViewById(R.id.checkBox4);
        cbFoodPanda = findViewById(R.id.checkBox3);
        cbChinese = findViewById(R.id.checkBox5);
        cbItalian = findViewById(R.id.checkBox6);
        cbJapanese = findViewById(R.id.checkBox7);
        cbThai = findViewById(R.id.checkBox8);
        cbKorean = findViewById(R.id.checkBox9);
        cbAmerican = findViewById(R.id.checkBox10);
        btnAddRestaurant = findViewById(R.id.btnAddRestaurant);
        cbDrink = findViewById(R.id.checkBox11);
        // 加載地圖
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 新增店家按鈕監聽事件
        btnAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRestaurant();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // 初始位置 (台南成大)
        LatLng initialLocation = new LatLng(22.9997, 120.2270);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 15));

        // 地圖點擊事件 - 標記位置
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectedLocation = latLng;
                mMap.clear();  // 清除先前標記
                mMap.addMarker(new MarkerOptions().position(latLng).title("店家位置"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        });
    }
    private void saveRestaurant() {
        String name = etName.getText().toString();
        String u = "0",p="0";
        String l="0",j="0",h="0",c="0",w="0",jp="0",k="0",i="0",t="0",d="0";
        // 支付方式
        if (cbLinePay.isChecked()) {
            l="1";
        }
        if (cbStreetPay.isChecked()) {
            j="1";
        }
        // 外送平台
        if (cbUberEats.isChecked()) u="1";
        if (cbFoodPanda.isChecked()) p="1";

        // 類型選擇
        if (cbChinese.isChecked()) c="1";
        if (cbItalian.isChecked()) i="1";
        if (cbJapanese.isChecked()) jp="1";
        if (cbThai.isChecked()) t="1";
        if (cbKorean.isChecked()) k="1";
        if (cbAmerican.isChecked()) w="1";
        if(cbDrink.isChecked()) d="1";
        // 驗證輸入資料
        if (selectedLocation != null && !name.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("foodpanda", p);
            intent.putExtra("linepay",l);
            intent.putExtra("ubereats",u);
            intent.putExtra("jie",j);
            intent.putExtra("chin",c);
            intent.putExtra("west",w);
            intent.putExtra("kore",k);
            intent.putExtra("japan",jp);
            intent.putExtra("ita",i);
            intent.putExtra("tai",t);
            intent.putExtra("dri",d);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "請選擇地圖上的位置並填寫店名", Toast.LENGTH_SHORT).show();
        }
    }

}