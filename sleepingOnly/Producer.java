/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    27/3/2013
*               Description
*   A class that takes creates a random
*   integer and puts it into the buffer
*/
import java.util.Random;

public class Producer extends Thread{
    private BoundedBuffer buffer;
    private int intval;
    private Random r = new Random();

    Producer(BoundedBuffer buffer){
        this.buffer = buffer;
    }
    
    public void run(){
        while(true){
            try{
                intval = r.nextInt(100); //get value between 0-100
                buffer.insert(intval);	 
                sleep((int)Math.random()*100);
            }
            //do something here?
            catch(InterruptedException e){
                //e.printStackTrace();
                //Don't handle
            }
        }
    }	
}