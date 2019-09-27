
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HttpUtil {
    private static final String TAG = "HttpUtil";

    public static Call requestPost(String url, FormBody formBody) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .post(formBody)
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        return call;
    }

    public static Call requestPostJSON(String url, String json, HashMap<String, String> requestHeaderMap) {
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSONMediaType = MediaType
                .parse("application/json; charset=utf-8");
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (requestHeaderMap != null && !requestHeaderMap.isEmpty()) {
            for (Map.Entry<String, String> stringEntry : requestHeaderMap.entrySet()) {
                requestBuilder.addHeader(stringEntry.getKey(), stringEntry.getValue());
            }
        }
        Request request = requestBuilder
                .post(RequestBody.create(JSONMediaType, json)).build();

        Call call = okHttpClient.newCall(request);
        return call;
    }

    public static Call send(String url) {
        return send(url, null);
    }

    public static Call send(String url, Map<String, String> requestHeaderMap) {
        /*
        .connectTimeout(5, TimeUnit.SECONDS)//单位是秒
.readTimeout(20, TimeUnit.SECONDS)

         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.build();
        Request.Builder requestBuilder = new Request
                .Builder();
        if (requestHeaderMap != null && !requestHeaderMap.isEmpty()) {
            for (Map.Entry<String, String> stringEntry : requestHeaderMap.entrySet()) {
                requestBuilder.addHeader(stringEntry.getKey(), stringEntry.getValue());
            }
        }
        Request request = requestBuilder
                .cacheControl(new CacheControl.Builder().maxStale(30, TimeUnit.SECONDS).build())
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        return call;
    }
}
