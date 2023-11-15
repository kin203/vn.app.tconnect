package vn.app.tconnect.activity;

import android.os.Bundle;
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
import vn.app.tconnect.adapter.LaptopAdapter;
import vn.app.tconnect.models.LaptopModel;

public class LaptopActivity extends AppCompatActivity {
    private List<LaptopModel> laptopModelList;
    private LaptopAdapter laptopAdapter;
    private FirebaseFirestore db;
    RecyclerView lapTopRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        db =FirebaseFirestore.getInstance();
        lapTopRec=findViewById(R.id.laptop_Rec);

        lapTopRec.setLayoutManager(new GridLayoutManager(this, 2));
        laptopModelList= new ArrayList<>();
        laptopAdapter= new LaptopAdapter(this,laptopModelList);
        lapTopRec.setAdapter(laptopAdapter);
        db.collection("Laptop")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                LaptopModel laptopModel =document.toObject(LaptopModel.class);
                                laptopModelList.add(laptopModel);
                                laptopAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getParent(),"Error"+task.getException(),Toast.LENGTH_SHORT);

                        }
                    }
                });
    }
}