import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ex5 implements Runnable{

    static private Lock lock1 = new ReentrantLock(true);
    static private Lock lock2 = new ReentrantLock(true);


    public void run() {
        String threadName = Thread.currentThread().getName();//get the current thread's name
        if (threadName.equals("t1")) {
            this.sy1();
        } else {
            this.sy2();
        }
    }

    public void sy1() {
        while (true) {
            try {
                lock1.tryLock(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("lock1 acquired, trying to acquire lock2.");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (lock2.tryLock()) {
                System.out.println("lock2 acquired.");
            } else {
                System.out.println("cannot acquire lock2, releasing lock1.");
                lock1.unlock();
                continue;
            }

            System.out.println("executing first operation.");
            break;
        }
        lock2.unlock();
        lock1.unlock();
    }

    public void sy2() {
        while (true) {
            try {
                lock2.tryLock(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("lock2 acquired, trying to acquire lock1.");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (lock1.tryLock()) {
                System.out.println("lock1 acquired.");
            } else {
                System.out.println("cannot acquire lock1, releasing lock2.");
                lock2.unlock();
                continue;
            }
            System.out.println("executing second operation.");
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }



    public static void main(String args[]) {
        Runnable r1 = new ex5();
        Thread t1 = new Thread(r1, "t1");
        Runnable r2 =new ex5();
        Thread t2 = new Thread(r2, "t2");

        t1.start();
        t2.start();
    }
}
