class BoundedBuffer{
    private int nextIn = nextOut = size = 0;
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
        BoundedBuffer(5);
        //Some kind of default size
    }
    
    boolean synchronized insert(int toInsert){
        if(!roomAvailable){
            //Didn't succeed
            return false;
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
        return true;
    }
    
    int synchronized remove() throws InterruptedException{
        if(!dataAvailable)
            throw new InterruptedException;
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