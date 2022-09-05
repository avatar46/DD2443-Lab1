public class ex2 implements Runnable{
    static public int i = 0;

    public void run() {
        this.sy();
    }

    // 2.2
//        public static void sy() {

    // 2.3
    public static synchronized void sy() {
        for (int j = 0; j < 2500; j++) {
            i++;
        }
        System.out.println(i);
    }


    public static void main(String args[]) {
        // 2.1
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Cores: " + cores);

        // 2.2
        Runnable r1 = new ex2();
        Thread t1 = new Thread(r1, "t1");
        Runnable r2 =new ex2();
        Thread t2 = new Thread(r2, "t2");
        Runnable r3 = new ex2();
        Thread t3 = new Thread(r3, "t3");
        Runnable r4 =new ex2();
        Thread t4 = new Thread(r4, "t4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
//        try {
//            t1.join();
//            t2.join();
//            t3.join();
//            t4.join();
//        } catch (Exception e) {
//            System.out.println("Interrupted");
//        }
//
        System.out.println(i);
    }
}


