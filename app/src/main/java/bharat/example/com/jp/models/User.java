package bharat.example.com.jp.models;

/**
 * Created by bharat on 1/8/18.
 */

public class User {

    private String name;
    private String email;
    private String phone;
    private String profile_image;
    private String  isStaff;
    private String user_id;

    public User(String user_id) {
        this.user_id = user_id;
    }

    public User(String name, String email, String phone, String profile_image, String  isStaff, String user_id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profile_image = profile_image;
        this.isStaff = isStaff;
        this.user_id = user_id;
    }

    public User() {

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

    public String  getPhone() {
        return phone;
    }

    public void setPhone(String  phone_no) {
        this.phone = phone_no;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getIsStaff() {

        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone_no='" + phone + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", isStaff='" + isStaff + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}

