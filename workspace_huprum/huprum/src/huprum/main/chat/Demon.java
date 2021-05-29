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
			try
			{
				Thread.sleep(1000);
				System.out.println(++i+"");
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
