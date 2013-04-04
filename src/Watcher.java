/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    27/3/2013
*               Description
*   A class that looks at what is currently
*   in the buffer and returns the information
*   to standard output.
*/
public class Watcher extends Thread{

    private BoundedBuffer buffer;
    //Requires the buffer to be passed in.
    Watcher(BoundedBuffer buffer){
        this.buffer = buffer;
    }
    
    //Takes in the buffer and prints it out
    public void run(){
        while(!buffer.timeOut()){
            try{
                System.out.println(buffer.getData());
                sleep(1000);
            }catch(InterruptedException e){ 
                //Don't handle
            }
        }
        //Display watcher ending statistics
        System.out.println("Goodbye from " + this.getName() + "A.K.A Watcher");
        System.out.println("Average wait time: " + buffer.averageTime() + "ms");   
    }
}