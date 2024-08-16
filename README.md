# Legacy Kits

**Overview:**

Legacy Kits is a mysql based [Spigot](https://spigotmc.org/) plugin.

**Features:**

- **Customizable Discord Invite Link:** Server owners can easily configure the Discord invite link to their community server.
  
- **Flexible Message Format:** Customize the message format that players receive when using the `/discord` command. Include dynamic placeholders for the invite link to make it easy to maintain and update.

**Usage:**

1. Install the plugin on your Bukkit/Spigot server.
2. Customize the Discord invite link and message format in the `config.yml` file.
3. Players can use the `/discord` and `/store` command in-game to access the Discord invite link or the store link.

**Configuration Example:**

```

# This plugin is based on the MiniMessage API, means that colors are displayed by using "<color>" or "<rgbcolor".
# Even decorations can be displayed through MiniMessage.
# You can find more here: https://docs.advntr.dev/minimessage/format.html


Messages:
  Join:
  # In these messages, <Player> is a variable that will be replaced with the player's name.
    Enabled: "true"
    Message: "<gray>[<green>+<gray>] <Player>"
  Leave:
  # In these messages, <Player> is a variable that will be replaced with the player's name.
    Enabled: "true"
    Message: "<gray>[<red>-<gray>] <Player>"

  Reload:
    Message: "<green>The config has been reloaded"
  Discord:
    Message: "\n<blue><bold> DISCORD SERVER<reset>\n\n<gray>Did you know that this server has a <blue>discord server??<reset>\n<gray>You can join in on things like <blue>events<gray>, <blue>giveaways <gray>ect..<link>"
    # Make this link clickable by using <click:OPEN_URL:https://discord.gg/example> Example Text/Example Link </click>
    Link: "\n\n<blue><underlined> https://discord.gg/defaultlink"
    Actionbar: "<blue>Discord Link Sent"
  Store:
    Message: "\n<green><bold> SERVER STORE<reset>\n\n<gray>Did you know that this server has a <green>server store??<reset>\n<gray>You can purchase items like <green>crate keys<gray>, <green>ranks <gray>ect..<link>"
    # Make this link clickable by using <click:OPEN_URL:https://discord.gg/example> Example Text/Example Link </click>
    Link: "\n\n<green><underlined> https://discord.gg/defaultlink"
    Actionbar: "<green>Store Link Sent"
```

**Contributing:**

Contributions to this project are welcome! If you have any suggestions, bug fixes, or feature requests, feel free to open an issue or submit a pull request.

**License:**

This plugin is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

**APIs Emplyed**

MessageCore leverages the following powerful APIs:

- **Manere's Utils API**: Enhance your gameplay with the comprehensive utilities provided by Manere's Utils API. [GitHub Link](https://github.com/Manered/Utils)

**Author:**

Created by [Omnikeck](https://github.com/omnikeck)
