package ncku.geomatics.f64126147_ch08_09_practice7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,
        SearchView.OnQueryTextListener
{
    ArrayList<String> listItems = new ArrayList<>();
    ArrayList<String> filter = new ArrayList<>();
    ArrayAdapter<String> listAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.lv);
        listItems.add("小妞炒飯");
        listItems.add("牛伯麵店");
        listItems.add("余家蔬菜麵");
        listItems.add("新增店家");
        listItems.add("隨機店家");
        filter = new ArrayList<>(listItems);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filter);
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);

        SearchView sv = findViewById(R.id.sv);
        sv.setOnQueryTextListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int size = listItems.size();
        if(position != size-1 && position != size-2){
            Intent it = new Intent();
            it.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("https://www.google.com/search?q="+listItems.get(position));
            it.setData(uri);
            startActivity(it);
        }
        if(position == size-2){
            Intent it = new Intent(this,MainActivity2.class);
            startActivityForResult(it,2);
        }
        if(position == size-1){
            if(size>2){
                Random random = new Random();
                int randomIndex = random.nextInt(size-2);
                String randomRestaurant = listItems.get(randomIndex);
                Intent it = new Intent();
                it.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://www.google.com/search?q="+randomRestaurant);
                it.setData(uri);
                startActivity(it);
            }
            else{
                Toast t = Toast.makeText(this,"清單內無任何店家，請先新增店家", Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        int size = listItems.size();
        if(position != size-1 && position != size-2){
            listItems.remove(position);
            filter.clear();
            filter.addAll(listItems);
            listAdapter.notifyDataSetChanged();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                String a = data.getStringExtra("new_re");
                listItems.remove(listItems.size()-1);
                listItems.remove(listItems.size()-1);
                listItems.add(a);
                listItems.add("新增店家");
                listItems.add("隨機店家");
                filter.clear();
                filter.addAll(listItems);
                listAdapter.notifyDataSetChanged();
                Snackbar snack = Snackbar.make(findViewById(R.id.root),"新增店家成功",Snackbar.LENGTH_LONG);
                snack.show();
            }
            if(resultCode == RESULT_CANCELED){
                Snackbar snack = Snackbar.make(findViewById(R.id.root),"您已取消新增店家",Snackbar.LENGTH_LONG);
                snack.show();
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter.clear();
        if(newText.isEmpty()){
            filter.addAll(listItems);
        }
        else{
            for(int i=0; i<listItems.size()-2; i++){
                if(listItems.get(i).toLowerCase().contains(newText.toLowerCase())){
                    filter.add(listItems.get(i));
                }
            }
        }
        listAdapter.notifyDataSetChanged();
        return false;
    }
}