import java.util.logging.Level;
import java.util.logging.Logger;

public class TimeDisplay {
    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        Object lock = new Object();
        Logger logger = Logger.getLogger(TimeDisplay.class.getName());

        Thread timeThread = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                    System.out.println("Час, що минув: " + elapsedTime + " секунд");
                    try {
                        lock.wait(1000);
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, "Interrupted while waiting", e);
                    }
                }
            }
        });

        Thread messageThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    System.out.println("Минуло 5 секунд");
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "Thread interrupted while sleeping", e);
                }
            }
        });

        timeThread.start();
        messageThread.start();
    }
}
