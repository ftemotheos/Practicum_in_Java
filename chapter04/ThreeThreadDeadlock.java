package chapter04;

/**
 * Created by Timofey on 04.04.2016.
 */
public class ThreeThreadDeadlock {
    public static void main(String[] args) {
        final Object resource1 = "resource1", resource2 = "resource2", resource3 = "resource3";
        new Thread(new Runnable() {
            public void run() {
                try {
                    synchronized (resource1) {
                        System.out.println(Thread.currentThread().getName() + ": grabbed " + resource1);
                        Thread.sleep(50);
                        synchronized (resource2) {
                            System.out.println(Thread.currentThread().getName() + ": grabbed " + resource2);
                            Thread.sleep(50);
                            synchronized (resource3) {
                                System.out.println(Thread.currentThread().getName() + ": grabbed " + resource3);
                                Thread.sleep(50);
                                System.out.println(Thread.currentThread().getName() + ": released " + resource3);
                            }
                            System.out.println(Thread.currentThread().getName() + ": released " + resource2);
                        }
                        System.out.println(Thread.currentThread().getName() + ": released " + resource1);
                    }
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1").start();
        new Thread(new Runnable() {
            public void run() {
                try {
                    synchronized (resource2) {
                        System.out.println(Thread.currentThread().getName() + ": grabbed " + resource2);
                        Thread.sleep(50);
                        synchronized (resource3) {
                            System.out.println(Thread.currentThread().getName() + ": grabbed " + resource3);
                            Thread.sleep(50);
                            synchronized (resource1) {
                                System.out.println(Thread.currentThread().getName() + ": grabbed " + resource1);
                                Thread.sleep(50);
                                System.out.println(Thread.currentThread().getName() + ": released " + resource1);
                            }
                            System.out.println(Thread.currentThread().getName() + ": released " + resource3);
                        }
                        System.out.println(Thread.currentThread().getName() + ": released " + resource2);
                    }
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread2").start();
        new Thread(new Runnable() {
            public void run() {
                try {
                    synchronized (resource3) {
                        System.out.println(Thread.currentThread().getName() + ": grabbed " + resource3);
                        Thread.sleep(50);
                        synchronized (resource1) {
                            System.out.println(Thread.currentThread().getName() + ": grabbed " + resource1);
                            Thread.sleep(50);
                            synchronized (resource2) {
                                System.out.println(Thread.currentThread().getName() + ": grabbed " + resource2);
                                Thread.sleep(50);
                                System.out.println(Thread.currentThread().getName() + ": released " + resource2);
                            }
                            System.out.println(Thread.currentThread().getName() + ": released " + resource1);
                        }
                        System.out.println(Thread.currentThread().getName() + ": released " + resource3);
                    }
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread3").start();
    }
}
