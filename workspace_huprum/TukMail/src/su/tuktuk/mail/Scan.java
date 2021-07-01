package su.tuktuk.mail;

import java.util.Scanner;

public class Scan implements Runnable
{
	private Scanner sc;        // = new Scanner(p.getInputStream());;
	private String  otvet = "";

	public Scan(TukMail main)
	{
		sc = new Scanner(main.p.getInputStream());;
	}

	@Override
	public void run()
	{
		otvet = "";
		String r    = "";
		String line = "";
		while (sc.hasNextLine())
		{
			line = sc.nextLine();
			otvet += r + line;
			r = "\n";
		}
		//otvet += r + line;
	}

	public String getOut()
	{
		return otvet;
	}

	public void clearOut()
	{
		otvet = "";
	}
}