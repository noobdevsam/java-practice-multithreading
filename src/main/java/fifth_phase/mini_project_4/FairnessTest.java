package fifth_phase.mini_project_4;

import java.util.concurrent.locks.ReentrantLock;

public record FairnessTest(ReentrantLock lock) {

    public static void main(String[] args) {
        var test = new FairnessTest(
                new ReentrantLock(true)
        );

        for (int i = 0; i <= 3; i++) {
            test.access("test" + i);
        }
    }

    public void access(String name) {
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    System.out.println(name + " acquired the lock");
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                    System.out.println(name + " released the lock");
                }
            }
        }).start();
    }
}
