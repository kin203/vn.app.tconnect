package vn.app.tconnect.activity;

import static vn.app.tconnect.activity.MainActivity.redirectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import vn.app.tconnect.adapter.TaiNgheAdapter;
import vn.app.tconnect.models.TaiNgheModel;

public class TaingheActivity extends AppCompatActivity {
    private List<TaiNgheModel> taiNgheModelList;
    private TaiNgheAdapter taiNgheAdapter;
    private FirebaseFirestore db;
    RecyclerView taiNgheRec;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tainghe);
        db =FirebaseFirestore.getInstance();
        taiNgheRec=findViewById(R.id.tainghe_Rec);
        back=findViewById(R.id.backbutton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(TaingheActivity.this, MainActivity.class);
            }
        });

        taiNgheRec.setLayoutManager(new GridLayoutManager(this, 2));
        taiNgheModelList= new ArrayList<>();
        taiNgheAdapter= new TaiNgheAdapter(this,taiNgheModelList);
        taiNgheRec.setAdapter(taiNgheAdapter);
        db.collection("TaiNghe")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TaiNgheModel taiNgheModel =document.toObject(TaiNgheModel.class);
                                taiNgheModelList.add(taiNgheModel);
                                taiNgheAdapter.notifyDataSetChanged();
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
        startActivity(new Intent(TaingheActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}