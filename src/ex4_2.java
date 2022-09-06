import java.util.Random;

public class ex4_2 implements Runnable{

    static private final int threadPoolSize = 10;

    static private int semaphore = threadPoolSize - 1;
    static private boolean terminate = false;
    static public boolean condNegBefore = false;
    static public boolean condNowNeg = false;
    private static final Object lock = new Object();

    public void run() {
        String threadName = Thread.currentThread().getName();//get the current thread's name
        if (threadName.equals("t1")) {
            this.signal();
        } else {
            this.waitThread();
        }
    }

    public void signal() {
        Random rand = new Random();
        while (!(terminate && semaphore == threadPoolSize - 1)) {
            synchronized (lock) {
                try {
                    while (semaphore == threadPoolSize - 1) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                Thread.sleep(5 * (rand.nextInt(20) + 1));

            } catch (Exception e) {
                System.out.println("Thread interrupted.");
            }

            synchronized (lock) {
                semaphore += 1;
                System.out.println("Released 1 thread, now " + (semaphore + 1) + " threads available.");
                if (semaphore >= 0) {
                    lock.notifyAll();
                }
            }
        }
    }

    public void waitThread() {
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            synchronized (lock) {
                try {
                    while (semaphore < 0) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            try {
                Thread.sleep(5 * (rand.nextInt(20) + 1));

            } catch (Exception e) {
                System.out.println("Thread interrupted.");
            }

            synchronized (lock) {
                semaphore -= 1;
                System.out.println("Consumed 1 thread, now " + (semaphore + 1) + " threads available.");
                if (semaphore < threadPoolSize - 1) {
                    lock.notifyAll();
                }
            }
        }
        terminate = true;
    }


    public static void main(String args[]) {
        Runnable r1 = new ex4_2();
        Thread t1 = new Thread(r1, "t1");
        Runnable r2 =new ex4_2();
        Thread t2 = new Thread(r2, "t2");

        t1.start();
        t2.start();
    }
}
