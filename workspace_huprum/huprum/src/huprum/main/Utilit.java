package huprum.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilit implements UtilitInt
{
	public static void main(String[] args)
	{
		Utilit ut  = new Utilit();
		int    ret = ut.ChecLogin("+7(928)661-35-22");
		System.out.println(ret);
		System.out.println(ut.ClearPhone("+7(928)661-35-22"));
	}

	@Override
	public int ChecLogin(String login)
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

	@Override
	public String ClearPhone(String phone)
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
}
