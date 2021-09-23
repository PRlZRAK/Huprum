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

	public VideoPlayer(URL url) throws IOException {
		f = File.createTempFile("test", ".3gp");
		Path path = f.toPath();
		try (InputStream in = url.openStream()) {
			Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
		}
		f.deleteOnExit();
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
				Runnable r = new Runnable() {
					@Override
					public void run() {

						VideoPlayer p;
						try {
							p = new VideoPlayer(new URL("http://tuktuk.su/huprum/server/img/test.3gp"));
							p.play();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				};

				Thread t = new Thread(r);
				t.start();

			}

		});
		t.setVisible(true);

	}
}
