package club.revived.config;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.utils.library.Utils;
import dev.manere.utils.scheduler.Schedulers;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ConfigHandler {
    private static final Map<Class<?>, Function<Object, ?>> typeConverters = new HashMap<>();

    static {
        typeConverters.put(int.class, object
                -> object instanceof Integer integer ? integer : Integer.valueOf(Integer.parseInt(object.toString())));
        typeConverters.put(Integer.class, object
                -> object instanceof Integer integer ? integer : Integer.valueOf(Integer.parseInt(object.toString())));
        typeConverters.put(double.class, object
                -> object instanceof Double doubleValue ? doubleValue : Double.valueOf(Double.parseDouble(object.toString())));
        typeConverters.put(Double.class, object
                -> object instanceof Double doubleValue ? doubleValue : Double.valueOf(Double.parseDouble(object.toString())));
        typeConverters.put(Component.class, object
                -> object instanceof Component component ? component : TextStyle.style((String) object));
        typeConverters.put(String.class, object
                -> object instanceof String string ? string : object);
        typeConverters.put(boolean.class, object
                -> object instanceof Boolean bool ? bool : Boolean.valueOf(Boolean.parseBoolean(object.toString())));
        typeConverters.put(Boolean.class, object
                -> object instanceof Boolean bool ? bool : Boolean.valueOf(Boolean.parseBoolean(object.toString())));
        typeConverters.put(TimeUnit.class, object -> object instanceof String string ? switch (string.toLowerCase()) {
            case "hours", "hour", "h" -> TimeUnit.HOURS;
            case "minutes", "minute", "min", "m" -> TimeUnit.MINUTES;
            default -> TimeUnit.SECONDS;
        } : TimeUnit.SECONDS);
    }

    @NotNull
    @CanIgnoreReturnValue
    public static FileConfiguration config() {
        return ConfigHandler.config(Utils.plugin());
    }

    @NotNull
    @CanIgnoreReturnValue
    public static FileConfiguration config(final @NotNull JavaPlugin plugin) {
        try {
            return CompletableFuture.supplyAsync(() -> {
                plugin.saveDefaultConfig();
                plugin.reloadConfig();
                return plugin.getConfig();
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> void edit(final @NotNull String path, final @NotNull ValueEditor<V> editor) {
        config().set(path, editor.edit((V) config().get(path)));
        save();
    }

    public static <V> void edit(final @NotNull String path, final @NotNull Class<V> type, final @NotNull ValueEditor<V> editor) {
        config().set(path, editor.edit(type.cast(config().get(path))));
        save();
    }

    @NotNull
    public static <T> T value(final @Nullable Class<T> type, final @NotNull String path) {
        final Object value = ConfigHandler.config().get(path);

        if (type == null) {
            if (value != null) {
                return at(path);
            }

            return at(path);
        }

        if (typeConverters.containsKey(type)) {
            if (value != null) {
                return type.cast(typeConverters.get(type).apply(value));
            }

            return type.cast(new Object() {});
        } else {
            return Objects.requireNonNull(ConfigHandler.config().getObject(path, type), "Value at path '" + path + "' is null");
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> T at(final @NotNull String path) {
        return (T) Objects.requireNonNull(ConfigHandler.config().get(path));
    }

    @NotNull
    public static List<String> list(final @NotNull String path) {
        final FileConfiguration configuration = config();
        return configuration.getStringList(path);
    }

    @NotNull
    public static List<Map<?, ?>> mapList(final @NotNull String path) {
        return config().getMapList(path);
    }

    public static void comment(final @NotNull String path, final @NotNull String @NotNull ... comments) {
        comment(path, new ArrayList<>(Arrays.asList(comments)));
    }

    public static void comment(final @NotNull String path, final @NotNull List<String> comments) {
        config().setComments(path, comments);
    }

    public static void inlineComment(final @NotNull String path, final @NotNull String @NotNull ... comments) {
        inlineComment(path, new ArrayList<>(Arrays.asList(comments)));
    }

    public static void inlineComment(final @NotNull String path, final @NotNull List<String> comments) {
        config().setInlineComments(path, comments);
    }

    public static void save() {
        Schedulers.async().execute(() -> Utils.plugin().saveConfig());
    }
}