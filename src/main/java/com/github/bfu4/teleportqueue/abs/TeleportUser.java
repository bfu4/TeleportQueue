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


package com.github.bfu4.teleportqueue.abs;

import com.github.bfu4.teleportqueue.teleport.Teleportable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;
import com.github.bfu4.teleportqueue.TeleportQueuePlugin;
import com.github.bfu4.teleportqueue.teleport.queue.SafeTeleportQueue;
import com.github.bfu4.teleportqueue.teleport.queue.TeleportQueue;
import com.github.bfu4.teleportqueue.teleport.queue.TeleportQueues;
import com.github.bfu4.teleportqueue.teleport.queue.UnsafeTeleportQueue;

import java.util.UUID;

public class TeleportUser implements ServerOperator, Teleportable<TeleportUser> {

   private CommandSender sender;

   public TeleportUser(CommandSender sender) {
      this.sender = sender;
   }

   public void sendMessage(String message) {
      sender.sendMessage(translateMessage(message));
   }

   public void sendFormattedMessage(String message) {
      sendMessage(TeleportQueuePlugin.COLORED_PREFIX + " " + message);
   }

   public boolean isOperator() {
      return sender.isOp();
   }

   public boolean hasPermission(String permission) {
      return sender.hasPermission(permission);
   }

   public String getName() {
      return sender.getName();
   }

   public UUID getUUID() {
      Player player = Bukkit.getPlayer(sender.getName());
      return player != null ? player.getUniqueId() : null;
   }

   public Location getLocation() { return sender instanceof Player ? ((Player) sender).getLocation() : null; }

   private String translateMessage(String message) {
      return ChatColor.translateAlternateColorCodes('&', message);
   }

   @Override
   public boolean isOp() {
      return sender.isOp();
   }

   @Override
   public void setOp(boolean b) {
      sender.setOp(b);
   }

   public TeleportQueue<TeleportUser> queueTeleport(Location location) {
      if (sender instanceof Player) {
         Player player = (Player) sender;

         int gamemode = player.getGameMode().getValue();
         if (gamemode == 1 || gamemode == 3) {
            return unsafeTeleport(location);
         } else {
            return safeTeleport(location);
         }
      }
      return null;
   }

   @Override
   public UnsafeTeleportQueue<TeleportUser> unsafeTeleport(UnsafeTeleportQueue<TeleportUser> teleportQueue, Location location) {
      teleportQueue.queueTeleport(this, location);
      return teleportQueue;
   }

   @Override
   public SafeTeleportQueue<TeleportUser> safeTeleport(SafeTeleportQueue<TeleportUser> teleportQueue, Location location) {
      teleportQueue.queueTeleport(this, location);
      return teleportQueue;
   }

   /**
    * Teleport using the vanilla teleport function
    *
    * @param location location to teleport to
    */
   public void vanillaTeleport(Location location) {
      if (sender instanceof Player) {
         ((Player) sender).teleport(location);
      }
   }

   /**
    * Teleport the player.
    *
    * @param location location to teleport to
    */
   @Override
   public UnsafeTeleportQueue<TeleportUser> unsafeTeleport(Location location) {
      return unsafeTeleport(TeleportQueues.UNSAFE_CORE_PLAYER_TELEPORT_QUEUE, location);
   }

   @Override
   public SafeTeleportQueue<TeleportUser> safeTeleport(Location location) {
     return safeTeleport(TeleportQueues.SAFE_CORE_PLAYER_TELEPORT_QUEUE, location);
   }


}
