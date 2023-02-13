package hw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        List<Future> futures = new ArrayList<>(); //список future

        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        long startTs = System.currentTimeMillis(); // start time
        for (String text : texts) {

            Callable<Integer> myCallable = new MyCallable(text);
            FutureTask futureTask = new FutureTask(myCallable);
            new Thread(futureTask).start();
            futures.add(futureTask);

        }

        int x = 0;
        for (Future future : futures) {
            if ((int) future.get() > x) {
                x = (int) future.get();
            }
        }
        System.out.println("Максимальный интервал значений: " + x);

        long endTs = System.currentTimeMillis(); // end time
        System.out.println("Time: " + (endTs - startTs) + "ms");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}