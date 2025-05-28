package ru.elementcraft.elementmeteor.storage;

import ru.elementcraft.elementmeteor.data.User;

import java.util.concurrent.CompletableFuture;

public interface Storage {

    CompletableFuture<User> getUser(String name);
    void addUser(User user);
    void editUser(User user);

}
