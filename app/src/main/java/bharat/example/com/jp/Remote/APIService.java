package bharat.example.com.jp.Remote;


import bharat.example.com.jp.models.MyResponse;
import bharat.example.com.jp.models.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by bharat on 3/1/18.
 */

public interface APIService {
    @Headers(

            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAA7rqZbc:APA91bGwvA3L8sotoTQ8tiiA8vJpIkfJjlOs89D45uB-Ab-KbUFPD53tOHcxrEujpCiE9UfZzIuZ9sXovR180ZJ9MzuPFCQPVKr2spJ_k4K8oNlCm8Grjyn8u5tKdsaPakeYwY344n8T"
            }

    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
