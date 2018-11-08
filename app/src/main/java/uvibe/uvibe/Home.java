package uvibe.uvibe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.IOException;

// Spotify Auth
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

// Networking
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// Activities
import uvibe.uvibe.Analyzing;

public class Home extends AppCompatActivity {
    public static final String CLIENT_ID = "fa6c626b0f8f4e57b9164d1c7c7c750c";
    public static final String REDIRECT_URI = "uvibeapp://callback";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0x10;
    public static final int AUTH_CODE_REQUEST_CODE = 0x11;

    private String mAccessToken;
    private String mAccessCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onRequestTokenClicked(View view) {
        final AuthenticationRequest request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN);
        AuthenticationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request);
    }

    private AuthenticationRequest getAuthenticationRequest(AuthenticationResponse.Type type) {
        return new AuthenticationRequest.Builder(CLIENT_ID, type, REDIRECT_URI)
                .setShowDialog(true)
                .setScopes(new String[]{"user-read-email", "user-top-read", "playlist-modify-private"})
                .setCampaign("uVibe")
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("ACTIVITY RESULT", "COMES BACK");
        final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);

        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();

            Intent intent = new Intent(getBaseContext(), Analyzing.class);
            intent.putExtra("access_token", mAccessToken);
            startActivity(intent);
        }
    }

    private Uri getRedirectUri() {
        return new Uri.Builder()
                .scheme(getString(R.string.com_spotify_sdk_redirect_scheme))
                .authority(getString(R.string.com_spotify_sdk_redirect_host))
                .build();
    }
}
