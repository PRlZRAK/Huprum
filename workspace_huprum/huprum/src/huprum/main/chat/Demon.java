package huprum.main.chat;

public class Demon implements Runnable
{
	private Chat chat;

	public Demon(Chat chat)
	{
		this.chat = chat;
	}

	@Override
	public void run()
	{
		
		while (true)
		{
			/*
			 * обновление чата раз в секунду
			 */
			try
			{
				Thread.sleep(1000);
				
					chat.wp.redr();
				chat.cp.chatRedr();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			/*
			 * обновление левой панели раз в пять секунд
			 */
		
		}
	}
}
