package vn.app.tconnect.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserModels {
    private String name;
    private String email;
    private String password; // Sửa tên thành password
    private String imageUri; // Thêm thuộc tính imageUri

    public UserModels() {
        // Default constructor required for calls to DataSnapshot.getValue(UserModels.class)
    }

    public UserModels(String name, String email, String password, String imageUri) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}