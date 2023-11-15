package vn.app.tconnect.activity;

import static vn.app.tconnect.activity.MainActivity.redirectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import vn.app.tconnect.R;
import vn.app.tconnect.adapter.ManHinhAdapter;
import vn.app.tconnect.models.ManHinhModel;

public class ManhinhActivity extends AppCompatActivity {
    private List<ManHinhModel> manHinhModelList;
    private ManHinhAdapter manHinhAdapter;
    private FirebaseFirestore db;
    RecyclerView manHinhRec;
    ImageView home,noti,favorite,cart;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinh);
        db =FirebaseFirestore.getInstance();

        home= findViewById(R.id.footer_home);
        noti = findViewById(R.id.footer_notification);
        favorite = findViewById(R.id.footer_favorite);
        cart= findViewById(R.id.footer_cart);
        manHinhRec=findViewById(R.id.manhinh_Rec);
        back=findViewById(R.id.backbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ManhinhActivity.this, MainActivity.class);
            }
        });

        manHinhRec.setLayoutManager(new GridLayoutManager(this, 2));
        manHinhModelList= new ArrayList<>();
        manHinhAdapter= new ManHinhAdapter(this,manHinhModelList);
        manHinhRec.setAdapter(manHinhAdapter);
        db.collection("ManHinh")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ManHinhModel manHinhModel =document.toObject(ManHinhModel.class);
                            manHinhModelList.add(manHinhModel);
                            manHinhAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getParent(),"Error"+task.getException(),Toast.LENGTH_SHORT);

                    }
                }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManhinhActivity.this,MainActivity.class));
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManhinhActivity.this,MainActivity.class));
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManhinhActivity.this,MainActivity.class));
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManhinhActivity.this,GiohangActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ManhinhActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}