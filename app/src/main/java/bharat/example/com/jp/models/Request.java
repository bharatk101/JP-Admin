package bharat.example.com.jp.models;

import java.util.List;

/**
 * Created by bharat on 2/28/18.
 */

public class Request {

    private String name;
    private String phone;
    private String email;
    private String address;
    private String total;
    private List<Order> foods;
    private String status;
    private String uid;
    private String alternateMobile;
    private String landMark;
    private String datetime;

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Request() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Request(String name, String phone, String email, String address, String total, String uid, String datetime, String landMark,
                   String alternateMobile, String status, List<Order> foods) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.uid = uid;
        this.datetime = datetime;
        this.landMark = landMark;
        this.alternateMobile = alternateMobile;
        this.status = status;

    }
}
