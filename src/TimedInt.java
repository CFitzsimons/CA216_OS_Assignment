
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