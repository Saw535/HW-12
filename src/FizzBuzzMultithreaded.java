import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FizzBuzzMultithreaded {
    private final int n;
    private int currentNumber = 1;
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final Logger logger = Logger.getLogger(FizzBuzzMultithreaded.class.getName());

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
    }

    public void fizz() {
        while (true) {
            synchronized (this) {
                if (currentNumber > n) break;
                if (currentNumber % 3 == 0 && currentNumber % 5 != 0) {
                    queue.add("fizz");
                    currentNumber++;
                    notifyAll();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, "Thread interrupted", e);
                    }
                }
            }
        }
    }

    public void buzz() {
        while (true) {
            synchronized (this) {
                if (currentNumber > n) break;
                if (currentNumber % 5 == 0 && currentNumber % 3 != 0) {
                    queue.add("buzz");
                    currentNumber++;
                    notifyAll();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, "Thread interrupted", e);
                    }
                }
            }
        }
    }

    public void fizzbuzz() {
        while (true) {
            synchronized (this) {
                if (currentNumber > n) break;
                if (currentNumber % 3 == 0 && currentNumber % 5 == 0) {
                    queue.add("fizzbuzz");
                    currentNumber++;
                    notifyAll();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, "Thread interrupted", e);
                    }
                }
            }
        }
    }

    public void number() {
        while (true) {
            synchronized (this) {
                if (currentNumber > n && queue.isEmpty()) break;
                if (!queue.isEmpty()) {
                    System.out.println(queue.poll());
                    notifyAll();
                } else if (currentNumber % 3 != 0 && currentNumber % 5 != 0) {
                    System.out.println(currentNumber);
                    currentNumber++;
                    notifyAll();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        logger.log(Level.SEVERE, "Thread interrupted", e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(n);

        Thread threadA = new Thread(fizzBuzz::fizz);
        Thread threadB = new Thread(fizzBuzz::buzz);
        Thread threadC = new Thread(fizzBuzz::fizzbuzz);
        Thread threadD = new Thread(fizzBuzz::number);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            Logger.getLogger(FizzBuzzMultithreaded.class.getName()).log(Level.SEVERE, "Thread interrupted", e);
        }
    }
}
