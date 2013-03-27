class Producer extends thread
{
  BoundedBuffer buffer;
	Random r = new Random()

	Producer(Boundedbuffer bbuffer)
	{
		buffer = bbuffer;
	}

	void ProdInsert()
	{
		try
		{
			while(true)
			{
				buffer.insert(r); //boundedbuffer method
				sleep(100);		  //randomise?
			}
			//do something here?
		}
		catch(InterruptedException e)
		{
			System.out.println(e);
		}
	}	
}
