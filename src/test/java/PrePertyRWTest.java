import client.util.PropertyRW;

public class PrePertyRWTest {

	public static void main(String[] args) {
		/*PropertyRW pr = PropertyRW.getInstance();
		String u = pr.getProperty("username");
		String p = pr.getProperty("password");
		String k = pr.getProperty("key");
		System.out.println(u);
		System.out.println(p);
		System.out.println(k);*/
		
		PropertyRW rw = PropertyRW.getInstance("C:/date.properties");
		rw.setProperty("password1", "222222");
	}

}
