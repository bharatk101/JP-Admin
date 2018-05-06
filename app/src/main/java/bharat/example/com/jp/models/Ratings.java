package bharat.example.com.jp.models;

/**
 * Created by bharat on 3/6/18.
 */

public class Ratings {

    private String uid;
    private String fid;
    private String rateValue;
    private String comment;
    private String userName;
    private String dateTime;
    private String foodName;
    private String userImage;
    private String userPhone;


    public Ratings() {
    }

    public Ratings(String uid, String fid, String rateValue, String comment, String userName, String dateTime, String foodName, String userImage, String userPhone) {
        this.uid = uid;
        this.fid = fid;
        this.rateValue = rateValue;
        this.comment = comment;
        this.userName = userName;
        this.dateTime = dateTime;
        this.foodName = foodName;
        this.userImage = userImage;
        this.userPhone = userPhone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
