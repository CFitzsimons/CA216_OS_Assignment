import java.util.Random;

package yulfy.leed.os;

class Producer extends thread{

    BoundedBuffer buffer;
    Random r = new Random();

    Producer(Boundedbuffer bbuffer)
    {
        buffer = bbuffer;
    }

    void prodInsert(){
    
        try{
            while(true){
                buffer.insert(r); //boundedbuffer method
                sleep(100);		  //randomise?
            }
        }
        catch(InterruptedException e){
            //Changed this here to print the error
            e.printStackTrace();
        }
    }	
}
