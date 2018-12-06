package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @author boudaa
 *
 */
public class JavaClient {

	public static CloseableHttpResponse postRequest(String url, Map<String, String> params) throws Exception {
		CloseableHttpResponse response = null;

		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			for (Map.Entry<String, String> entry : params.entrySet()) {

				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

			}

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = httpclient.execute(httpPost);

		} finally

		{
			if (response != null) {
				response.close();

			}
		}
		
		return response;
	}

	public static void test(String[] args) throws Exception {

		String url = "http://localhost:8080/awalApp/LoginServlet";
		Map<String, String> params = new HashMap<>();
		params.put("uname", "boudaa");
		params.put("pwd", "tarik");

		CloseableHttpResponse resp = postRequest(url, params);
		System.out.println(resp);
	}

}