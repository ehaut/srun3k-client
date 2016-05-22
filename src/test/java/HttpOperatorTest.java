import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.utils.URIBuilder;
import client.core.HttpOperator;

public class HttpOperatorTest {

	public static void main(String[] args) throws URISyntaxException {
		URI uri;
		uri = new URIBuilder().setScheme("http").setHost("172.16.154.130")
				.setPath("/cgi-bin/srun_portal").build();
		String userAgent = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64)"
				+ "AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89"
				+ " Safari/537.1 SRun3K Client_W117S733B20140709A-SRun3K.Portal";
		HttpOperator operator = new HttpOperator(uri);
		String entity = "action=login&username=201116040117&password=7cc%3e%3fcc%3c%3dcc%3a"
				+ "&drop=0&pop=1&type=2&ip=1947734188&mbytes=0&minutes=0&ac_id=6"
				+ "&mac=60:eb:69:df:60:a8"
				+ "&key=7E4B015C";
		operator.PushHeader("Accept-Encoding", "identity");
		operator.PushHeader("Content-Type", "application/x-www-form-urlencoded");
		operator.PushHeader("User-Agent", userAgent);
		operator.PushHeader("Accept", "text/plain");
		operator.setEntity(entity);
		System.out.println(operator.post());
	}

}

/* action=logout&username=201116040117&password=111111&drop=0&type=1&n=1
 * action=logout&ac_id=6&username=201116040117&mac=60:eb:69:df:60:a8&type=2
 * 
 * */


