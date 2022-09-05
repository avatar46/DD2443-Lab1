import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;

public class ex4_1 implements Runnable{

    static private final int buffSize = 100;

//    static public int i = 0;

    // if the producer write is finished
    static public boolean writeFin = false;
    static public int readPtr = 0;
    static public int writePtr = 0;
    private static final Object lock = new Object();

    static private Integer[] buff = new Integer[buffSize];

    public void run() {
        String threadName = Thread.currentThread().getName();//get the current thread's name
        if (threadName.equals("producer")) {
            this.prod();
        } else {
            this.cons();
        }
    }

    public void prod() {

        for (int i = 0; i < 1_000_000; i++) {
            synchronized (lock) {
                try {
                    while ((writePtr % buffSize == (readPtr % buffSize) - 1) || (writePtr % buffSize == 99 && readPtr == 0)) {
                        //System.out.println("111");
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                if (writePtr % buffSize != (readPtr % buffSize) - 1) {
                    System.out.println("       " + i);
                    buff[writePtr] = i;
                    writePtr = (writePtr + 1) % buffSize;
                }
                lock.notifyAll();
            }
        }
        synchronized (lock){
            writeFin = true;
            lock.notifyAll();
        }


    }

    public void cons() {
//        System.out.println(writeFin);
//        System.out.println(readPtr + " " + writePtr);
        while (!(writeFin && (readPtr % buffSize == writePtr % buffSize))) {
            synchronized (lock) {
                try {
                    while (readPtr % buffSize == writePtr % buffSize) {
                        System.out.println(writeFin + " " + readPtr % buffSize + " " + writePtr % buffSize + "222");
                        lock.wait();
                        System.out.println(!(writeFin && (readPtr % buffSize == writePtr % buffSize)));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (readPtr % buffSize != writePtr % buffSize) {
                    System.out.println(buff[readPtr % buffSize]);
                    readPtr = (readPtr + 1) % buffSize;
                }
                lock.notifyAll();
            }
        }
    }


    public static void main(String args[]) {
        Runnable r1 = new ex4_1();
        Thread t1 = new Thread(r1, "producer");
        Runnable r2 =new ex4_1();
        Thread t2 = new Thread(r2, "consumer");

        t1.start();
        t2.start();
    }
}
