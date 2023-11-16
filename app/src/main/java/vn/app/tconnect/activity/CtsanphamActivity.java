package vn.app.tconnect.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import vn.app.tconnect.R;
import vn.app.tconnect.models.BanPhimModel;
import vn.app.tconnect.models.ChuotModel;
import vn.app.tconnect.models.LaptopModel;
import vn.app.tconnect.models.ManHinhModel;
import vn.app.tconnect.models.TaiNgheModel;

public class CtsanphamActivity extends AppCompatActivity {
        ImageView ctimg;
        TextView name,price;
        Button addtocart;
        Toolbar toolbar;

        ManHinhModel manHinhModel = null;
        BanPhimModel banPhimModel = null;
        ChuotModel chuotModel = null;
        LaptopModel laptopModel = null;
        TaiNgheModel taiNgheModel = null;
        FirebaseFirestore firestore;
        FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctsanpham);
        toolbar = findViewById(R.id.toolbar_ctsp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        addtocart = findViewById(R.id.add_tocart);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedtocart();
            }

            private void addedtocart() {
                final HashMap<String,Object> cartMap = new HashMap<>();
                cartMap.put("productImage",manHinhModel.getImg_url());
                cartMap.put("productName",manHinhModel.getName());
                cartMap.put("productPrice",manHinhModel.getPrice());
                firestore.collection("addtocart").document(auth.getCurrentUser().getUid()).collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(CtsanphamActivity.this,"Added to a cart ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

        final Object object =getIntent().getSerializableExtra("Chitiet");
        if(object instanceof ManHinhModel){
            manHinhModel = (ManHinhModel) object;
        }

        ctimg = findViewById(R.id.ct_img);
        name = findViewById(R.id.ct_name);
        price = findViewById(R.id.ct_price);
        if(manHinhModel != null)
        {
            Glide.with(getApplicationContext()).load(manHinhModel.getImg_url()).into(ctimg);
            name.setText(manHinhModel.getName());
            price.setText(manHinhModel.getPrice());
        }
        addtocart = findViewById(R.id.add_tocart);
        if(object instanceof BanPhimModel){
            banPhimModel = (BanPhimModel) object;
        }

        ctimg = findViewById(R.id.ct_img);
        name = findViewById(R.id.ct_name);
        price = findViewById(R.id.ct_price);
        if(banPhimModel != null)
        {
            Glide.with(getApplicationContext()).load(banPhimModel.getImg_url()).into(ctimg);
            name.setText(banPhimModel.getName());
            price.setText(banPhimModel.getPrice());
        }
        addtocart = findViewById(R.id.add_tocart);

        if(object instanceof ChuotModel){
            chuotModel = (ChuotModel) object;
        }

        ctimg = findViewById(R.id.ct_img);
        name = findViewById(R.id.ct_name);
        price = findViewById(R.id.ct_price);
        if(chuotModel != null)
        {
            Glide.with(getApplicationContext()).load(chuotModel.getImg_url()).into(ctimg);
            name.setText(chuotModel.getName());
            price.setText(chuotModel.getPrice());
        }
        addtocart = findViewById(R.id.add_tocart);

        if(object instanceof LaptopModel){
            laptopModel = (LaptopModel) object;
        }

        ctimg = findViewById(R.id.ct_img);
        name = findViewById(R.id.ct_name);
        price = findViewById(R.id.ct_price);
        if(laptopModel != null)
        {
            Glide.with(getApplicationContext()).load(laptopModel.getImg_url()).into(ctimg);
            name.setText(laptopModel.getName());
            price.setText(laptopModel.getPrice());
        }
        addtocart = findViewById(R.id.add_tocart);

        if(object instanceof TaiNgheModel){
            taiNgheModel = (TaiNgheModel) object;
        }

        ctimg = findViewById(R.id.ct_img);
        name = findViewById(R.id.ct_name);
        price = findViewById(R.id.ct_price);
        if(taiNgheModel != null)
        {
            Glide.with(getApplicationContext()).load(taiNgheModel.getImg_url()).into(ctimg);
            name.setText(taiNgheModel.getName());
            price.setText(taiNgheModel.getPrice());
        }
        addtocart = findViewById(R.id.add_tocart);
    }
}