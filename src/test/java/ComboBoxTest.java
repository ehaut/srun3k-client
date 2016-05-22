import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import client.exception.GetIPFailException;
import client.exception.GetMACFailException;
import client.util.InterInfoGetter;

public class ComboBoxTest {

	private JFrame frame;
	private JComboBox<NetworkInterface> combo;
	private JButton button;
	
	public ComboBoxTest() throws SocketException {
		Vector<NetworkInterface> inters =  InterInfoGetter.getInterfaces();
		
		frame = new JFrame("测试");
		combo = new JComboBox<NetworkInterface>(inters);
		button = new JButton("获取");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NetworkInterface inter = (NetworkInterface)combo.getSelectedItem();
				try {
					String mac = InterInfoGetter.getLocalMac(inter);
					System.out.println(mac);
					System.out.println(InterInfoGetter.getLocalIP(inter));
				} catch (GetMACFailException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GetIPFailException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Container c = frame.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(combo);
		c.add(button);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 170);
		frame.setVisible(true);
		
		
		
	}
	
	public static void main(String[] args) throws SocketException {
		new ComboBoxTest();

	}

}
