/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    27/3/2013
*               Description
*   A class that takes data out of the buffer
*   and performs arbitrary operations on it.
*/

public class Consumer extends Thread{
    private BoundedBuffer buffer;
    private int currentVal;
    
    //Construct private buffer based on value
    //passed in
    public Consumer(BoundedBuffer buffer){
        this.buffer = buffer;
    }
    //Keeps trying to remove an int from the buffer
    public void run(){
        while(true){
        
            try{
                //If remove is successfull sleep for 0-100ms
                currentVal = buffer.remove();
                sleep((int)Math.random()*100);
            }catch(InterruptedException e){
                //Don't handle 
            }
        }
    }
}