package app.awitcha.asynctasklistview.utill;

import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Song-rit Maleerat on 5/9/2559.
 */
public class OkHttpRequest {

    public final String mTag = OkHttpRequest.this.getClass().getSimpleName();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public Message HttpGetMessage(String url) throws IOException {
        Message message = new Message();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .build();

        Log.d(mTag, "HTTPGet: " + url);

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {

            if(response.isSuccessful()) {
                message.obj = response.body().string();
                message.what = 1;
            } else {
                message.obj = response.body().string();
                message.what = 0;
            }
            return message;
        }
    }
}
