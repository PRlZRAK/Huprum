package huprum.main.chat;

import huprum.main.timer.Timer;

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
				Timer.time("demon start wp redr");
					chat.wp.redr();
					Timer.time("demon start cp redr");
					Timer.time("demon end");
				chat.cp.chatRedr();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}		
		}
	}
}
