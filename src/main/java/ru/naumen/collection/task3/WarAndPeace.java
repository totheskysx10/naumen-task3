package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace
{

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    /**
     * Общая сложность алгоритма - O(m) + O(n log n)
     * m - количество слов в файле
     * n - количество уникальных слов
     *
     * Чтение и обработка файла с подсчетом слов: O(m).
     * Поиск топ-10 и последних 10 слов с помощью PriorityQueue:
     * Вставка в очередь O(log 10) = O(1),
     * Перебор n уникальных слов с операциями вставки и удаления - O(n log 10) = O(n).
     * Вывод: O(10) + O(10).
     *
     * В результате можно упростить до O(m + n log n).
     *
     * @param args
     */
    public static void main(String[] args) {
        // LinkedHashMap - связанное отображение, поэтому позволяет
        // быстро итерироваться по нему.
        // Сложность операций вставки и обновления (merge) - O(1),
        // так как все операции с доступом и модификацией элементов
        // имеют сложность O(1) из-за хеширования.
        Map<String, Long> result = new LinkedHashMap<>();

        // Операция merge имеет сложность O(1) из-за хеширования
        // Перебор происходит за O(m)
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    String lower = word.toLowerCase();
                    result.merge(lower, 1L, Long::sum);
                });

        // Используем PriorityQueue для нахождения 10 наиболее частых и редких слов, здесь слова сами будут
        // выстраиваться с учётом их количества вхождений - сортировка всех слов будет не нужна.
        PriorityQueue<Map.Entry<String, Long>> topWords = new PriorityQueue<>(Map.Entry.comparingByValue());
        PriorityQueue<Map.Entry<String, Long>> leastWords = new PriorityQueue<>((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()));

        // Перебор всех уникальных слов для нахождения первых 10 и последних 10, сложность - O(n log 10)
        result.entrySet().forEach(entry -> {
            topWords.offer(entry);
            if (topWords.size() > 10) {
                topWords.poll();
            }

            leastWords.offer(entry);
            if (leastWords.size() > 10) {
                leastWords.poll();
            }
        });

        // Перебор и вывод элементов. Сложность обеих операций - O(10)
        System.out.println("TOP 10 наиболее используемых слов:");
        topWords.stream().sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())).forEach(entry ->
                System.out.println(entry.getKey() + " - " + entry.getValue()));

        System.out.println("\nLAST 10 наименее используемых слов:");
        leastWords.stream().sorted(Comparator.comparingLong(Map.Entry::getValue)).forEach(entry ->
                System.out.println(entry.getKey() + " - " + entry.getValue()));
    }
}
