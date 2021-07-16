package huprum.main.utils;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;

public class Utilit
{
	/*
	 * Цвета интерфейса
	 */
	public static String S_COLOR_1085 = "#E7F0FA";
	public static Color  COLOR_1085   = Color.decode(S_COLOR_1085);
	public static String S_COLOR_490  = "#2B2415";
	public static Color  COLOR_490    = Color.decode(S_COLOR_490);
	public static String S_COLOR_389  = "#F3B25F";
	public static Color  COLOR_389    = Color.decode(S_COLOR_389);
	public static String S_COLOR_470  = "#928348";
	public static Color  COLOR_470    = Color.decode(S_COLOR_470);
	public static String S_COLOR_1074 = "#064A5F";
	public static Color  COLOR_1074   = Color.decode(S_COLOR_1074);
	public static String S_COLOR_399  = "#C59242";
	public static Color  COLOR_399    = Color.decode(S_COLOR_399);
	public static String S_COLOR_1057 = "#00678E";
	public static Color  COLOR_1057   = Color.decode(S_COLOR_1057);
	public static String S_COLOR_1068 = "#BACDDA";
	public static Color  COLOR_1068   = Color.decode(S_COLOR_1068);
	/*
	 * url сервера
	 */
	//public static String HUPRUM_URL = "http://localhost/huprum/";
	//public static String HUPRUM_URL = "http://localhost/html/huprum/";
	public static String        HUPRUM_URL      = "http://tuktuk.su/huprum/";
	public static String        SERVER_URL      = HUPRUM_URL + "server/index.php";
	public static final String  SMILE_URL       = HUPRUM_URL + "server/smile/";
	public static final String  IMG_URL       = HUPRUM_URL + "server/img/";
	public static String        CONFIG          = "config.json";
	public static String        DBNAME          = "tuktuk.stor";
	public static String        CHAT_IMAGES     = "chatimages.json";
	private static String       PHON_PATERN     = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
	private static final String MAIL_PATERN     = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final int     IMG_MAX_WIDTH   = 1000;
	public static final int     IMG_MAX_HEIGHT  = 800;
	public static boolean       SET_CRY_SOUND   = true;
	public static boolean       SET_STUK_SOUND  = true;
	public static boolean       SET_SEND_SOUND  = true;
	public static boolean       SET_DYATEL_SHOW = true;


	public static int CheckLogin(String login)
	{
		// TODO Auto-generated method stub
		// 0 -mail
		// 1 -phone
		// 2 -login
		Pattern usrNamePtrn = Pattern.compile(MAIL_PATERN);
		Matcher mtch        = usrNamePtrn.matcher(login);
		if (login.indexOf("@") > -1)
		{
			if (mtch.matches())
				return 0;
		}
		usrNamePtrn = Pattern.compile(PHON_PATERN);
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

	public static boolean isPhone(String phone)
	{
		Pattern usrNamePtrn = Pattern.compile(PHON_PATERN);
		Matcher mtch        = usrNamePtrn.matcher(phone);
		return mtch.matches();
	}

	public static boolean isMail(String mail)
	{
		Pattern usrNamePtrn = Pattern.compile(MAIL_PATERN);
		Matcher mtch        = usrNamePtrn.matcher(mail);
		return mtch.matches();
	}

	public static String insertWordWrap(String src, int len_str, String separator)
	{
		String rez = "";
		String row = src;
		while (row.length() > len_str)
		{
			int i = row.lastIndexOf(" ", len_str);
			if (i <= 0)
				i = len_str;
			else
				i++;
			rez += row.substring(0, i) + separator;
			row = row.substring(i);
		}
		return rez + row;
	}

	@SuppressWarnings("static-access")
	public static JLabel getCopyright(Lang lang)
	{
		return new JLabel(lang.put("<html><br> &nbsp;&nbsp;&nbsp;&nbsp; Copyright © 2021. Limited Liability "
				+ "Company \"TukTukSoft\". All rights reserved.<br> &nbsp;"
				+ "#<html><br> &nbsp;&nbsp;&nbsp;&nbsp; Copyright © 2021. "
				+ "Общество с ограниченной ответственностью «ТуктукСофт». Все права защищены.<br> &nbsp;"));
	}

	public static JLabel getCopyright(String lang)
	{
		return getCopyright(new Lang(lang));
	}
}
