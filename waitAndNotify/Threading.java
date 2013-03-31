//////////////////////////////////////////
//                                      //
//  I jammed all the files into here    //
//  for quick and easy compilation      //
//  do not make edits on this file!     //
//                                      //
//////////////////////////////////////////
public class Threading{
    public static void main(String [] args){
        BoundedBuffer bb = new BoundedBuffer(10);
        Consumer c = new Consumer(bb);
        Producer p = new Producer(bb);
        Watcher w = new Watcher(bb);
        
        c.start();
        p.start();
        w.start();
    }

}