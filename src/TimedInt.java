/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    31/3/2013
*               Description
*   A class that can hold an integer and allows
*   the controller to time anything from the point
*   of the classes creation.
*/
class TimedInt{
    private long startTime;
    private int number;
    
    public TimedInt(int number){
        this.number = number;
        startTime = System.currentTimeMillis();
    }
    public TimedInt(){
        this(0);
    }
    
    public long stopTimer(){
        return System.currentTimeMillis() - startTime;
    }
    
    public int getInt(){
        return number;
    }
}