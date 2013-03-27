/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    27/3/2013
*               Description
*   A class that looks at what is currently
*   in the buffer and returns the information
*   to standard output.
*/
package yulfy.leed.os;

public class Watcher{

    private BoundedBuffer buffer;
    //Requires the buffer to be passed in.
    Watcher(BoundedBuffer buffer){
        this.buffer = buffer;
    }
    
    //Takes in the buffer and prints it out
    void readBuffer(){
        int [] data = buffer.lookAt();
        //Loop prints the buffer 5 per line
        for(int i = 0; i < buffer.size(); i++){
            System.out.print(data[i] + " ");
            if(i % 5 == 0)
                System.out.println();
        }
    }
    
    //Incase the user wants to pass in a different
    //buffer to check   
    void readBuffer(BoundedBuffer buffer){
        this.buffer = buffer;
        this.readBuffer();
    }
}