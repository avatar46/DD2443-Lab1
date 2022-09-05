public class ex3_1 implements Runnable{
        static public int i = 0;

        public void run() {
            String threadName = Thread.currentThread().getName();//get the current thread's name
            if (threadName.equals("t1")) {
                this.sy1();
            } else {
                this.sy2();
            }
        }

        public void sy1() {
                for (int j = 0; j < 1000000; j++) {
                    i++;
                }
        }

        public void sy2() {
                System.out.println(i);
        }


        public static void main(String args[]) {
            Runnable r1 = new ex3_1();
            Thread t1 = new Thread(r1, "t1");
            Runnable r2 =new ex3_1();
            Thread t2 = new Thread(r2, "t2");

            t1.start();
            t2.start();
        }
}
