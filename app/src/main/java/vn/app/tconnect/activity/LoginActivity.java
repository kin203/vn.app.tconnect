package vn.app.tconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import vn.app.tconnect.R;

public class LoginActivity extends AppCompatActivity  {
    Button btnLogin;
    EditText editTextEmail,editTextPassword;
    TextView textView;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        editTextEmail =findViewById(R.id.SI_email);
        editTextPassword= findViewById(R.id.SI_password);
        btnLogin = findViewById(R.id.btnLogin);
        textView= findViewById(R.id.textviewSignUp);
        progressBar= findViewById(R.id.LG_progessbar);
        progressBar.setVisibility(View.GONE);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUp.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loginUser() {
        String userEmail = editTextEmail.getText().toString();
        String userPassword = editTextPassword.getText().toString();

        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"Mật khẩu không được bỏ trống !!!" ,Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this,"Email không được bỏ trống !!!" ,Toast.LENGTH_SHORT).show();
            return;
        }

        if(userPassword.length()<8){
            Toast.makeText(this,"Mật khẩu không được ít hơn 8 ký tự !!!" ,Toast.LENGTH_SHORT).show();
            return;
        }

        //
        auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);

//                    UserModels userModels= new UserModels(userEmail,userPassword);
//                    String id = task.getResult().getUser().getUid();
//                    database.getReference().child("Users").child(id).setValue(userModels);

                    Toast.makeText(LoginActivity.this,"Đăng Nhập thành công !!!" ,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Lỗi "+ task.getException() ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}