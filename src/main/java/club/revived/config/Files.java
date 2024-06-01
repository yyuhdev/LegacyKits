package club.revived.config;

import club.revived.WeirdoKits;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.utils.server.Servers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Files {
    @NotNull
    @CanIgnoreReturnValue
    public static FileConfiguration config(final @NotNull File file) {
        try {
            return CompletableFuture.supplyAsync(
                    () -> YamlConfiguration.loadConfiguration(file)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @CanIgnoreReturnValue
    public static boolean saveConfig(final @NotNull File file, final @NotNull FileConfiguration config) {
        try {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    config.save(file);
                    return true;
                } catch (final IOException ignored) {
                    return false;
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    @NotNull
    @CanIgnoreReturnValue
    public static File create(final @NotNull File file) {
        try {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    if (!file.exists() && !file.createNewFile()) Servers.wrapped(
                        WeirdoKits.class
                    ).log(
                        999,
                        "Failed to create file '" + file.getPath() + "'."
                    );
                } catch (IOException e) {
                    Servers.wrapped(
                        WeirdoKits.class
                    ).log(
                        999,
                        "Failed to create file '" + file.getPath() + "'."
                    );
                }

                return file;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @CanIgnoreReturnValue
    public static File mkdirs(final @NotNull File directory) {
        try {
            return CompletableFuture.supplyAsync(() -> {
                if (!directory.exists() && !directory.mkdirs()) Servers.wrapped(
                    WeirdoKits.class
                ).log(
                    999,
                    "Failed to create directory '" + directory.getPath() + "'."
                );

                return directory;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @CanIgnoreReturnValue
    public static File file(final @NotNull String... nodes) {
        final String joined = String.join("/", nodes);

        if (nodes[nodes.length - 1].endsWith(".yml")) {
            return new File(Servers.dataFolder(), "/" + joined);
        } else if (nodes[nodes.length - 1].endsWith(".schem")) {
            return new File(Servers.dataFolder(), "/" + joined);
        } else if (nodes[nodes.length - 1].endsWith("/")) {
            return new File(Servers.dataFolder(), "/" + joined + "/");
        } else {
            return new File(Servers.dataFolder(), "/" + joined);
        }
    }

    public static void save(final @NotNull String... nodes) {
        String joined;

        if (nodes.length > 1) {
            joined = String.join("/", nodes);
        } else {
            joined = nodes[0];
        }

        final File file = file(nodes);

        if (file.getPath().endsWith(".yml")) {
            if (!file.exists()) Servers.wrapped(WeirdoKits.class).saveResource(joined, false);
        } else {
            if (!file.exists()) mkdirs(file);
        }
    }
}