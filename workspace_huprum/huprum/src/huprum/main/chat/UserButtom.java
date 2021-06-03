/**
 * 
 */
package huprum.main.chat;

import java.awt.Color;

/**
 * @author yaa Интерфейс для создания объекта кнопка для юзера. Эта кнопка
 *         должна иметь другой цвет(синий к примеру), если setSelect(true).
 *         Класс имеющий этот интерфейс должен наследоваться от JButton.
 * 
 */
public interface UserButtom
{
	/**
	 * @param select метод устанавливает синий цвет кнопке
	 */
	public void setSelect(boolean select);

	/**
	 * @return узнаем - выделена ли кнопка
	 */
	public boolean isSelected();

	/**
	 * @param id запомнить юзера
	 */
	public void setId(Integer id);

	/**
	 * @return узнать заполненного юзера
	 */
	public Integer getId();

	/**
	 * @return Установить цвет кнопке и её тексту
	 */
	public void setColorButton(Color Background, Color Foreground);
	
}
