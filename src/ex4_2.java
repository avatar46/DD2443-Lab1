public class ex4_2 implements Runnable{

    static private final int threadPoolSize = 10;

    static public int semaphore = 0;
    static private boolean
    static public boolean condNegBefore = false;
    static public boolean condNowNeg = false;
    private static final Object lock = new Object();

    public void run() {
        String threadName = Thread.currentThread().getName();//get the current thread's name
        if (threadName.equals("t1")) {
            this.signal();
        } else {
            this.wait();
        }
    }

    public void signal() {
        synchronized (lock) {
            while (!condNowNeg) {

            }
            lock.notifyAll();
        }
    }

    public void wait() {
        synchronized (lock) {
            try {
                while (!cond) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(i);
        }
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
