package vn.app.tconnect.activity;

import static vn.app.tconnect.activity.MainActivity.redirectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.app.tconnect.R;

public class ProfileActivity extends AppCompatActivity {
    TextView name, email;
    ImageView userImage;
    AppCompatButton logOut, backToMain, accountSetting, editLocation;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.Profile_name);
        email = findViewById(R.id.Profile_mail);
        userImage = findViewById(R.id.Profile_avt);
        logOut = findViewById(R.id.logOut);
        backToMain = findViewById(R.id.backtoMain);
        accountSetting = findViewById(R.id.btn_acc_setting);
        editLocation = findViewById(R.id.btn_location);

        user = FirebaseAuth.getInstance().getCurrentUser();

        name.setText((user.getDisplayName()));
        email.setText(user.getEmail());

        // Lấy đường dẫn ảnh từ Firebase Realtime Database
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        userRef.child("imageUri").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imageUri = dataSnapshot.getValue(String.class);

                    // Load ảnh từ đường dẫn và hiển thị bằng Glide
                    Glide.with(ProfileActivity.this).load(imageUri).into(userImage);
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectActivity(ProfileActivity.this, MainActivity.class);
//                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}