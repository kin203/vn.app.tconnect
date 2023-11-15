package vn.app.tconnect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import vn.app.tconnect.R;
import vn.app.tconnect.adapter.RecommendedAdapter;
import vn.app.tconnect.adapter.SaleAdapter;
import vn.app.tconnect.models.FlashSaleModel;
import vn.app.tconnect.models.RecommendedModel;

public class MainActivity extends AppCompatActivity  {
    // khai báo các biến giao diện
    DrawerLayout drawerLayout;
    ProgressBar progressBar;
    ImageView menu,profile;
    TextView hello;
    LinearLayout home,mouse,keyboard,laptop,monitor,news,headset,contact,logout;
    //flash sale item
    private List<FlashSaleModel> flashSaleModelList;
    SaleAdapter saleAdapter;

    //Recomended item
    private List<RecommendedModel> recommendedModelList;
    RecommendedAdapter rcmAdapter;
    RecyclerView flashRec,recommendedRec;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ id cho các biến giao diện
        auth=FirebaseAuth.getInstance();
        db =FirebaseFirestore.getInstance();
        user=auth.getCurrentUser();
        flashRec=findViewById(R.id.pop_sale);
        recommendedRec= findViewById(R.id.pop_rcm_today);
        hello = findViewById(R.id.helloText);
        drawerLayout =findViewById(R.id.drawer_layout);
        menu =findViewById(R.id.menu);
        home =findViewById(R.id.home);
        mouse = findViewById(R.id.mouse);
        keyboard = findViewById(R.id.keyboard);
        laptop=findViewById(R.id.laptop);
        monitor=findViewById(R.id.monitor);
        news=findViewById(R.id.news);
        headset=findViewById(R.id.headset);
        contact=findViewById(R.id.contact);
        logout=findViewById(R.id.logout);
        progressBar =findViewById(R.id.progessbar);
        profile=findViewById(R.id.profile_btn);

        progressBar.setVisibility(View.VISIBLE);


        //Flash sale item
        flashRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        flashSaleModelList= new ArrayList<>();
        saleAdapter= new SaleAdapter(this,flashSaleModelList);
        flashRec.setAdapter(saleAdapter);
        db.collection("FlashSaleProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FlashSaleModel flashSaleModel =document.toObject(FlashSaleModel.class);
                                flashSaleModelList.add(flashSaleModel);
                                saleAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getParent(),"Error"+task.getException(),Toast.LENGTH_SHORT);

                        }
                    }
                });

        //RCM item
        recommendedRec.setLayoutManager(new GridLayoutManager(this, 2));
        recommendedModelList= new ArrayList<>();
        rcmAdapter= new RecommendedAdapter(this,recommendedModelList);
        recommendedRec.setAdapter(rcmAdapter);
        db.collection("RecommendedToday")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel =document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                rcmAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getParent(),"Error"+task.getException(),Toast.LENGTH_SHORT);

                        }
                    }
                });

        //SlideImage
        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list

        imageList.add(new SlideModel("https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:80/plain/https://dashboard.cellphones.com.vn/storage/iphone-15-11-2023.jpg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:80/plain/https://dashboard.cellphones.com.vn/storage/690-300-max-sliding-bluestone.jpg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:80/plain/https://dashboard.cellphones.com.vn/storage/samsung-galaxy-s23-ultra-sliding-th111.png", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://cdn2.cellphones.com.vn/insecure/rs:fill:690:300/q:80/plain/https://dashboard.cellphones.com.vn/storage/pova-5-sliding-th111.jpg", ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

        //menu
        menu.setOnClickListener(view -> openDrawer(drawerLayout));
        home.setOnClickListener(view -> recreate());
        mouse.setOnClickListener(view -> redirectActivity(MainActivity.this,ChuotActivity.class));
        keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivity.this, BanphimActivity.class);
            }
        });
        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ManhinhActivity.class));
            }
        });
        headset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivity.this, TaingheActivity.class);
            }
        });
        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivity.this, LaptopActivity.class);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                redirectActivity(MainActivity.this,LoginActivity.class);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(MainActivity.this,ProfileActivity.class);
            }
        });
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public static void redirectActivity(Activity activity,Class secondActivity){
        Intent intent = new Intent(activity,secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}