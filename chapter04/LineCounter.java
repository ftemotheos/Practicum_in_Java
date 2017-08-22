package chapter04;

import java.io.*;

/**
 * Created by Timofey on 24.03.2016.
 */
public class LineCounter extends Thread {
    LineCounter(String name) {
        super(name);
    }
    @Override
    public void run() {
        String fileName = Thread.currentThread().getName();
        try (LineNumberReader in = new LineNumberReader(new FileReader(fileName))) {
            while (in.readLine() != null) {
                /*System.out.println("My thread: " + fileName);*/
            }
            System.out.println("My threads:\n" + fileName + ":\n" + in.getLineNumber());
            Thread.yield();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void makeConsistent(String arg) {
        try (LineNumberReader in = new LineNumberReader(new FileReader(arg))) {
            while (in.readLine() != null) {
                /*System.out.println("Main thread: " + arg);*/
            }
            System.out.println("Main thread:\n" + arg + ":\n" + in.getLineNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //args = {C:\Windows\DPINST.log, C:\Windows\DirectX.log, C:\Windows\PFRO.log, C:\Windows\DtcInstall.log, C:\Windows\Synaptics.log, C:\Windows\setupact.log, C:\Windows\lsasetup.log, C:\Windows\logs\DirectX.log, C:\Windows\logs\RHDsetup.log, C:\Windows\debug\mrt.log}
        LineCounter[] fileThread = new LineCounter[args.length];
        int counterOfThreads = 0;
        long myThreadsStart, mainThreadStart, myThreadsDuration, mainThreadDuration;
        if (args.length != 0) {
            mainThreadStart = System.currentTimeMillis();
            for (String arg : args) {
                makeConsistent(arg);
            }
            myThreadsStart = System.currentTimeMillis();
            mainThreadDuration = myThreadsStart - mainThreadStart;
            System.out.println("Main thread have worked during " + mainThreadDuration);
            for (String arg : args) {
                fileThread[counterOfThreads] = new LineCounter(arg);
                fileThread[counterOfThreads].start();
                counterOfThreads++;
            }
            try {
                for (; counterOfThreads > 0; ) {
                    fileThread[--counterOfThreads].join();
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            myThreadsDuration = System.currentTimeMillis() - myThreadsStart;
            System.out.println("My threads have worked during " + myThreadsDuration);
        }
    }
}
