package huprum.main.utils;

/**
 * @author yaa интерфейс для класс вычисляющего новые размеры изображения
 */
public interface ResProp
{
	/*
	 * конструктор new ResPropClass(int ширина_экрана, int высота_экрана)
	 */
	/**
	 * @param x ширина изображения
	 * @param y высота изображения
	 * @return новая ширина изображения
	 */
	public int sizeX(int x, int y);

	/**
	 * @param x ширина изображения
	 * @param y высота изображения
	 * @return новая высота изображения
	 */
	public int sizeY(int x, int y);

	/**
	 * @param x ширина изображения
	 * @param y высота изображения
	 * @return true если картинка влезает в экран
	 */
	public boolean isInside(int x, int y);
}
