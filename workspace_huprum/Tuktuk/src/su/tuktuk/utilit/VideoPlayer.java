package su.tuktuk.utilit;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VideoPlayer {
	private File f;
	private static VideoPlayer p;

	public VideoPlayer(URL url) throws IOException {
		boolean[] dwnl = { true };
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				int time = 0;
				try {
					while (dwnl[0]) {

						System.out.println(time);
						time++;
						Thread.sleep(1000);

					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		Thread t1 = new Thread(r1);
		t1.start();

		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					String s = url.getFile();
					String ext;
					if (s.contains("."))
						ext = s.substring(s.lastIndexOf("."));
					else
						ext = "";
					f = File.createTempFile("test", ext);
					Path path = f.toPath();
					try (InputStream in = url.openStream()) {
						Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
					}
					f.deleteOnExit();
					dwnl[0] = false;
					p.play();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};

		Thread t = new Thread(r);
		t.start();

	}

	private void play() {
		try {
			Desktop.getDesktop().open(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		JFrame t = new JFrame();
		t.setSize(500, 500);
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = t.getContentPane();
		c.setLayout(new FlowLayout());
		JButton b = new JButton("посмотреть видео");
		c.add(b);
		b.addActionListener(new ActionListener() {

			

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					p = new VideoPlayer(new URL("http://tuktuk.su/huprum/server/img/long_test.3gp"));
					e.getSource();					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});
		t.setVisible(true);

	}
}
