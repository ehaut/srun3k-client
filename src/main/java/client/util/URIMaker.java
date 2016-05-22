package client.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

public class URIMaker {
	public static URI makeURI(String _path){
        PropertyRW reader = PropertyRW.getInstance();
        String scheme = reader.getProperty(Global.SCHEME);
        String host = reader.getProperty(Global.HOST);
        String path = reader.getProperty(_path);
        int port = 80;

        URI uri;
        try {
            if (_path.equals("loginpath") || _path.equals("logoutpath")) {
                port = Integer.parseInt(reader.getProperty("login_port"));
            }

            uri = new URIBuilder()
                                .setScheme(scheme)
                                .setHost(host)
                                .setPort(port)
                                .setPath(path).build();
        } catch (URISyntaxException e) {
                throw new RuntimeException();
        }
        return uri;
    }
}
