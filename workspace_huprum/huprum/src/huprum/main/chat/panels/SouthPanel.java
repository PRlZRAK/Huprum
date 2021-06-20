package huprum.main.chat.panels;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;
import huprum.main.utils.DTime;
import huprum.main.utils.ImageManipulation;

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

	public SouthPanel(Huprum main)
	{
		this.main = main;
		loginer = main.getLoginer();
		myId = Integer.toString(loginer.getId());
		setLayout(new FlowLayout());
		ImageIcon img    = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/paper clip.png")));
		JLabel    imgBut = new JLabel();
		imgBut.setIcon(img);
		imgBut.setToolTipText("Прикрепить изображение");
		imgBut.addMouseListener(new ImgSeachListener());
		add(imgBut);
		show = new JLabel();
		add(show);
		vod = new JTextField(40);
		add(vod);
		JButton enter = new JButton("Отправить");
		enter.addActionListener(new Message());
		add(enter);
	}

	public void setImgNull()
	{
		this.img_send = null;
	}

	/*
	 * отправка сообщения
	 */
	public class Message implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
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

	public class ImgSeachListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			try
			{
				img_send = new ImageManipulation(main);
				show.setIcon(img_send.getImageIcon(20, 20));
				vod.setText(" ");
			} catch (IOException e)
			{
				img_send = null;
				e.printStackTrace();
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
}
