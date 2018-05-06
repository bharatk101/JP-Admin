package bharat.example.com.jp.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import bharat.example.com.jp.models.Token;

/**
 * Created by bharat on 3/1/18.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    String uemail;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenRefresh = FirebaseInstanceId.getInstance().getToken();
        if (FirebaseAuth.getInstance().getCurrentUser() !=  null)
            updateTokenToFirebase(tokenRefresh);
    }

    private void updateTokenToFirebase(String tokenRefresh) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");

        Token token = new Token(tokenRefresh,true);
        uemail =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        tokens.child(uemail).setValue(true);
    }


}
