/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    31/3/2013
*               Description
*   BoundedBuffer is an array of data that should
*   implement mutual exclusion when adding and
*   removing information.  Implemented similar to a
*   queue.
*/

public class BoundedBuffer{
    //This class only exists to keep a running timer
    //so the other threads know when to stop could be
    //altered easily to be used in a multitude of situations
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
    private int nextIn = 0, nextOut = 0, size = 0, ins = 1, 
        outs = 1, numItems = 0;
    private long totalTime = 0;
    private boolean dataAvailable = false, roomAvailable = true;    
    private Timer timer = new Timer();
    
    
    BoundedBuffer(int sizeOfBuffer){
        timer.start();
        size = sizeOfBuffer;
        buffer = new TimedInt [size];
    }
    //Default constructor, makes a queue
    //with size 10
    BoundedBuffer(){
        this(10);
    }
    
    /*
    *   Preconditions: That no other syncronized threads are being run
    *   Postconditions: Places an integer onto the queue and when sucessfull
    *       updates ins, nextIn and data/roomAvailable
    */
    public synchronized void insert(int item) throws InterruptedException{
        if(!roomAvailable)
            wait();
        //Now we know that room is available
        buffer[nextIn] = new TimedInt(item);
        nextIn++;           //Update nextIn
        nextIn %= size;     //Wrap around if greating then the size
        numItems++;
        ins++;
        dataAvailable = true; //As we've just put something in.

        if(numItems == size - 1)
            roomAvailable = false;
        notifyAll();
    }
    /*
    *   Preconditions: That no other syncronized threads are being run
    *   Postconditions: Returns the integer at the 'bottom of the stack' and
    *       upon completion has updated totalTime, nextOut, outs and room/dataAvailable
    */
    public synchronized int remove() throws InterruptedException{
        if(!dataAvailable)
            wait();
        //int temp = buffer[nextOut];
        
        totalTime += buffer[nextOut].stopTimer();
        int toReturn = buffer[nextOut].getInt(); 
        nextOut++;
        nextOut %= size;    //Wrap around if less then the size
        numItems--;
        outs++;
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
    
    /*
    *           Note
    *   This method allows other classes calling this 
    *   method to see a unified timer.
    */
    public boolean timeOut(){
        return timer.isFinished();
    }
    
    /*
    *       Note
    *   Allows for the calculation and return of the
    *   current average time between an item being placed
    *   onto the queue and it being taken off.
    */
    public int averageTime(){
        return (int)(((double)totalTime/(double)outs)*100000);
    
    }
    
    
}