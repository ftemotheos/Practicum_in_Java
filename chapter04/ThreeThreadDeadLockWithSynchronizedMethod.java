package chapter04;

/**
 * Created by Timofey on 06.04.2016.
 */
public class ThreeThreadDeadLockWithSynchronizedMethod {
    public static class SynchronizedClass1 {
        final private static Object resource = "resource1";
        synchronized public static void GrabberMethod() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + ": grabbed " + resource);
            Thread.sleep(50);
            SynchronizedClass2.GrabberMethod();
            System.out.println(Thread.currentThread().getName() + ": released " + resource);
        }
    }
    public static class SynchronizedClass2 {
        final private static Object resource = "resource2";
        synchronized public static void GrabberMethod() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + ": grabbed " + resource);
            Thread.sleep(50);
            SynchronizedClass3.GrabberMethod();
            System.out.println(Thread.currentThread().getName() + ": released " + resource);
        }
    }
    public static class SynchronizedClass3 {
        final private static Object resource = "resource3";
        synchronized public static void GrabberMethod() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + ": grabbed " + resource);
            Thread.sleep(50);
            SynchronizedClass1.GrabberMethod();
            System.out.println(Thread.currentThread().getName() + ": released " + resource);
        }
    }
    public static void main(String[] args) {
        new Thread("thread1") {
            public void run() {
                try {
                    SynchronizedClass1.GrabberMethod();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread("thread2") {
            public void run() {
                try {
                    SynchronizedClass2.GrabberMethod();

                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread("thread3") {
            public void run() {
                try {
                    SynchronizedClass3.GrabberMethod();

                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
