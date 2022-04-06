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
		Runnable r = new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								f=st.get(url.toString());								
								if (f == null) {
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
				//http://tuktuk.su/huprum/server/img/long_test 
				//https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1p4HTvApPb2USv26S1f6T_l2lF9LACtA5     powerpoint
				//https://drive.google.com/uc?export=download&confirm=no_antivirus&id=1Ildv2Z_jiAEg5UCEXQeH4KRqH9HRKrbk     word
				JButton b = new JButton();
				b.setEnabled(false);
				b.setText("загрузка");		
				n.add(b,c);
				c.gridy++;
				t.revalidate();
				t.repaint();
				try {
					
				
						
						VideoPlayer p = new VideoPlayer(new URL(vod.getText()),st);
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	
			}
		});
		s.add(enter,BorderLayout.EAST);
		t.setVisible(true);
	}
}
