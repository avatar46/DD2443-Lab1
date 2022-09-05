public class ex1 extends Thread {

    public void run() {
        System.out.println("Hello World! Thread ID: " + Thread.currentThread().getId());
    }

    public static void main(String args[]) {
        ex1 t1 = new ex1();
        ex1 t2 = new ex1();
        ex1 t3 = new ex1();
        ex1 t4 = new ex1();
        ex1 t5 = new ex1();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

    }
}