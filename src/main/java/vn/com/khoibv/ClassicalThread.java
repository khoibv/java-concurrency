package vn.com.khoibv;

public class ClassicalThread {

  public void run() throws InterruptedException {
    final long THREAD_COUNT = 20_000; // warning: crash when try 100K threads
    for (long i = 0L; i < THREAD_COUNT; i++) {
      var thread = new CustomThread(i);
      thread.join();
      thread.start();
    }
  }

  static class CustomThread extends Thread {

    private long index = -1L;

    CustomThread(long index) {
      this.index = index;
    }

    @Override
    public void run() {
      System.out.println(String.format("Classical thread #%s. Total thread: %s", this.index, Thread.activeCount()));
//      try {
//        Thread.sleep(1_000L);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
    }
  }
}
