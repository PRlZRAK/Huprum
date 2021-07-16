package huprum.main.timer;

import java.util.Date;

public class Timer
{
	private static long startTime;

	public static void start(String str)
	{
		startTime = new Date().getTime();
		time(str);
	}

	public static void time(String label)
	{
		double time = ((double) new Date().getTime() - startTime) / 1000;
		System.out.println(label + ": " + time);
	}
}
