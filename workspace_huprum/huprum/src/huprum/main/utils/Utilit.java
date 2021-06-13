package huprum.main.utils;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Utilit
{
	/*
	 * Цвета интерфейса
	 */
	public static String S_COLOR_1085="#E7F0FA";
	public static Color COLOR_1085=Color.decode(S_COLOR_1085);
	public static String S_COLOR_490="#2B2415";
	public static Color COLOR_490=Color.decode(S_COLOR_490);
	public static String S_COLOR_389="#F3B25F";
	public static Color COLOR_389=Color.decode(S_COLOR_389);
	public static String S_COLOR_470="#928348";
	public static Color COLOR_470=Color.decode(S_COLOR_470);
	public static String S_COLOR_1074="#064A5F";
	public static Color COLOR_1074=Color.decode(S_COLOR_1074);
	public static String S_COLOR_399="#C59242";
	public static Color COLOR_399=Color.decode(S_COLOR_399);
	public static String S_COLOR_1057="#00678E";
	public static Color COLOR_1057=Color.decode(S_COLOR_1057);
	public static String S_COLOR_1068="#BACDDA";
	public static Color COLOR_1068=Color.decode(S_COLOR_1068);
	//
	public static int CheckLogin(String login)
	{
		// TODO Auto-generated method stub
		// 0 -mail
		// 1 -phone
		// 2 -login
		Pattern usrNamePtrn = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mtch        = usrNamePtrn.matcher(login);
		if (login.indexOf("@") > -1)
		{
			if (mtch.matches())
				return 0;
		}
		usrNamePtrn = Pattern.compile("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
		mtch = usrNamePtrn.matcher(login);
		if (mtch.matches())
			return 1;
		return 2;
	}

	public static String CleaPhone(String phone)
	{
		String shablon = "0123456789";
		if (phone.charAt(0) == '+' && phone.charAt(1) == '7')
		{
			phone = "8" + phone.substring(2);
		}
		String ret = "";
		for (int i = 0; i < phone.length(); i++)
		{
			if (shablon.contains(phone.charAt(i) + ""))
				ret += phone.charAt(i);
		}
		return ret;
	}

	public static String InsertPerenos(String text, int wide, String znak)
	{
		/*
		 * обработка текста
		 */
		int    j       = wide;
		int    end     = 0;
		int    i       = wide;
		int    lenZnak = znak.length();
		int    lenght  = text.length();
		String doublZnak = znak+znak;
		String otvet   = null;
		if (lenght <= wide)
			return text;
		while(wide<lenght)
		{
			for (i = wide; i > end; i--)
			{
				char ch = text.charAt(i);
				if (ch == ' ')
				{
					System.out.println(i);
					text = new StringBuilder(text).insert(i + 1, znak).toString();
					otvet = text.replaceAll(doublZnak, znak);;
					break;
				}
				if (i == 0)
				{
					System.out.println("i=" + i);
					text = new StringBuilder(text).insert(wide, znak).toString();
					otvet = text.replaceAll(doublZnak, znak);;
				}
			}
			lenght+=lenZnak;
			wide+=j;
		}
		return otvet;
	}
	public static void main(String[] args)
	{
		JFrame jframe = new JFrame("test Utilit");
		jframe.setSize(800, 600);
		jframe.setVisible(true);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		String text  = "На этом сайте можно снять квартиру на сутки. В центре города, хороший ремонт, недорого. Но зта квартира дешевле остальных. Поэтому многие хотят её купить.";
		String otvet = InsertPerenos(text, 10, "<br>");
		System.out.println(otvet);
		JLabel jb = new JLabel("<html>"+otvet);
		jb.setBounds(5, 5, 1000, 300);
		panel.add(jb);
		jframe.getContentPane().add(panel);
	}
}
