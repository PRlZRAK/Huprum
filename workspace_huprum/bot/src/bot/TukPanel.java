package bot;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TukPanel extends JPanel
{
	public static void main(String[] args)
	{
		JFrame jf = new JFrame("test");
		jf.setSize(600, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TukPanel panel = new TukPanel();
		jf.add(panel);
		jf.setVisible(true);
	}
}
