package ru.naumen.collection.task2;

import ru.naumen.collection.task1.Ticket;

import java.util.Arrays;
import java.util.Objects;

/**
 * Пользователь
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class User {
    private String username;
    private String email;
    private byte[] passwordHash;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User user))
            return false;
        return (Objects.equals(this.username, user.username) && Objects.equals(this.email, user.email) && Arrays.equals(this.passwordHash, user.passwordHash));
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(username, email);
        result = 31 * result + Arrays.hashCode(passwordHash);
        return result;
    }
}
