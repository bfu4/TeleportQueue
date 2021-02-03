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


package com.github.bfu4.teleportqueue.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.permissions.ServerOperator;
import com.github.bfu4.teleportqueue.abs.TeleportUser;

public class TeleportationAction<T extends ServerOperator> {

   private final T entity;
   private final Location location;

   public TeleportationAction(T entity, Location location) {
      this.entity = entity;
      this.location = location;
   }

   public T getEntity() { return this.entity; }

   public Location getLocation() { return this.location; }

   /**
    * Teleport the entity
    */
   public void teleport() {
      synchronized (entity) {
         if (entity instanceof Entity) {
            Entity e = (Entity) entity;
            e.teleport(location);
         } else if (entity instanceof TeleportUser) {
            TeleportUser player = (TeleportUser) entity;
            player.vanillaTeleport(location);
         }
      }
   }

}