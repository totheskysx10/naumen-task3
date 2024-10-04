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
     * Общая сложность алгоритма - O(m) + O(n (log n)) + O(n) + O(10)
     * Можно упростить до O(m + n(log n))
     * m - кол-во слов в файле
     * n - кол-во уникальных слов
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

        // Сортировка элементов отображения. Возьмём в качестве
        // сложности сортировки n(log n). Получается, сложность операции - O(n(log n))
        List<Map.Entry<String, Long>> sorted = result.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()).toList();

        // Перебор первых 10 элементов. Получается, сложность операции - O(10)
        System.out.println("TOP 10 наиболее используемых слов:");
        sorted.stream().limit(10).forEach(entry ->
                System.out.println(entry.getKey() + " - " + entry.getValue()));

        System.out.println("\nLAST 10 наименее используемых:");

        // Пропуск всех слов кроме 10 последних. Получается, сложность операции в худшем случае - O(n)
        sorted.stream().skip(Math.max(0, sorted.size() - 10)).forEach(entry ->
                System.out.println(entry.getKey() + " - " + entry.getValue()));
    }
}
