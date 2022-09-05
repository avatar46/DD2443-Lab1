public class ex3_2 implements Runnable{

    static public int i = 0;
    static public boolean cond = false;
    private static final Object lock = new Object();

    public void run() {
        String threadName = Thread.currentThread().getName();//get the current thread's name
        if (threadName.equals("t1")) {
            this.sy1();
        } else {
            this.sy2();
        }
    }

    public void sy1() {
        synchronized (lock) {
            for (int j = 0; j < 1000000; j++) {
                i++;
            }
            cond = true;
            lock.notifyAll();
        }
    }

    public void sy2() {
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
        Runnable r1 = new ex3_2();
        Thread t1 = new Thread(r1, "t1");
        Runnable r2 =new ex3_2();
        Thread t2 = new Thread(r2, "t2");

        t1.start();
        t2.start();
    }
}
