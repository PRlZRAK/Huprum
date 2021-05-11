package huprum.main.chat;

import java.awt.Button;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UserButtomClass extends JButton implements UserButtom
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0;
	private boolean           isSel;
	public UserButtomClass(String string)
	{
    	
	} 
	@Override
	public void setSelect(boolean select)
	{
		Button b = new Button();
		if (select == true)
		{
			b.setBackground(new Color(255, 204, 0));
			isSel = true;
		}
	}

	@Override
	public boolean isSelected()
	{
		if (isSel = true)
			return true;
		else
			return false;
	}

	@Override
	public void setId(Integer id)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Integer getId()
	{
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @param args
	 * Метод добавллен для отладки класса
	 */
	public static void main(String[] args) {
		JFrame jframe = new JFrame("test UserButtomClass");
		jframe.setSize(800, 600);
		jframe.setVisible(true);
		JPanel panel = new JPanel();
        panel.setLayout(null);
        // стандартная кнопка
		JButton jb = new JButton("enter 1");
		jb.setBounds(5, 5, 100, 30);
		panel.add(jb);
		// кнопка Алексея раскоментировать и отладить
		
		UserButtomClass ubc=new UserButtomClass("enter 2");
		ubc.setBounds(5, 40, 100, 30);
		panel.add(ubc);
		
		jframe.getContentPane().add(panel);
		
	}
}

