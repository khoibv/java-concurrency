package vn.com.khoibv;

public class LoomThread {

  public void run() throws InterruptedException {
    final long THREAD_COUNT = 1_000_000;
    for (long i = 0L; i < THREAD_COUNT; i++) {
      Thread thread = Thread.startVirtualThread(new CustomLoomThread(i));
      thread.join();
    }
  }

  static class CustomLoomThread implements Runnable {

    private final long id;
    CustomLoomThread(long id) {
      this.id = id;
    }

    @Override
    public void run() {
      System.out.println(String.format("Virtual thread %s. Total thread: %s", id, java.lang.Thread.activeCount()));
//      try {
//        Thread.sleep(1_000L);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
    }
  }
}
