package com.example.roombooking1.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roombooking1.R;
import com.example.roombooking1.activity.adapter.AvalableRoomsAdapter;
import com.example.roombooking1.activity.model.AvalableRoomsPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvalableRoomsActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<AvalableRoomsPojo> a1;
    SharedPreferences sharedPreferences;
    String uname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avalable_rooms);

       // sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        //uname = sharedPreferences.getString("user_name", "");

        getSupportActionBar().setTitle("Available Rooms");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();
        serverData();
    }
    public void serverData(){
        progressDialog = new ProgressDialog(AvalableRoomsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<AvalableRoomsPojo>> call = service.getAvailableRooms();
        call.enqueue(new Callback<List<AvalableRoomsPojo>>() {
            @Override
            public void onResponse(Call<List<AvalableRoomsPojo>> call, Response<List<AvalableRoomsPojo>> response) {
                //Toast.makeText(AvalableRoomsActivity.this,"ddddd   "+response.body().size(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(AvalableRoomsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    // Toast.makeText(MyAddsActivity.this,"ddddd   "+response.body(),Toast.LENGTH_SHORT).show();
                    list_view.setAdapter(new AvalableRoomsAdapter(a1, AvalableRoomsActivity.this));


                }
            }

            @Override
            public void onFailure(Call<List<AvalableRoomsPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AvalableRoomsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override                                                                                                                    //add this method in your program
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}


