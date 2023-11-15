package vn.app.tconnect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ánh xạ id cho các biến giao diện
        auth=FirebaseAuth.getInstance();
        db =FirebaseFirestore.getInstance();
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
        user = FirebaseAuth.getInstance().getCurrentUser();

        progressBar.setVisibility(View.VISIBLE);

            // Lấy tên hiển thị của người dùng
            String displayName = user.getDisplayName();
            hello.setText("Xin chào " + displayName + " !");

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

        imageList.add(new SlideModel("https://anphat.com.vn/media/banner/08_Nov21e513fbe8a0a90ce9feb308c034544d.jpg",ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://anphat.com.vn/media/banner/07_Nov5d4ab481c6e8a490c59b70115429df91.jpg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://anphat.com.vn/media/banner/01_Nova7004383d7438b8bdc521f1d0f5c0ac7.jpg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://anphat.com.vn/media/banner/07_Nov4b48efae74e25ad429c0af61f6992af3.jpg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://anphat.com.vn/media/banner/10_Nov930b99085b737f2ebff945eee2124796.jpg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://anphat.com.vn/media/banner/13_Nov44542b20d9e44fcf08b7fc979b831824.jpg", ScaleTypes.CENTER_CROP));

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
                redirectActivity(MainActivity.this, ManhinhActivity.class);
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
    private static final int BACK_PRESS_INTERVAL = 2000;
    private long backPressTime = 0;

    @Override
    public void onBackPressed() {
        if (backPressTime + BACK_PRESS_INTERVAL > System.currentTimeMillis()) {
            // Nếu thời gian giữa 2 lần nhấn back nhỏ hơn BACK_PRESS_INTERVAL, thoát ứng dụng
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        } else {
            // Nếu thời gian giữa 2 lần nhấn back lớn hơn BACK_PRESS_INTERVAL, reset lại thời gian
            backPressTime = System.currentTimeMillis();
            // Thông báo cho người dùng
            Toast.makeText(this, "Nhấn back thêm lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
    }
}