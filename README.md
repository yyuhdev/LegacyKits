## Aithon Kits

Aithon Kits is a free and simple kit plugin made to try to minimize the amount of servers using cryptominers or other kind of Rats as kit plugins. 

# Config 

**messages.yml**

``` 
PREFIX: "<gold><bold>PK"

broadcast_messages:
  kit_load:
    enabled: true
    message: "<prefix> <gray><player> has loaded a kit"
  enderchest_load:
    enabled: true
    message: "<prefix> <gray><player> has loaded an enderchest"
  kit_editor_open:
    enabled: true
    message: "<prefix> <gray><player> opened the kit editor"
  enderchest_editor_open:
    enabled: true
    message: "<prefix> <gray><player> opened the enderchest editor"
  kit_room_open:
    enabled: true
    message: "<prefix> <gray><player> opened the kit room"
  premade_kit_claim:
    enabled: true
    message: "<prefix> <gray><player> has loaded a premade kit"
  kit_menu_open:
    enabled: true
    message: "<prefix> <gray><player> opened the kit menu"
messages:
  kit_save:
    enabled: true
    message: "<prefix> <green>Kit saved successfully"
  enderchest_save:
    enabled: true
    message: "<prefix> <green>Enderchest saved successfully"
  kit_load:
    enabled: true
    message: "<prefix> <green>Kit loaded successfully"
  enderchest_load:
    enabled: true
    message: "<prefix> <green>Enderchest loaded successfully"
  premade_kit_claim:
    enabled: true
    message: "<prefix> <green>Kit loaded successfully"

auto_broadcast:
  enabled: true
  timer: 5
  message: "e"
 ```
