package huprum.main.chat;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UserButtomClass extends JButton implements UserButtom
{
	@Override
	public String toString()
	{
		return "UserButtomClass [isSel=" + isSel + ", recId=" + recId + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 0;
	private boolean           isSel;
	private Integer           recId;
	private Color             defaultColor;
	private Color             defaultColorText;
	

	public UserButtomClass(String b)
	{
		super(b);
		defaultColor = getBackground();
		defaultColorText = getForeground();
	}

	@Override
	public void setSelect(boolean select)
	//Background Background, Color Foreground
	{
		isSel = select;
        if (select)
            this.setBackground(Color.gray);
        else
            this.setBackground(defaultColor);
		
	}
	public void setColorButton(Color Background, Color Foreground)
	{
		
			this.setBackground(Background);
		
			this.setForeground(Foreground);
	}
	

	@Override
	public boolean isSelected()
	{
		return isSel;
	}

	@Override
	public void setId(Integer id)
	{
		recId = id;
	}

	@Override
	public Integer getId()
	{
		return recId;
	}

	/**
	 * @param args Метод добавллен для отладки класса
	 */
	public static void main(String[] args)
	{
		JFrame jframe = new JFrame("test UserButtomClass");
		jframe.setSize(800, 600);
		jframe.setVisible(true);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		// стандартная кнопка
		JButton jb = new JButton("enter 1");
		jb.setBounds(5, 5, 100, 30);
		panel.add(jb);
		// кнопка Алексея
		UserButtomClass ubc = new UserButtomClass("enter 2");
		ubc.setBounds(5, 40, 100, 30);
		panel.add(ubc);
		ubc.addActionListener(new ActionListener()		
		{
			boolean flag = true;

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ubc.setSelect(flag);
				ubc.setId(999);
				jframe.repaint();
				flag = !flag;
				System.out.println(ubc.toString());
			}
		});
		jframe.getContentPane().add(panel);
	}

	public Color getDefaultColor()
	{
		return defaultColor;
	}

	public Color getDefaultColorText()
	{
		return defaultColorText;
	}


	
}
