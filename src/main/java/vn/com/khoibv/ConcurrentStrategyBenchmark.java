package vn.com.khoibv;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import sun.misc.Unsafe;

public class ConcurrentStrategyBenchmark {

  private static final long MAX = 1_000_000L;
  private final AtomicLong sharedCounterAtomic = new AtomicLong(0L);
  private long sharedCounter = 0L;

  public void run() {
    final int THREAD_COUNT = 1000;

    try {
//      doSingleThread();

//      doMultiThreadWithLock();

//      doMultiThreadWithAtomic();

//      doMultiThreadWithReadBarrier();

      doNThreadWithLock(THREAD_COUNT);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 1 thread
   */
  private void doSingleThread() throws InterruptedException {
    doAction(1, MAX, () -> sharedCounter++);

    System.out.println("counter=" + sharedCounter);
  }

  /**
   * 2 threads, without lock
   */
  private void doMultiThreadWithoutLock() throws InterruptedException {

    doAction(2, MAX / 2, () -> sharedCounter++);

    System.out.println("counter=" + sharedCounter);
  }


  /**
   * 2 threads with lock
   */
  private void doMultiThreadWithLock() throws InterruptedException {
    doAction(2, MAX / 2, () -> {
      synchronized (this) {
        sharedCounter++;
      }
    });

    System.out.println("counter=" + sharedCounter);
  }

  /**
   * 100 threads with lock
   */
  private void doNThreadWithLock(final int threadCount) throws InterruptedException {
    doAction(threadCount, MAX / threadCount, () -> {
      synchronized (this) {
        sharedCounter++;
      }
    });

    System.out.println("counter=" + sharedCounter);
  }


  /**
   * 2 threads using Atomic
   */
  private void doMultiThreadWithAtomic() throws InterruptedException {
    doAction(2, MAX / 2, sharedCounterAtomic::incrementAndGet);

    System.out.println("counter=" + sharedCounterAtomic);
  }

  /**
   * 2 threads with unsafe barrier
   */
  private void doMultiThreadWithReadBarrier() throws InterruptedException {

    doAction(2, MAX / 2, () -> {
      Unsafe.getUnsafe().loadFence(); // Read barrier
      sharedCounter++;
      Unsafe.getUnsafe().storeFence(); // Store barrier
    });

    System.out.println("counter=" + sharedCounter);
  }

  /**
   * Do action with N threads
   */
  private void doAction(final int threadCount, final long loopCount, Runnable action)
      throws InterruptedException {

    List<Thread> threadList = new ArrayList<>();
    for (int i = 0; i < threadCount; i++) {
      threadList.add(new Thread(() -> {
        for (long j = 0; j < loopCount; j++) {
          action.run();
        }
      }));
    }

    // Start threads
    threadList.forEach(Thread::start);

    // Wait threads
    for (Thread t : threadList) {
      t.join();
    }
  }

}
