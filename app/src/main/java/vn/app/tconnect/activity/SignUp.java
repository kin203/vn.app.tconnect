package vn.app.tconnect.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import vn.app.tconnect.R;
import vn.app.tconnect.models.UserModels;


public class SignUp extends AppCompatActivity {
    EditText editTextFullname, editTextPassword, editTextEmail;
    Button buttonSignUp;
    ImageView avatar;
    TextView textViewLogin;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri imageUri;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        editTextFullname = findViewById(R.id.fullname);
        editTextPassword = findViewById(R.id.password);
        editTextEmail = findViewById(R.id.email);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.Login);
        avatar = findViewById(R.id.profileImg);
        progressBar = findViewById(R.id.SU_progessbar);
        progressBar.setVisibility(View.GONE);

        // Thêm sự kiện chọn ảnh từ thư viện
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    // Mở hộp thoại chọn ảnh từ thư viện
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    // Xử lý kết quả trả về từ hộp thoại chọn ảnh hoặc camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Hiển thị ảnh đã chọn lên ImageView
            avatar.setImageURI(imageUri);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Nếu bạn chụp ảnh từ camera, sử dụng imageUri để lưu trữ địa chỉ URI của ảnh
            // Bạn đã cài đặt imageUri trước khi mở camera trong phương thức createUser
            // Hiển thị ảnh đã chụp lên ImageView
            avatar.setImageURI(imageUri);
        }
    }

    private void createUser() {
        String userName = editTextFullname.getText().toString();
        String userEmail = editTextEmail.getText().toString();
        String userPassword = editTextPassword.getText().toString();
//        String userAvt=

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 8 ký tự!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = task.getResult().getUser().getUid();

                    // Tạo một tham chiếu đến thư mục trong Firebase Storage dựa trên ID của người dùng
                    StorageReference userImageRef = storageRef.child("user_images").child(userId + ".jpg");

                    // Upload ảnh lên Firebase Storage
                    userImageRef.putFile(imageUri)
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    // Lấy đường dẫn URL của ảnh đã tải lên
                                    userImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                        // Tạo một đối tượng UserModels với đường dẫn URL của ảnh
                                        UserModels userModels = new UserModels(userName, userEmail, userPassword, uri.toString());

                                        // Đặt tên hiển thị
                                        auth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(userName).build());

                                        // Thêm người dùng vào nút Users trong Firebase Realtime Database
                                        database.getReference().child("Users").child(userId).setValue(userModels);

                                        progressBar.setVisibility(View.GONE);

                                        Toast.makeText(SignUp.this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
                                    });
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(SignUp.this, "Lỗi khi tải ảnh lên: " + task1.getException(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUp.this, "Lỗi " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}