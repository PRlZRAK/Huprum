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
	private File               f;
	protected static boolean dwnl;
	private static VideoPlayer p;

	public VideoPlayer(URL url) throws IOException
	{
		/*
		Runnable  r1   = new Runnable()
						{
							@Override
							public void run()
							{
								int time = 0;
								try
								{
									while (dwnl[0])
									{
										System.out.println(time);
										time++;
										Thread.sleep(1000);
									}
								} catch (InterruptedException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
						
		Thread    t1   = new Thread(r1);
		t1.start();*/
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
								f.deleteOnExit();
								dwnl = false;
								p.play();
							} catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					};
		Thread   t = new Thread(r);
		t.start();
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
		p = new VideoPlayer(new URL("http://tuktuk.su/huprum/server/img/long_test"));
		JButton b = new JButton("посмотреть видео");
		c.add(b);
		b.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(dwnl) {
					
              JButton b = (JButton) e.getSource();
              b.setText("Загрузка");
      		Runnable  r1   = new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						while (dwnl)
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
			
Thread    t1   = new Thread(r1);
t1.start();
              
				}
				else p.play();
			}
		});
		t.setVisible(true);
	}
}
