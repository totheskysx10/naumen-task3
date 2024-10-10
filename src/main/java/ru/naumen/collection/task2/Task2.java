package ru.naumen.collection.task2;

import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2
{

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях.
     *
     * Сложность: O(n + m + k)
     * O(m) - создание второй коллекции (множества), O(n) - итерация по первой коллекции с проверкой наличия в множестве,
     * O(k) - преобразование результата в список.
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        // Создаем HashSet для collB для эффективной работы contains O(1).
        Set<User> setB = new HashSet<>(collB);

        // Итерируемся по collA и оставляем только те элементы, которые есть в setB.
        return collA.stream()
                .filter(setB::contains)  // O(1) для каждой проверки contains
                .toList();               // Преобразование в список O(k), k - кол-во дубликатов
    }
}
