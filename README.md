# adopt-plugin
Very weird minecraft plugin - made-to-order

<br>

---

<br>

DEBUG VERSION!  
USE MAIN BRANCH IF POSSIBLE!  


<br>

---

<br>

# About  
If you do not know what this is, then this won't be relevant to you.  
This is a minecraft plugin, I was requested to program.  
Basically, there are a few players that can adopt new players that wish to join the server, this plugin will be running on. The adopted player is now their property for a few days and will have some negative potion effects until they are grownup.  
The first players will have to be imported using `/adoptimport`.  
Also, a whitelist must be active, to which no players should be manually added.  

<br>

# User-commands  
`/adoptwith <player> <target>`  
Request <player> to adopt and raise <target>. The player having been sent the request has 2 minutes to accept or decline before the request will become invalid. Once the other player has accepted, the administrators will be notified to add the new user to the game using `/adoptimport`.  

<br>

# Admin-commands  
`/adoptimport <option> <user>`  
Add the specified user to the game, plus whitelisting them. The <option> option must be set to either "grownup" or "child", specifiying if the player is already a grownup, or a child that has been adopted.  

`/adoptsave`  
Save all data to the 'adopt_data.txt' file. Automatically done on every reload/restart.  

`/adoptparse`  
Parse an instruction, like the ones that are used in the 'adopt_data.txt' files. The format will be explained soon.

<br>

---

<br>

# Developer info  
Building the plugin can either be done by setting up a minecraft plugin development workspace for spigot 1.16, or by using the build file.  
It is a simple bash script. Before running, you will have to "chmod +x it" first, then you can run it by typing "./build.sh". It will download a spigot server jar, if not present, and compile everything into a file named "Adopt.jar".
