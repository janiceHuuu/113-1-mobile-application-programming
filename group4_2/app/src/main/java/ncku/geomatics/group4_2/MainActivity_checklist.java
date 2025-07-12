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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity_checklist extends AppCompatActivity
implements DialogInterface.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
    SQLiteDatabase db_cl_cl = null;
    ArrayList<String> checklist_name = null;
    Cursor cs_cl=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_checklist);

        AlertDialog.Builder ad_cl = new AlertDialog.Builder(this);
        ad_cl.setTitle("使用說明");
        ad_cl.setMessage("1. 點擊預約內容即可進入該餐廳的選擇頁面\n2. 長按即可取消預約");
        ad_cl.setCancelable(false);
        ad_cl.setIcon(android.R.drawable.btn_star);
        ad_cl.setPositiveButton("確定",this);
        ad_cl.show();

        ListView lv_cl = findViewById(R.id.lv_cl);
        lv_cl.setOnItemClickListener(this);
        lv_cl.setOnItemLongClickListener(this);

        db_cl_cl = openOrCreateDatabase("checklist", Context.MODE_PRIVATE,null);
    }

    private void requery_cl(){
        ListView lv_cl = findViewById(R.id.lv_cl);
        checklist_name = new ArrayList<>();
        String cmd_cl = "SELECT rowid AS _id,name , '年份：' || year || '年' AS year_2," +
                "'日期：' || month || '/' || day AS date,'時間：' ||  hour || ':' || minute AS time," +
                "'人數：' || people || '人'AS people_2 FROM testTable1";
        cs_cl = db_cl_cl.rawQuery(cmd_cl, null);
        if(cs_cl != null && cs_cl.moveToFirst()){
            // 遍歷 Cursor，將所有名稱加入 checklist_name
            do {
                checklist_name.add(cs_cl.getString(cs_cl.getColumnIndexOrThrow("name")));
            } while (cs_cl.moveToNext());
            if (cs_cl.getCount()>0){
                String[] N = new String[]{"name","year_2","date","time","people_2"};
                SimpleCursorAdapter ad = new SimpleCursorAdapter(
                        this, R.layout.item, cs_cl, N,
                        new int[]{R.id.item_name, R.id.item_year,R.id.item_date,R.id.item_time,R.id.item_people}, 0);
                lv_cl.setAdapter(ad) ;
            }
            else{
                lv_cl.setAdapter(null);
            }
        }
        else{
            Toast t = Toast.makeText(this,"預約清單為空",Toast.LENGTH_SHORT);
            t.show();
            lv_cl.setAdapter(null);
        }
    }

    public void on_click_back_cl(View v){
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it_cl = new Intent();
        it_cl.putExtra("food_name",checklist_name.get(position));
        it_cl.setClass(this,MainActivity_choose.class);
        startActivity(it_cl);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        db_cl_cl.delete("testTable1", "rowid = ?", new String[]{String.valueOf(id)});
        requery_cl();
        Toast.makeText(this, "預約已刪除", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        requery_cl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requery_cl();
    }

}