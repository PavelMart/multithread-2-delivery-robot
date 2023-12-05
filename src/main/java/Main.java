import java.util.*;

public class Main {
    static final Integer THREADS_COUNT = 50;
    public static final HashMap<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < THREADS_COUNT; i++) {
            int finalI = i;
            new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int routeLength = (int) route.chars()
                        .filter(x -> x == 'R')
                        .count();
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(routeLength)) {
                        sizeToFreq.put(routeLength, sizeToFreq.get(routeLength) + 1);
                    } else {
                        sizeToFreq.put(routeLength, 1);
                    }
                }
                System.out.println("Количество команд поворота направо в маршруте №" + finalI + " составляет " + routeLength);
            }).start();
        }

        Map.Entry<Integer, Integer> max = Collections.max(sizeToFreq.entrySet(), Map.Entry.comparingByValue());

        System.out.println("\n\n--- Самое частое количество повторений " + max.getKey() + " (встретилось " + max.getValue() + " раз)");

        System.out.println("Другие размеры:");

        TreeMap<Integer, Integer> treeMap = new TreeMap<>(Collections.reverseOrder());
        treeMap.putAll(sizeToFreq);

        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            if (entry.getKey().equals(max.getKey())) continue;
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("- " + key + " (" + value + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
