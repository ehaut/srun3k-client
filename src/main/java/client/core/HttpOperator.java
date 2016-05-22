package client.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
	private CloseableHttpClient client;
	private HttpPost post;
	private HttpResponse response;
	private StringEntity entity;
	HashMap<String, String> headers;
	
	public HttpOperator(URI _uri) {
		uri = _uri;
		client = HttpClients.createDefault();
		post = new HttpPost(uri);
		headers = new HashMap<String, String>();
		
	}
	
	public void PushHeader(String name, String value){
		headers.put(name, value);
	}
	public void setEntity(String e){
		try {
			entity = new StringEntity(e);
		} catch (UnsupportedEncodingException e1) {
			throw new RuntimeException("make entity error");
		}
	}
	public String post(){
		if (client == null)
			throw new RuntimeException("httpclient is null");
		if (post == null)
			throw new RuntimeException("post is null");
		for (Map.Entry<String, String> entry : headers.entrySet()){
			post.addHeader(entry.getKey(), entry.getValue());
		}
		post.setEntity(entity);
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() != 200 )
				throw new ResponseCodeError();
			if (response.getEntity() == null) return null;
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new RuntimeException();
		}finally{
			try {
				client.close();
			} catch (IOException e) {
				throw new RuntimeException();
			}
		}
	}
	public void interrupt() throws OperatorCloseException{
		if (client != null){
			try {
				client.close();
			} catch (IOException e) {
				throw new OperatorCloseException();
			}
		}
	}
	

}
