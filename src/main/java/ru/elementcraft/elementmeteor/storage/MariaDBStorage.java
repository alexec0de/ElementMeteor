package ru.elementcraft.elementmeteor.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.elementcraft.elementmeteor.Config;
import ru.elementcraft.elementmeteor.data.User;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MariaDBStorage implements Storage {

    private final HikariDataSource dataSource;
    private final ExecutorService dbExecutor = Executors.newFixedThreadPool(2);

    public MariaDBStorage() {
        final HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://" + Config.Mariadb.host + ":" + Config.Mariadb.port + "/" + Config.Mariadb.database);
        config.setUsername(Config.Mariadb.user);
        config.setPassword(Config.Mariadb.password);
        config.setMaximumPoolSize(3);
        dataSource = new HikariDataSource(config);
        initDatabase();
    }

    private void initDatabase() {
        CompletableFuture.runAsync(() -> {
            try (final Connection connection = dataSource.getConnection();
                 final PreparedStatement statement = connection.prepareStatement(
                         "CREATE TABLE IF NOT EXISTS uses (" +
                                 "name VARCHAR(36) PRIMARY KEY," +
                                 "uses INT NOT NULL DEFAULT 0)")) {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, dbExecutor);
    }

    @Override
    @Nullable
    public CompletableFuture<User> getUser(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try (final Connection connection = dataSource.getConnection();
                 final PreparedStatement statement = connection.prepareStatement(
                         "SELECT uses FROM uses WHERE name = ?")) {
                statement.setString(1, name);

                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        int uses = result.getInt("uses");
                        return new User(name, uses);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, dbExecutor);
    }

    @Override
    public void addUser(User user) {
        CompletableFuture.runAsync(() -> {
            try (final Connection connection = dataSource.getConnection();
                 final PreparedStatement statement = connection.prepareStatement("INSERT INTO uses (name, uses) VALUES (?, 0)")) {
                statement.setString(1, user.getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, dbExecutor);

    }

    @Override
    public void editUser(User user) {
        CompletableFuture.runAsync(() -> {
            try (final Connection connection = dataSource.getConnection();
                 final PreparedStatement statement = connection.prepareStatement(
                         "UPDATE uses SET uses = ? WHERE name = ?")) {
                statement.setInt(1, user.getUses());
                statement.setString(2, user.getName());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, dbExecutor);
    }
}
