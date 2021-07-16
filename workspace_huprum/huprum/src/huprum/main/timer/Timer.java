package huprum.main.timer;
import java.util.Date;
public class Timer
{
	private static long startTime;
	private static String processName;
	private static Date date;
	public static void start(String str) {
		startTime = date.getTime();
		processName=str;
	}
	public static void time() {
		long time = date.getTime()-startTime;
		System.out.println("Время выполнения "+processName+": "+time);
	}
}
