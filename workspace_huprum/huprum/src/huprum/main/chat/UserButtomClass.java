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
	private boolean isSel;
	private Integer recId;
	public UserButtomClass(String b)
	{
    super(b);
	} 
	@Override
	public void setSelect(boolean select)
	{
		if (select == true)
		{
			isSel = true;
		}
		else 
			isSel = false;
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
		// кнопка Алексея
		
		
		UserButtomClass ubc=new UserButtomClass("enter 2");
		ubc.setBounds(5, 40, 100, 30);
		panel.add(ubc);
		
		jframe.getContentPane().add(panel);
		
	}
}

