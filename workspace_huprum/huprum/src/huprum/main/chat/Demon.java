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
		int i=0;
		while (true)
		{
			/*
			 * обновление чата раз в секунду
			 */
			try{
				Thread.sleep(1000);
				System.out.println(++i+"");				
				chat.cp.chatRedr();
			} catch (InterruptedException e){e.printStackTrace();}
			/*
			 * обновление левой панели раз в пять секунд
			 */
			if(i==5) {
				i=0;
				chat.wp.redr();
			}
		}
	}
}
