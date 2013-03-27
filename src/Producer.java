import java.util.Random;

package yulfy.leed.os;

class Producer extends Thread{
    BoundedBuffer buffer;
    int intval;

    Producer(BoundedBuffer buffer)
	{
		this.buffer = buffer;
	}

	void putInt()
	{
		while(true){
			try{
				intval = (int) Math.random() * 100;			    //get value between 0-100
				buffer.insert(intval);							//boundedBuffer method
				sleep((int)Math.random()*100);
			}
			//do something here?
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}	
}
