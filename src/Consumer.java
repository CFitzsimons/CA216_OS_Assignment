/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    27/3/2013
*               Description
*   BoundedBuffer is an array of data that should
*   implement mutual exclusion when adding and
*   removing information.
*/

class Consumer extend Thread{
    private BounderBuffer buffer;
    private int currentVal;
    
    //Construct private buffer based on value
    //passed in
    public Consumer(BounderBuffer buffer){
        this.buffer = buffer;
    }
    //Keeps trying to remove an int from the buffer
    public void takeInt(){
        while(true){
        
            try{
                //If remove is successfull sleep for 0-100ms
                currentVal = buffer.remove();
                sleep(Math.random()*100);
            }catch(InterruptedException e){
                //Don't handle 
            }
        }
    }
}