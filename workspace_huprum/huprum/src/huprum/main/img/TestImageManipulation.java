package huprum.main.img;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import huprum.main.utils.Utilit;

public class TestImageManipulation extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestImageManipulation(String string) throws IOException
	{
		super(string);
		setSize(1000, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		add(new JScrollPane(panel));
		ImageManipulation im = new ImageManipulation(this);
		if (im.getImage() != null)
		{
			c.gridx = 0;
			c.gridy = 0;
			panel.add(new JLabel("Характеристики изображения:"), c);
			c.gridx = 1;
			panel.add(new JLabel(im.toString()), c);
			c.gridx = 0;
			c.gridy++;
			panel.add(new JLabel("Оригинальное изображение:"), c);
			c.gridx = 1;
			panel.add(new JLabel(im.getImageIcon(300, 300)), c);
			c.gridx = 0;
			c.gridy++;
			panel.add(new JLabel("<html>Его измененная пропорционально <br>уменьшенная копия:"), c);
			c.gridx = 1;
			panel.add(new JLabel(im.getImageIcon(100, 100)), c);
			c.gridx = 0;
			c.gridy++;
			panel.add(new JLabel("Первые 50 знаков Base64:"), c);
			c.gridx = 1;
			String base64 = im.getBase64();
			panel.add(new JLabel(base64.substring(0, 50)), c);
			ImageManipulation im1 = new ImageManipulation(base64);
			c.gridx = 0;
			c.gridy++;
			panel.add(new JLabel("Изображение из строки:"), c);
			c.gridx = 1;
			JLabel lb1 = new JLabel(im1.getImageIcon(300, 300));
			panel.add(lb1, c);
			c.gridx = 0;
			c.gridy++;
			JButton save = new JButton("сохранить изображение");
			panel.add(save, c);
			save.addActionListener(new SaveActionListener(this, im1));
			c.gridx = 0;
			c.gridy++;
			panel.add(new JLabel("нажмите на третье сверху изображение"), c);
			lb1.addMouseListener(new MyListener(this, im1));
			c.gridx = 0;
			c.gridy++;
			panel.add(new JLabel("Изображение с надписью:"), c);
			c.gridx = 1;
			String text = "Примечание: ниже расположен перевод статьи \"Inline Images with Data URLs\", в которой"
					+ " рассматривается вопрос о внедрении картинки на веб-страницы при помощи data:URI.";
			panel.add(im.getImageTxt(200, 200, text, null, 25, Utilit.COLOR_1085), c);
			c.gridy++;
			c.gridx = 0;
			JButton button = new JButton("test compress img");
			button.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					Component main=null;
					try
					{
						ImageManipulation im2 = new ImageManipulation(main);
						im2.checkMaxSize(200, 100);
						im2.show(null, "");
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			panel.add(button,c);
			JLabel video=new JLabel("<html><iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/wta_7lYLIto\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>");
			c.gridy++;
			panel.add(video,c);
		}
		setVisible(true);
	}

	public class SaveActionListener implements ActionListener
	{
		private TestImageManipulation main;
		private ImageManipulation     im;

		public SaveActionListener(TestImageManipulation testImageManipulation, ImageManipulation im)
		{
			this.main = testImageManipulation;
			this.im = im;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			try
			{
				im.save(main);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class MyListener implements MouseListener
	{
		private ImageManipulation     im;
		private TestImageManipulation main;

		public MyListener(TestImageManipulation testImageManipulation, ImageManipulation im1)
		{
			main = testImageManipulation;
			im = im1;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			try
			{
				im.show(main, "Привет");
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			// im.show(main);
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
		}
	}

	public static void main(String[] args)
	{
		try
		{
			new TestImageManipulation("test");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
