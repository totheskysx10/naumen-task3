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
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     *
     * Сложность: O(n + m + k), сформирована из O(n) - создание первой коллекциии,
     * O(m) - создание второй коллекции, O(m+n) - retainAll и O(k) - преобразование результата в список.
     * Получается O(2n + 2m + k), но упрощено до O(n + m + k).
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        // LinkedHashSet позволит быстро итерироваться
        // по элементам благодаря связанности (это нужно для retainAll).
        HashSet<User> setA = new LinkedHashSet<>(collA);

        // Сложность операции contains (это нужно для retainAll) в HashSet - O(1), благодаря хешированию.
        HashSet<User> setB = new HashSet<>(collB);

        setA.retainAll(setB);

        return setA.stream().toList();
    }
}
