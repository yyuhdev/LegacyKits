package club.revived.config;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;

public enum TextMessageType {
    /**
     * Sends an action bar message
     *
     * @see Audience#sendActionBar(Component)
     */
    ACTION_BAR,

    /**
     * Sends a chat message and an action bar message
     * at the same time.
     *
     * @see TextMessageType#ACTION_BAR
     * @see TextMessageType#CHAT
     */
    BOTH,

    /**
     * Sends a chat message
     *
     * @see Audience#sendMessage(Component)
     */
    CHAT,

    /**
     * Sends a title
     *
     * @see Audience#sendTitlePart(TitlePart, Object)
     */
    TITLE
}