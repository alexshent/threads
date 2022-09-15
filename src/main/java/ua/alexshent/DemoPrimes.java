package ua.alexshent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Напишите приложение, которое в 2 потока будет считать
 * количество простых чисел, которые заданы в List, выводить
 * результат и возвращать его в главный поток.
 * Главный поток подсчитывает и выводит общее количество.
 */
public class DemoPrimes {

    private final List<Integer> list;
    private final AtomicInteger totalCounter;

    public DemoPrimes() {
        list = new ArrayList<>();
        totalCounter = new AtomicInteger();
    }

    class PrimesCounter implements Runnable {
        private final List<Integer> list;
        private final int startIndex;
        private final int endIndex;
        private int counter;

        public PrimesCounter(List<Integer> list, int startIndex, int endIndex) {
            this.list = list;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            for (int index = startIndex; index <= endIndex; index ++) {
                if (isPrime(list.get(index))) {
                    counter++;
                }
            }
            System.out.println("counter = " + counter);
            totalCounter.addAndGet(counter);
        }
    }

    public boolean isPrime(int number) {
        return number > 1
                && IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .noneMatch(n -> (number % n == 0));
    }

    public void demo() {

        list.add(1);
        list.add(2); // prime number
        list.add(3); // prime number
        list.add(4);
        list.add(5); // prime number
        list.add(6);

        list.add(7); // prime number
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11); // prime number
        list.add(12);

        Thread thread1 = new Thread(new PrimesCounter(list, 0, list.size() / 2 - 1));
        Thread thread2 = new Thread(new PrimesCounter(list, list.size() / 2, list.size() - 1));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("total counter = " + totalCounter);
    }
}
