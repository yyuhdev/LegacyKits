package club.revived.config;

import dev.manere.utils.elements.Elements;
import dev.manere.utils.model.Tuple;
import dev.manere.utils.text.font.SmallFont;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TextMessages {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @SuppressWarnings("unused")
    private final TextMessageType type;
    private final List<String> raw;
    private final Elements<Tuple<String, String>> replacements;

    private Sound sound = null;
    private double pitch = -1;
    private double volume = -1;

    private String title;
    private String subtitle;
    private Duration fadeIn;
    private Duration stay;
    private Duration fadeOut;

    public TextMessages(final @NotNull TextMessageType type, final @NotNull String raw, final @NotNull Elements<Tuple<String, String>> replacements) {
        this.type = type;
        this.raw = new ArrayList<>(List.of(raw));
        this.replacements = replacements;
    }

    public TextMessages(final @NotNull TextMessageType type, final @NotNull String raw) {
        this(type, raw, Elements.of());
    }

    public TextMessages(final @NotNull TextMessageType type, final @NotNull List<String> raw, final @NotNull Elements<Tuple<String, String>> replacements) {
        this.type = type;
        this.raw = raw;
        this.replacements = replacements;
    }

    public TextMessages(final @NotNull TextMessageType type, final @NotNull List<String> raw) {
        this(type, raw, Elements.of());
    }

    @NotNull
    public Component text() {
        final TagResolver smallCapsTag = TagResolver.resolver("small_caps", (queue, ctx) -> {
            final String text = queue.popOr("Invalid syntax found. Should be <small_caps:'text to convert'>").value();
            return Tag.selfClosingInserting(Component.text(SmallFont.convert(text)));
        });

        return miniMessage.deserialize(replacedRaw(), smallCapsTag);
    }

    @NotNull
    public String replaced(@NotNull String text) {
        for (final Tuple<String, String> tuple : replacements) {
            final String rawTupleKey = tuple.key();
            if (rawTupleKey == null) continue;

            final String tupleKey = rawTupleKey
                    .replaceAll("<", "")
                    .replaceAll(">", "");

            final String key = "<" + tupleKey + ">";
            final String val = tuple.val();
            if (val == null) continue;

            text = text.replaceAll(key, val);
        }

        return text;
    }

    @NotNull
    public String replacedRaw() {
        String rawText = String.join("<newline>", this.raw);

        for (final Tuple<String, String> tuple : replacements) {
            final String rawTupleKey = tuple.key();
            if (rawTupleKey == null) continue;

            final String tupleKey = rawTupleKey
                    .replaceAll("<", "")
                    .replaceAll(">", "");

            final String key = "<" + tupleKey + ">";
            final String val = tuple.val();
            if (val == null) continue;

            rawText = rawText.replaceAll(key, val);
        }

        return rawText;
    }

    public void sound(final @NotNull String rawSound, final double pitch, final double volume) {
        this.sound = Sound.valueOf(rawSound.replaceAll("minecraft:", ""));
        this.pitch = pitch;
        this.volume = volume;
    }

    @Nullable
    public Sound sound() {
        return sound;
    }

    public double pitch() {
        return pitch;
    }

    public double volume() {
        return volume;
    }

    public TextMessageType type() {
        return type;
    }

    public void title(
            final @NotNull String title, final @NotNull String subtitle,
            final @NotNull Duration fadeIn,
            final @NotNull Duration stay,
            final @NotNull Duration fadeOut
    ) {
        this.title = title;
        this.subtitle = subtitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    @Nullable
    public Component title() {
        final TagResolver smallCapsTag = TagResolver.resolver("small_caps", (queue, ctx) -> {
            final String text = queue.popOr("Invalid syntax found. Should be <small_caps:'text to convert'>").value();
            return Tag.selfClosingInserting(Component.text(SmallFont.convert(text)));
        });

        if (title == null || title.isEmpty() || title.isBlank()) return null;
        return miniMessage.deserialize(replaced(title), smallCapsTag);
    }

    @Nullable
    public Component subtitle() {
        final TagResolver smallCapsTag = TagResolver.resolver("small_caps", (queue, ctx) -> {
            final String text = queue.popOr("Invalid syntax found. Should be <small_caps:'text to convert'>").value();
            return Tag.selfClosingInserting(Component.text(SmallFont.convert(text)));
        });

        if (subtitle == null || subtitle.isEmpty() || subtitle.isBlank()) return null;
        return miniMessage.deserialize(replaced(subtitle), smallCapsTag);
    }

    @Nullable
    public Duration fadeIn() {
        return fadeIn;
    }

    @Nullable
    public Duration stay() {
        return stay;
    }

    @Nullable
    public Duration fadeOut() {
        return fadeOut;
    }
}