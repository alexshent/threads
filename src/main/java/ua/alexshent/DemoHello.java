package ua.alexshent;

/**
 * Напишите приложение, которое создает 50 потоков один за
 * одним, каждый из потоков выводит сообщение "Hello from thread
 * (number of thread)", особенность заключается в том, что вывод
 * должен быть строго в обратном порядке, от 49 до 0
 */
public class DemoHello {

    private volatile int counter;

    private synchronized int getCounter() {
        return counter;
    }

    private synchronized void setCounter(int counter) {
        this.counter = counter;
    }

    private synchronized void decrementCounter() {
        counter--;
    }

    public void demo() {
        final int numberOfThreads = 50;

        setCounter(numberOfThreads);

        while (getCounter() > 0) {
            Thread thread = new Thread(() -> {
                decrementCounter();
                System.out.println("Hello from thread " + getCounter());
            });

            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
