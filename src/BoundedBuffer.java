/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    27/3/2013
*               Description
*   BoundedBuffer is an array of data that should
*   implement mutual exclusion when adding and
*   removing information.
*/

public class BoundedBuffer{
    private class Timer extends Thread{
        private boolean isFinished = false;
        private long time;
        public Timer(){
        
        }
        public void run(){
            try{
                sleep(60000);
            }catch(InterruptedException e){
            
            }
            isFinished = true;
        }
        public boolean isFinished(){
            return isFinished;
        }
    }
    private TimedInt [] buffer;
    private int nextIn = 0, nextOut = 0, size = 0, ins = 1, outs = 1;
    private int numItems = 0;
    private boolean dataAvailable = false, roomAvailable = true;    
    int delta = 0;
    //calculated cumlativly
    private long totalTime = 1;
    private double averageTime = 0;
    Timer timer = new Timer();
    
    BoundedBuffer(int sizeOfBuffer){
        timer.start();
        size = sizeOfBuffer;
        buffer = new TimedInt [size];

    }
    
    BoundedBuffer(){
        this(10);
    }
    
        
    public synchronized void insert(int item) throws InterruptedException{
        if(!roomAvailable)
            wait();
        //Now we know that room is available
        //TODO: Check item for valid input?
        buffer[nextIn] = new TimedInt(item);
        nextIn++;
        numItems++;
        //Note: May have to use Math.abs() here.
        nextIn %= size;
        //System.out.println(nextIn);
        dataAvailable = true; //As we've just put something in.
        ins++;
        if(numItems == size - 1)
            roomAvailable = false;
        notifyAll();
    }
    
    public synchronized int remove() throws InterruptedException{
        if(!dataAvailable)
            wait();
        //int temp = buffer[nextOut];
        outs++;
        totalTime += buffer[nextOut].stopTimer();
        int toReturn = buffer[nextOut].getInt();
        
        nextOut++;
        nextOut %= size;
        
        numItems--;
        roomAvailable = true;//becuase we just removed something
        if(numItems == 0)   //If queue is empty
            dataAvailable = false;
        //return temp;
        notifyAll();
        return toReturn;
    }
    /*
    *   Preconditions: That no other syncronized threads are being run
    *   Postconditions: Data relating to the current state of the program
    *                       Notes   
    *   This method has to be synchronized becuase if it
    *   is not then the data could be changed as we attempt
    *   to retrieve it.
    */
    public synchronized String getData(){
        return "Delta is: " + (ins - outs - numItems) + " Number of items in buffer: " + numItems;
    }
    public boolean timeOut(){
        return timer.isFinished();
    }
    public int averageTime(){
        return (int)(((double)totalTime/(double)outs)*100000);
    
    }
    
    
}