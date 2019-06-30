package ir.rastech.analytic.test.jerseytest;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by hassan on 17/10/2016.
 */
public class RetrofitFactory {

    public <T> T getRetApi(Class<T> c, String path) {
        String[] headers = null;
        if (path == null) {
            return null;
        }
        final OkHttpClient okHttpClient = getOkHttpClient(headers);
        Retrofit retrofit;

        retrofit = new Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
        T t = retrofit.create(c);
        return t;
    }

    private OkHttpClient getOkHttpClient(String[] headers) {

        final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().readTimeout(6000, TimeUnit.SECONDS);
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder request = chain.request().newBuilder();
                if (headers != null) for (int i = 0; i < headers.length; i += 2) {
                    request.addHeader(headers[i], headers[i + 1]);
                }
                return chain.proceed(request.build());
            }
        });
        okHttpClient.connectTimeout(6000, TimeUnit.SECONDS)
                .writeTimeout(12000, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return okHttpClient.build();
    }
}
