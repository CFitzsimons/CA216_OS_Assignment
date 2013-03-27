/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    27/3/2013
*               Description
*   BoundedBuffer is an array of data that should
*   implement mutual exclusion when adding and
*   removing information.
*/

package yulfy.leed.os;

public class BoundedBuffer{
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