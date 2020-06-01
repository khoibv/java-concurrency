package vn.com.khoibv;

import java.time.Duration;
import java.time.Instant;

public class MainApp {

  public static void main(String[] args) {

    var app = new MainApp();
//    app.classicalThread();
//    app.loomThread();
    app.concurrentBenchmark();
  }

  private void concurrentBenchmark() {
    monitorTime(() -> {
      new ConcurrentStrategyBenchmark().run();
    });
  }

  void loomThread() {
    monitorTime(() -> {
      try {
        new LoomThread().run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

  void classicalThread() {
    monitorTime(() -> {
      try {
        new ClassicalThread().run();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }

  private void monitorTime(Runnable runnable) {
    Instant start = Instant.now();

    runnable.run();

    Instant end = Instant.now();
    System.out.println(String.format("=== Duration: %s", Duration.between(start, end)));
  }

}
