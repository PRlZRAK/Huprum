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

public class VideoPlayer
{
	private File    f;
	private boolean dwnl=true;
	// private VideoPlayer p;

	public VideoPlayer(URL url) throws IOException
	{
		Runnable r = new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								String s = url.getFile();
								String ext;
								if (s.contains("."))
									ext = s.substring(s.lastIndexOf("."));
								else
									ext = "";
								f = File.createTempFile("test", ext);
								Path path = f.toPath();
								try (InputStream in = url.openStream())
								{
									Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
								}
								System.out.println("загрузилось");
								f.deleteOnExit();
								dwnl = false;
								
							} catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					};
		Thread   t = new Thread(r);
		t.start();
	}

	protected boolean isLoading()
	{
		return dwnl;
	}

	private void play()
	{
		try
		{
			Desktop.getDesktop().open(f);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException
	{
		JFrame t = new JFrame();
		t.setSize(500, 500);
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = t.getContentPane();
		c.setLayout(new FlowLayout());
		Stor st =null;
		VideoPlayer p = new VideoPlayer(new URL("http://tuktuk.su/huprum/server/img/long_test"),st);
		JButton     b = new JButton("посмотреть видео");
		c.add(b);
		b.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (p.isLoading())
				{
					JButton b = (JButton) e.getSource();
					b.setText("Загрузка");
					Runnable r1 = new Runnable()
								{
									@Override
									public void run()
									{
										try
										{
											while (p.isLoading())
											{
												Thread.sleep(1000);
											}
											b.setText("посмотреть видео");
										} catch (InterruptedException e)
										{
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								};
					Thread   t1 = new Thread(r1);
					t1.start();
				} else
					p.play();
			}
		});
		t.setVisible(true);
	}
}
