package repository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.http.HttpClient;

public class HttpRequest {
    private String base_Url = "https://eksisozluk.com";
    private OkHttpClient client;
    private Request request;

    public HttpRequest()
    {
        this.client = new OkHttpClient();
    }


    public Document DefaultPage() throws IOException {
        this.request = new Request.Builder().url(base_Url).build();
        String html = client.newCall(request).execute().body().string();
        return Jsoup.parse(html);
    }

    public Document GetPage(String endpoint) throws IOException {
        this.request = new Request.Builder().url(base_Url+endpoint).build();
        String html = client.newCall(request).execute().body().string();
        return Jsoup.parse(html);
    }
}
