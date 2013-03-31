/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    27/3/2013
*               Description
*   BoundedBuffer is an array of data that should
*   implement mutual exclusion when adding and
*   removing information.
*/
public class BoundedBuffer{

    private int [] buffer;
    private int nextIn = 0, nextOut = 0, size = 0;
    private int numItems = 0;
    private boolean dataAvailable = false, roomAvailable = true;    
    int delta = 0;
    BoundedBuffer(int sizeOfBuffer){
        size = sizeOfBuffer;
        buffer = new int [size];

    }
    
    BoundedBuffer(){
        this(10);
    }
    
        
    public synchronized void insert(int item) throws InterruptedException{
        if(!roomAvailable)
            throw new InterruptedException();
        //Now we know that room is available
        //TODO: Check item for valid input?
        buffer[nextIn] = item;
        nextIn++;
        numItems++;
        //Note: May have to use Math.abs() here.
        nextIn %= size;
        //System.out.println(nextIn);
        dataAvailable = true; //As we've just put something in.
        delta++;
        if(numItems == size - 1)
            roomAvailable = false;
        notifyAll();
    }
    
    public synchronized void remove() throws InterruptedException{
        if(!dataAvailable)
            throw new InterruptedException();
        int temp = buffer[nextOut];
        nextOut++;
        nextOut %= size;
        //System.out.println(nextOut);
        delta--;
        numItems--;
        roomAvailable = true;//becuase we just removed something
        if(numItems == 0)   //If queue is empty
            dataAvailable = false;
        //return temp;
        notifyAll();
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
        return "Delta is: " + (delta - numItems) + " Number of items in buffer: " + numItems;
    }
    /*
    *
    *   This method was used during the debugging stage
    synchronized String getData(){
        return "Delta is: " + getDelta() + " Number of items in buffer: " + numItems + "\n"
            + "Delta var: " + delta + "\n" 
            + "NumItems var: " + numItems + "\n"
            + "DataAvailable var: " + dataAvailable + "\n"
            + "RoomAvailable var: " + roomAvailable + "\n";
    }
    */
    
    
}





/*
public class BoundedBuffer{
    private int nextIn = 0, nextOut = 0, size = 0, ins = 0, outs = 0;
    private boolean dataAvailable = false, roomAvailable = true;
    private int numItems = 0;
    private int [] buffer;
    
    //Start array with length 'size'
    BoundedBuffer(int size){
        this.size = size;
        //TODO: make sure size isn't negative 
        buffer = new int [size];
    }
    //Default buffer at size 5
    BoundedBuffer(){
        this(5);
        //Some kind of default size
    }
    //This method can be used to
    int [] lookAt(){
        return buffer;
    }
    int size(){
        return numItems;
    }
    int getDelta(){
        return ins - outs - numItems;
    }
    //Method allows the insertion of data into the array if
    //and only if the array is able to take more data
    public synchronized void insert(int toInsert) throws InterruptedException{
        if(!roomAvailable){
            //Didn't succeed
            throw new InterruptedException();
        }
        //Room is available
        //TODO: Check toInsert
        buffer[nextIn] = toInsert;
        numItems++;
        nextIn++;
        nextOut++;
        dataAvailable = true;
        ins++;
        //--Check if it's full--//
        if(numItems == size)
            roomAvailable = false;
    }
    //Method takes an element from the array and returns the
    //value to the requester.
    public synchronized int remove() throws InterruptedException{
        if(!dataAvailable)
            throw new InterruptedException();
        //Data can be taken
        nextIn--;
        nextOut--;
        numItems--;
        roomAvailable = true;
        outs--;
        //--Check if it's empty--//
        if(numItems == 0)
            dataAvailable = false;
        if(nextOut+1 >= buffer.length)
            return buffer[nextOut];
        else
            return buffer[nextOut + 1]; 
    }
}
*/