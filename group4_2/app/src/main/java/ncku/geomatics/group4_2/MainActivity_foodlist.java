package ncku.geomatics.group4_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity_foodlist extends AppCompatActivity
implements DialogInterface.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    SQLiteDatabase db_fl_fl = null;
    ArrayList<String> foodlist_name = null;
    Cursor cs_fl=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_foodlist);

        AlertDialog.Builder ad_fl = new AlertDialog.Builder(this);
        ad_fl.setTitle("使用說明");
        ad_fl.setMessage("1. 點擊店家名稱即可進入該店家的選擇頁面\n2. 長按即可刪除預約");
        ad_fl.setCancelable(false);
        ad_fl.setIcon(android.R.drawable.btn_star);
        ad_fl.setPositiveButton("確定",this);
        ad_fl.show();

        ListView lv_fl = findViewById(R.id.lv_fl);
        lv_fl.setOnItemClickListener(this);
        lv_fl.setOnItemLongClickListener(this);

        db_fl_fl = openOrCreateDatabase("food", Context.MODE_PRIVATE,null);
    }

    private void requery_fl(){
        if (cs_fl != null && !cs_fl.isClosed()) {
            cs_fl.close(); // 關閉舊的 Cursor
        }
        ListView lv_fl = findViewById(R.id.lv_fl);
        foodlist_name = new ArrayList<>();
        ArrayAdapter<String> listAdapter_fl = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodlist_name);
        String cmd_fl = "SELECT * FROM testTable1";
        cs_fl = db_fl_fl.rawQuery(cmd_fl, null);

        if(cs_fl != null && cs_fl.moveToFirst()){
            // 遍歷 Cursor，將所有名稱加入 checklist_name
            do {
                foodlist_name.add(cs_fl.getString(1));

            } while (cs_fl.moveToNext());
            lv_fl.setAdapter(listAdapter_fl);
        }
        else{
            Toast t = Toast.makeText(this,"店家清單為空",Toast.LENGTH_SHORT);
            t.show();
            lv_fl.setAdapter(listAdapter_fl);
        }
    }

    public void on_click_back_fl(View v){
        finish();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        requery_fl();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it_fl = new Intent();
        it_fl.putExtra("food_name",foodlist_name.get(position));
        it_fl.setClass(this,MainActivity_choose.class);
        startActivity(it_fl);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (cs_fl.moveToPosition(position)) {
            int itemId = cs_fl.getInt(cs_fl.getColumnIndexOrThrow("_id"));
            int rowsDeleted = db_fl_fl.delete("testTable1", "_id=?", new String[]{String.valueOf(itemId)});

            if (rowsDeleted > 0) {
                Toast.makeText(this, "店家已刪除", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "刪除失敗，請檢查ID", Toast.LENGTH_SHORT).show();
            }
            requery_fl(); // 重新加載資料
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requery_fl();
    }
}