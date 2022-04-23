package su.tuktuk.utilit;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VideoPlayer
{
	private File    f;
	private boolean dwnl=true;

	public VideoPlayer(URL url, MStor st) throws IOException
	{
		if (st.has(url.toString()))
				
		{
			Runnable r = new Runnable()
			{
				@Override
				public void run()
				{
                 while(st.get1(url.toString())==null)
					try
					{
						Thread.sleep(1000);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
                 f = st.get1(url.toString());
                 f.deleteOnExit();
				 dwnl = false;
				}
			};
                    Thread   t = new Thread(r);
                    t.start();
		}
		else {
			st.add(url.toString(), null);
		
		Runnable r = new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								f=st.get1(url.toString());								
								if (f == null) {
									String s = url.getFile();
									String ext;
									URLConnection urlConnection =url.openConnection();
									String mimeType = urlConnection.getContentType(); 
                                     
									if (s.contains("."))
										ext = s.substring(s.lastIndexOf("."));
									else
										if (mimeType==null)
										ext = "";
										else
										ext ="."+mimeType.substring(mimeType.lastIndexOf("/")+1);
									f = File.createTempFile("test", ext);
									Path path = f.toPath();
									try (InputStream in = url.openStream())
									{
										Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
									}
									System.out.println("загрузилось");
									st.add(url.toString(), f);									
								}
								
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
		t.setLayout(new BorderLayout());
		JPanel s = new JPanel();
		JPanel n = new JPanel();
		n.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		s.setLayout(new BorderLayout());
		JTextField vod = new JTextField(30);
		JButton enter = new JButton("скачать");
		s.add(vod, BorderLayout.WEST);
		t.add(s,BorderLayout.SOUTH);
		t.add(n,BorderLayout.CENTER);
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	    MStor st = new MStor();				
		enter.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//http://tuktuk.su/huprum/server/img/long_test   или   http://TUKTUK.SU/huprum/server/img/long_test
				//https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1p4HTvApPb2USv26S1f6T_l2lF9LACtA5     powerpoint
				//https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1Ildv2Z_jiAEg5UCEXQeH4KRqH9HRKrbk     word
				//https://thumb.cloud.mail.ru/weblink/thumb/xw1/v3vw/2cwVWoQKn
				//https://thumb.cloud.mail.ru/thumb/xw1/sambo/IMG_4588%5B1%5D.JPG
				//vod.setText("");
				JButton b = new JButton();
				try {
				VideoPlayer p = new VideoPlayer(new URL(vod.getText()),st);				
				b.setEnabled(false);
				b.setText("загрузка");		
				n.add(b,c);
				c.gridy++;
				t.revalidate();
				t.repaint();
				
					
				
						
						
						b.addActionListener(new ActionListener() 
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
							p.play();	
							}
							
						});
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
												b.setText("открыть");
												b.setEnabled(true);
											} catch (InterruptedException e)
											{
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									};
						Thread   t1 = new Thread(r1);
						t1.start();				
				} catch (IOException e1) {
					t.remove(b);
					e1.printStackTrace();
				}

	
			}
		});
		s.add(enter,BorderLayout.EAST);
		t.setVisible(true);
	}
}
