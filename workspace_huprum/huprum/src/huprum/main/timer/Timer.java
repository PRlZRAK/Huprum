package huprum.main.timer;
import java.util.Date;
public class Timer
{
	private static long startTime;
	private static String processName;
	public static void start(String str) {
		startTime = new Date().getTime();
		processName=str;
	}
	public static void time() {
		long time = new Date().getTime()-startTime;
		System.out.println("Время выполнения "+processName+": "+time);
	}
}
