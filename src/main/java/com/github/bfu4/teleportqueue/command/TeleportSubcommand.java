/**
 * MIT License
 * <p>
 * Copyright (c) 2021 bfu4
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package com.github.bfu4.teleportqueue.command;

import com.github.bfu4.teleportqueue.TeleportQueuePlugin;
import com.github.bfu4.teleportqueue.abs.TeleportUser;
import com.github.bfu4.teleportqueue.abs.command.CommandBase;
import com.github.bfu4.teleportqueue.abs.command.Permission;
import com.github.bfu4.teleportqueue.abs.command.Subcommand;
import com.github.bfu4.teleportqueue.abs.command.Usage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Permission("teleportqueue.tp")
@Usage(TeleportQueuePlugin.COLORED_PREFIX + " &7/teleportqueue tp <player/{x, y, z}>")
@Subcommand
public class TeleportSubcommand extends CommandBase {

   public TeleportSubcommand(TeleportQueuePlugin plugin) {
      super("tp", plugin);
   }

   @Override
   public void execute(TeleportUser user, String[] args) {
      if (args.length == 3) {
         user.queueTeleport(new Location(user.getLocation().getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2])));
      } else if (args.length == 1) {
         Player player = Bukkit.getPlayer(args[0]);
         if (player != null) {
            user.queueTeleport(player.getLocation());
         } else {
            user.sendFormattedMessage(String.format("&7Player %s does not exist!", args[0]));
         }
      }
   }

}
