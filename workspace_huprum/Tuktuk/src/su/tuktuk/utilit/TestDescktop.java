package su.tuktuk.utilit;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class TestDescktop
{
	public static void main(String[] args)
	{
		File f=new File("long_test.3gp");
		try
		{
			Desktop.getDesktop().open(f);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
