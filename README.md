# Legacy Kits

**Overview:**

Legacy Kits is a mysql based [Spigot](https://spigotmc.org/) plugin. The plugin allows players to create their own personal kits
to fight each other on your server. Legacy kits is currently based on the [MiniMessage API](https://docs.advntr.dev/minimessage/format.html). 
Legacy kits uses [FastInv](https://github.com/MrMicky-FR/FastInv) for menus & such. The plugin also offeres a so called "kitroom" which
is completly customizable by using ```/kitadmin kitroom [<text>]```. The plugin is most likely made for so called "Crystal PvP Practice"
servers, but you're also able to use it anywhere else.

# Installation

Just install the plugin on your server by putting the compiled jar file into the `container/plugins/` folder. Right after you restart
your server you'll be facing an error as you need to configure your mysql host first.

```config

# Mysql connection details

# IP of the database
host: localhost
# Port of the database
port: 3306
# Name of the database
database: u1_kits
# Username to authenticate your connection
username: root
# Password of the database
password: p4ssw0rd

```
# Commands
 - `/kit<num>` allows players to claim their kit.
 - `/kit` allows players to open the kit main menu
 - `/kitadmin <value> <value>` allows server admins to make changes to the plugin
 - `/claim <value>` allows players to claim preset kits
 - `/clear`, `/clearec`, `/cleareffects` allows players to clear their enderchest, inventory or potion effects



Created by [yyuh](https://github.com/yyuhdev)
