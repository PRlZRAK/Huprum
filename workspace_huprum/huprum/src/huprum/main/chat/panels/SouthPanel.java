package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.img.ImageManipulation;
import huprum.main.loginer.Loginer;
import huprum.main.smile.Smile;
import huprum.main.utils.DTime;
import huprum.main.utils.Utilit;

public class SouthPanel extends JPanel
{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Loginer           loginer;
	private Huprum            main;
	private String            myId;
	private JTextField        vod;
	private ImageManipulation img_send         = null;
	private JLabel            show;
	private Smile             sml;
	private JLabel show_label;

	public SouthPanel(Huprum main)
	{
		this.main = main;
		loginer = main.getLoginer();
		myId = Integer.toString(loginer.getId());
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 5, 1, 5);
		c.gridx = 0;
		c.gridy = 0;
		
		//
		c.gridwidth=3;
		
		sml = new Smile(this);
		add(sml, c);
		c.gridwidth=1;
		//
		c.gridx=3;
		c.gridwidth=1;
		show_label=new JLabel();
		add(show_label, c);
		//
		c.gridy++;
		c.gridx=0;
		JLabel gray_smile = new JLabel("<html>" + Smile.tag("sm1_gray.png"));
		gray_smile.addMouseListener(new GrayListener(sml));
		add(gray_smile, c);
		
		//
		c.gridx++;
		ImageIcon img    = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/paper clip.png")));
		JLabel    imgBut = new JLabel();
		imgBut.setIcon(img);
		imgBut.setToolTipText("Прикрепить изображение");
		imgBut.addMouseListener(new ImgSeachListener());
		add(imgBut, c);
		//
		c.gridx++;
		show = new JLabel();
		add(show, c);
		//
		c.gridx++;
		vod = new JTextField(40);
		vod.addKeyListener(new KeyList());
		add(vod, c);
		//
		c.gridx++;
		JButton enter = new JButton("Отправить");
		enter.addActionListener(new Message());
		add(enter, c);
	}

	public void setImgNull()
	{
		this.img_send = null;
		show.setIcon(null);
	}

	/*
	 * отправка сообщения
	 */
	public class Message implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			sml.setVisible(false);
			show_label.setText("");
			Client              cl   = main.getCl();
			String              msg  = vod.getText();                // .trim();
			Map<String, String> pars = new HashMap<String, String>();
			if (!msg.trim().equals("") || img_send != null)
			{
				pars.put("action", "put_msg");
				pars.put("id_from", myId);
				pars.put("id_to", loginer.getChat().wp.getId());
				pars.put("msg", msg);
				pars.put("dtime", DTime.now());
				if (img_send != null)
				{
					pars.put("img", img_send.getBase64());
					img_send = null;
					show.setIcon(null);
				}
				try
				{
					cl.send(pars);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				vod.setText("");
			}
		}
	}
	public void insertText(String insert_text)
	{
		vod.setText(vod.getText()+insert_text);
		
	}
	public class ImgSeachListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			try
			{
				img_send = new ImageManipulation(main);
				img_send.checkMaxSize(Utilit.IMG_MAX_WIDTH, Utilit.IMG_MAX_HEIGHT);
				if (img_send.getImage() != null)
				{
					show.setIcon(img_send.getImageIcon(40, 40));
					vod.setText(" ");
				} else
				{
					img_send = null;
					JOptionPane.showMessageDialog(main, "Пожалуйста выберите другую картинку",
							"Испорченное изображение", JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e)
			{
				img_send = null;
				JOptionPane.showMessageDialog(main, "Пожалуйста выберите другую картинку",
						"Очень испорченное изображение", JOptionPane.ERROR_MESSAGE);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	public class GrayListener implements MouseListener
	{
		private Smile sml;

		public GrayListener(Smile sml)
		{
			this.sml = sml;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			sml.setVisible(!sml.isVisible());
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}
	public class KeyList implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent arg0)
		{
			String txt = vod.getText();
			txt=Utilit.insertWordWrap(txt, "<br>", 30);
			txt=Smile.replace(txt);
			show_label.setText("<html>"+txt);
		}

		@Override
		public void keyReleased(KeyEvent arg0)
		{
			
		}

		@Override
		public void keyTyped(KeyEvent arg0)
		{
			
		}
	}

}
