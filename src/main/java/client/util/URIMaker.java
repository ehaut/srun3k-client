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
        URI uri;
        try {
                uri = new URIBuilder()
                                .setScheme(scheme)
                                .setHost(host)
                                .setPath(path).build();
        } catch (URISyntaxException e) {
                throw new RuntimeException();
        }
        return uri;
    }
}
