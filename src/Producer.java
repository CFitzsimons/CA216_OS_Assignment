/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    31/3/2013
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
        while(!buffer.timeOut()){
            try{
                intval = r.nextInt(100);    //get value between 0-100
                buffer.insert(intval);      //boundedBuffer method
                sleep((int)Math.random()*100);
            }
            catch(InterruptedException e){
                //Don't handle
            }
        }
        System.out.println("Goodbye from " + this.getName() + " A.K.A Producer");
    }	
}