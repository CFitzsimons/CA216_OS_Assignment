public class BoundedBuffer{

    private int size;
    private int [] buffer;
    private int nextIn = 0, nextOut = 0, size = 0, ins = 0, outs = 0;
    private int numItems = 0;
    
    BoundedBuffer(int sizeOfBuffer){
        size = sizeOfBuffer;
        buffer = new int [size];
        private boolean dataAvailable = false, roomAvailable = true;
    }
    
    BoundedBuffer(){
        this(10);
    }
    
        
    public synchronized void insertItem(int item) throws InterruptedException{
        if(!roomAvailable)
            throw new InterruptedException();
        //Now we know that room is available
        //TODO: Check item for valid input?
        buffer[nextIn] = item;
        nextIn++;
        numItems++;
        //Note: May have to use Math.abs() here.
        nextIn %= size;
        dataAvailable = true; //As we've just put something in.
        ins++;
        if(numItems == size + 1)
            roomAvailable = false;
    
    }
    
    public synchronized int removeItem() throws InterruptedException{
        if(!dataAvailable)
            throw new InterruptedException();
        int temp = buffer[nextOut];
        numItems++;
        nextOut++;
        nextOut %= size;
        outs++;
        roomAvailable = true;//becuase we just removed something
        if(numItems == 0)   //If queue is empty
            dataAvailable = false;
        return temp;
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
    
}