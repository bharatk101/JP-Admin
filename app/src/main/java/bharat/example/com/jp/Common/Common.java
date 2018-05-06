package bharat.example.com.jp.Common;

import com.jaredrummler.materialspinner.MaterialSpinner;

import bharat.example.com.jp.Remote.APIService;
import bharat.example.com.jp.Remote.RetrofitClient;
import bharat.example.com.jp.models.Order;
import bharat.example.com.jp.models.Request;
import bharat.example.com.jp.models.User;

/**
 * Created by bharat on 2/2/18.
 */

public class Common {

    public static final String USER_KEY = "users";
    public static User currentUser;
    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";
    public static Request currentRequest;
    public static String currentOrder;
    public static Order cOrder;
    public static String  key;
    public static MaterialSpinner spin;

    private static final String BASE_URL = "https://fcm.googleapis.com/";

    public static APIService getFCMService(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

    }

    public static String convertCodeToString(String code){
        if (code.equals("0"))
            return "Placed";
        else if (code.equals("1"))
            return "Confirmed";
        else if(code.equals("2"))
            return "Out for Delivery";
        else if (code.equals("3"))
            return "Delivered";
        else if (code.equals("4"))
            return "Delivery Attempted";
        else if (code.equals("5"))
            return "Delivery Failed";
        else if (code.equals("6"))
            return "Cancelled due to shop being closed";
        else if (code.equals("7"))
            return "Cancelled due to out of delivery area";
        else
            return "Cancelled due to no delivery currently available";
    }


}
