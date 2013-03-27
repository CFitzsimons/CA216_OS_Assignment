//////////////////////////////////////////
//                                      //
//  I jammed all the files into here    //
//  for quick and easy compilation      //
//  do not make edits on this file!     //
//                                      //
//////////////////////////////////////////
package yulfy.leed.os;

public class Threading{
    public static void main(String [] args){
        BoundedBuffer bb = new BoundedBuffer(10);
        Consumer c = new Consumer(bb);
        Producer p = new Producer(bb);
        Watcher w = new Watcher(bb);
        
        c.takeInt();
        p.putInt();
        w.readBuffer();
    }

}
class Producer extends Thread{
    BoundedBuffer buffer;
    int intval;

    Producer(BoundedBuffer buffer)
    {
        this.buffer = buffer;
    }
    
    void putInt(){
        while(true){
            try{
                intval = (int) Math.random() * 100;			    //get value between 0-100
                buffer.insert(intval);							//boundedBuffer method
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
class Watcher extends Thread{

    private BoundedBuffer buffer;
    //Requires the buffer to be passed in.
    Watcher(BoundedBuffer buffer){
        this.buffer = buffer;
    }
    
    //Takes in the buffer and prints it out
    void readBuffer(){
        while(true){
            try{
            int [] data = buffer.lookAt();
            //Loop prints the buffer 5 per line
            for(int i = 0; i < buffer.size(); i++){
                System.out.print(data[i] + " ");
                if(i % 5 == 0)
                    System.out.println();
            }
            sleep(1000);
            }catch(InterruptedException e){ }
        }
    }
    
    //Incase the user wants to pass in a different
    //buffer to check   
    void readBuffer(BoundedBuffer buffer){
        this.buffer = buffer;
        this.readBuffer();
    }
}
class Consumer extends Thread{
    private BoundedBuffer buffer;
    private int currentVal;
    
    //Construct private buffer based on value
    //passed in
    public Consumer(BoundedBuffer buffer){
        this.buffer = buffer;
    }
    //Keeps trying to remove an int from the buffer
    public void takeInt(){
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
class BoundedBuffer{
    private int nextIn = 0, nextOut = 0, size = 0;
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
        //--Check if it's empty--//
        if(numItems == 0)
            dataAvailable = false;
        return buffer[nextOut + 1];
    }
}