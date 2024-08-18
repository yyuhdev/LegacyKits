package club.revived.storage;

import club.revived.storage.handler.DatabaseHandler;
import club.revived.storage.handler.DatabaseType;
import club.revived.storage.handler.MySQLHandler;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * This is the manager for all database interactions.
 * There should be no case where this is modified.
 * Access this class via {@link DatabaseManager#getInstance()}
 */
public final class DatabaseManager {
    private static DatabaseManager instance;

    private final Map<DatabaseType, Class<? extends DatabaseHandler>> databaseHandlers = new HashMap<>();
    private final DatabaseHandler handler;

    private DatabaseManager() {
//        Fadah.getConsole().info("Connecting to Database and populating caches...");
        databaseHandlers.put(DatabaseType.MYSQL, MySQLHandler.class);

        this.handler = initHandler();
//        Fadah.getConsole().info("Connected to Database and populated caches!");
    }

    public <T> CompletableFuture<List<T>> getAll(Class<T> clazz) {
        if (!isConnected()) {
//            Fadah.getConsole().severe("Tried to perform database action when the database is not connected!");
            return CompletableFuture.completedFuture(List.of());
        }
        return CompletableFuture.supplyAsync(() -> handler.getAll(clazz));
    }

    public <T> CompletableFuture<Optional<T>> get(Class<T> clazz, UUID id) {
        if (!isConnected()) {
//            Fadah.getConsole().severe("Tried to perform database action when the database is not connected!");
            return CompletableFuture.completedFuture(Optional.empty());
        }
        return CompletableFuture.supplyAsync(() -> handler.get(clazz, id));
    }

    public <T> CompletableFuture<Void> save(Class<T> clazz, T t) {
        if (!isConnected()) {
//            Fadah.getConsole().severe("Tried to perform database action when the database is not connected!");
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> {
            handler.save(clazz, t);
            return null;
        });
    }

    public <T> CompletableFuture<Void> delete(Class<T> clazz, T t) {
        if (!isConnected()) {
//            Fadah.getConsole().severe("Tried to perform database action when the database is not connected!");
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> {
            handler.delete(clazz, t);
            return null;
        });
    }

    public <T> CompletableFuture<Void> update(Class<T> clazz, T t, String[] params) {
        if (!isConnected()) {
//            Fadah.getConsole().severe("Tried to perform database action when the database is not connected!");
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> {
            handler.update(clazz, t, params);
            return null;
        });
    }

    public <T> CompletableFuture<Void> deleteSpecific(Class<T> clazz, T t, Object o) {
        if (!isConnected()) {
//            Fadah.getConsole().severe("Tried to perform database action when the database is not connected!");
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.supplyAsync(() -> {
            handler.deleteSpecific(clazz, t, o);
            return null;
        });
    }

    public boolean isConnected() {
        return handler.isConnected();
    }

    public void shutdown() {
        handler.destroy();
    }

    private DatabaseHandler initHandler() {
//        DatabaseType type = Config.DATABASE_TYPE.toDBTypeEnum();
//        Fadah.getConsole().info("DB Type: %s".formatted(type.getFriendlyName()));
        try {
            Class<? extends DatabaseHandler> handlerClass = databaseHandlers.get(DatabaseType.MYSQL);
            if (handlerClass == null) {
                throw new IllegalStateException("No handler for database type %s registered!".formatted(DatabaseType.MYSQL));
            }
            return handlerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
            instance.handler.connect();
        }
        return instance;
    }
}