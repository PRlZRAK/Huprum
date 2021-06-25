package huprum.main.sqlite;

import java.io.IOException;
import java.sql.SQLException;

public class TestSQLite
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ImageStor imSt = new ImageStor();
		//imSt.conn(main);
		imSt.create();
		imSt.add(21312, "fgdg", 2);
		try
		{
			imSt.getImage(0, 0);
		} catch (ClassNotFoundException | SQLException | IOException e)
		{
			e.printStackTrace();
		}
		imSt.close();
	}
}
