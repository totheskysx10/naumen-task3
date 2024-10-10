package ru.naumen.collection.task4;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
public class ConcurrentCalculationManager<T> {

    /**
     * LinkedBlockingQueue даёт безопасный доступ из нескольких потоков,
     * блокирует поток доступа, если очередь заполнена.
     */
    private final BlockingQueue<Future<T>> queue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Добавить задачу на параллельное вычисление
     * Сложность: O(1) + O(1), получается O(2).
     * Можно упростить до O(1).
     */
    public void addTask(Supplier<T> task) {
        // Выполняется асинхронно, сложность считаем за O(1)
        Future<T> future = executorService.submit(task::get);

        // offer() имеет сложность O(1), добавление в конец очереди
        // происходит быстро, даже если очередь заполнена, благодаря связанности.
        queue.offer(future);
    }

    /**
     * Получить результат вычисления.
     * Возвращает результаты в том порядке, в котором добавлялись задачи.
     * Сложность: O(1) + O(n), получается O(n + 1).
     * Можно упростить до O(n).
     * n - количество задач.
     */
    public T getResult(){
        try {
            // poll() имеет сложность O(1), извлечение элемента
            // из начала очереди также происходит быстро.
            // Она обращается к внутреннему указателю,
            // указывающему на первый элемент очереди.
            Future<T> future = queue.poll();
            if (future != null)
                // get() может заблокироваться, если задача еще не завершена,
                // поэтому сложность в худшем случае - O(n).
                return future.get();
            else
                return null;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }
}