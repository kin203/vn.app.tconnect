package vn.app.tconnect.activity;

import static vn.app.tconnect.activity.MainActivity.redirectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import vn.app.tconnect.adapter.BanPhimAdapter;
import vn.app.tconnect.models.BanPhimModel;

public class BanphimActivity extends AppCompatActivity {
    private List<BanPhimModel> banPhimModelList;
    private BanPhimAdapter banPhimAdapter;
    private FirebaseFirestore db;
    RecyclerView banPhimRec;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banphim);
        db =FirebaseFirestore.getInstance();
        banPhimRec=findViewById(R.id.banphim_Rec);
        back=findViewById(R.id.backbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(BanphimActivity.this, MainActivity.class);
            }
        });

        banPhimRec.setLayoutManager(new GridLayoutManager(this, 2));
        banPhimModelList= new ArrayList<>();
        banPhimAdapter= new BanPhimAdapter(this,banPhimModelList);
        banPhimRec.setAdapter(banPhimAdapter);
        db.collection("BanPhim")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BanPhimModel banPhimModel =document.toObject(BanPhimModel.class);
                                banPhimModelList.add(banPhimModel);
                                banPhimAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getParent(),"Error"+task.getException(),Toast.LENGTH_SHORT);

                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BanphimActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}