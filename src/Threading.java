/*
*   Author:  Dylan Lee & Colin Fitzsimons
*   Date:    31/3/2013
*               Description
*   A class that creates and calls all other
*   classes we are using in the OS project (a.k.a
*   the main class)
*/
public class Threading{
    public static void main(String [] args) throws InterruptedException{
        BoundedBuffer bb;
        //If else checks the argument given for validitiy
        //and 
        if(args.length == 0){
            System.err.println("No arguments supplied, defaulting buffer to size 10");
            bb = new BoundedBuffer();
        }else if(!isValid(args[0])){
            System.err.println("Invalid argument, defaulting buffer to size 10");
            bb = new BoundedBuffer();
        }else
            bb = new BoundedBuffer(Integer.parseInt(args[0]));
            
        Consumer cons = new Consumer(bb);
        Producer prod = new Producer(bb);
        Watcher watch = new Watcher(bb);
        //Start all threads
        cons.start();
        prod.start();
        watch.start();
        
        //Wait for all threads to finish
        cons.join();
        prod.join();
        watch.join();
    }
    //Method will check if the string passed in contains only
    //numbers
    public static boolean isValid(String str){
        for(int i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i)))//TODO: CODE IS NUMERIC
                return false;
        }
        return true;
    }

}