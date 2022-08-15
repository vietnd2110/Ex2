package Ex2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Producer_Consumer {
    public static void main(String[] args) throws InterruptedException {
        final Run pc = new Run();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static class Run {
        BlockingQueue<Integer> list = new LinkedBlockingQueue<>(5);
        public void produce() throws InterruptedException {
            int value = 0;
            while (true) {
                synchronized (this) {
                    while (list.size() == 5)
                        wait();
                    System.out.println("Tôi đã sản xuất ra: " + value);
                    list.add(value++);
                    notifyAll();
                    Thread.sleep(500);
                }
            }
        }

        public void consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    while (list.size() == 0)
                        wait();
                    int val = list.poll();

                    System.out.println("Tôi đã lấy ra: " + val);
                    notifyAll();
                    Thread.sleep(1000);
                }
            }
        }
    }
}
