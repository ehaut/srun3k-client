package client.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.squareup.okhttp.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import client.exception.OperatorCloseException;
import client.exception.ResponseCodeError;

public class HttpOperator {
	private URI uri;
    OkHttpClient client;
	private HttpPost post;
	private Response response;
	private Map<String, String> params;
	HashMap<String, String> headers;
	
	public HttpOperator(URI _uri) {
		uri = _uri;
		client = new OkHttpClient();
		post = new HttpPost(uri);
		headers = new HashMap<String, String>();

		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0 ");
	}
	
	public void PushHeader(String name, String value){
		headers.put(name, value);
	}
	public void setParamMap(Map<String, String> pm){
		params = pm;
	}
	public String post(){
        String result = "";
        try {
            FormEncodingBuilder form = new FormEncodingBuilder();
            if (params != null) {
                for (String key : params.keySet()) {
                    form.addEncoded(key, params.get(key));
                }
            }

            Request request = new Request.Builder()
                    .url(uri.toURL())
                    .post(form.build())
                    .build();
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                result = response.body().string();
            } else {
                throw new IOException("网络连接错误:" + response);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
	public void interrupt() throws OperatorCloseException{
		if (client != null){
			try {
				// client.close();
			} catch (Exception e) {
				throw new OperatorCloseException();
			}
		}
	}
	

}
