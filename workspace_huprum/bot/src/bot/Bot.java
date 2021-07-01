package bot;

import java.util.Scanner;

public class Bot {

	private static Scanner sc;

	public static void main(String[] args) {

		sc = new Scanner(System.in);
		System.out.println("Я бот");
		System.out.println("Скажите я вам нравлюсь? yes/no");

		String otvet = sc.nextLine();

		System.out.println("Спасибо! Вы ввели  " + otvet);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Поспал 10 секунды");
		System.out.println("Введите куку");
		otvet = sc.nextLine();
		System.out.println("Спасибо! Вы ввели  " + otvet);
	}

}
