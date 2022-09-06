import sun.lwawt.macosx.CSystemTray;

import java.util.Random;

public class ex4_3 implements Runnable{
    static private final int phiNum = 8;
    static private final int rounds = 5;

    static private boolean[] chops = new boolean[phiNum];
    private static final Object lock = new Object();

    private int r = 0;

    public void run() {
        Integer threadName = Integer.valueOf(Thread.currentThread().getName());//get the current thread's name
        for (int i = 0; i < rounds; i++) {
           this.think(threadName);
            this.eat(threadName);
//            System.out.println(i + " for " + threadName);
        }
    }

    public void eat(int threadName) {
        Random rand = new Random();
        System.out.println("Phi num: " + threadName + " wants to begin eating.");
        while (true) {
            while ((chops[threadName % phiNum]) || (chops[(threadName + 1) % phiNum])) {
                // do nothing
            }
            synchronized (lock) {
                if (!((chops[threadName % phiNum]) || (chops[(threadName + 1) % phiNum]))) {
                    chops[threadName % phiNum] = true;
                    chops[(threadName + 1) % phiNum] = true;
                    System.out.println("Phi num: " + threadName + " grabbed chop no. " + threadName % phiNum + " and no. " + (threadName + 1) % phiNum);
                    break;
                }
            }
        }

        //eat
        try {
            Thread.sleep(20 * (rand.nextInt(20) + 1));
        } catch (Exception e) {
            System.out.println("Thread interrupted.");
        }
        chops[threadName % phiNum] = false;
        chops[(threadName + 1) % phiNum] = false;
        r++;
        System.out.println("Phi num: " + threadName + " done" + r + "th eating.");
//        lock.notifyAll();
//        this.think(threadName);
    }

    public void think(int threadName) {
        Random rand = new Random();
        try {
            Thread.sleep(10 * (rand.nextInt(20) + 1));
        } catch (Exception e) {
            System.out.println("Thread interrupted.");
        }
//        this.eat(threadName);
    }

    public static void main(String args[]) {
        Integer i = 0;
        for (i = 0; i < phiNum; i++) {
            Thread tt = new Thread(new ex4_3(), i.toString());
            tt.start();
        }
    }
}
