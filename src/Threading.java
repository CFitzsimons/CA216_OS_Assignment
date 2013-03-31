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
        BoundedBuffer bb = new BoundedBuffer(10);
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

}