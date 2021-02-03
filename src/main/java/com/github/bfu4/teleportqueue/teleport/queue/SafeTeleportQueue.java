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


package com.github.bfu4.teleportqueue.teleport.queue;

import com.github.bfu4.teleportqueue.TeleportQueuePlugin;
import com.github.bfu4.teleportqueue.abs.LimitedList;
import com.github.bfu4.teleportqueue.abs.TeleportUser;
import com.github.bfu4.teleportqueue.block.SafeLocationBuilder;
import com.github.bfu4.teleportqueue.teleport.TeleportEntityEvent;
import com.github.bfu4.teleportqueue.teleport.TeleportationAction;
import com.github.bfu4.teleportqueue.teleport.except.UnsafeTeleportException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.ServerOperator;

import java.util.Arrays;
import java.util.List;

public class SafeTeleportQueue<T extends ServerOperator> implements TeleportQueue<T> {

   private String identifier;
   private final LimitedList<TeleportationAction<T>> entityQueue;

   /**
    * Create private constructor to disallow creation of instances without our functions.
    */
   private SafeTeleportQueue() { entityQueue = new LimitedList<>(1); }

   /**
    * Create an safe teleport queue with the given identifier
    *
    * @param identifier name of the queue to register
    */
   protected SafeTeleportQueue(String identifier, int maxQueues) {
      this.identifier = identifier;
      this.entityQueue = new LimitedList<>(maxQueues);
   }

   /**
    * Create a new teleport queue
    *
    * @param identifier name of the queue
    * @param maxQueues max amount of queues that the queue can hold
    * @param <E> type restriction for the queue
    * @return a queue if the queue could be instantiated
    */
   public static <E extends ServerOperator> SafeTeleportQueue<E> create(String identifier, int maxQueues) {
      if (TeleportQueues.QUEUES.stream().anyMatch(queue -> queue.getIdentifier().equalsIgnoreCase(identifier))) {
         throw new InvalidQueueException("A queue with the name " + identifier + " already exists!");
      }
      SafeTeleportQueue<E> queue = new SafeTeleportQueue<>(identifier, maxQueues);
      TeleportQueues.register(queue);
      return queue;
   }


   /**
    * Increase the size of the queue.
    *
    * @param newSize new size of the queue
    */
   public void increaseQueueSize(int newSize) {
      this.entityQueue.setMaxSize(newSize);
   }

   @Override
   public void queueTeleport(T entity, Location location) throws UnsafeTeleportException {
      synchronized (entityQueue) {
         Location safeLocation =  new SafeLocationBuilder(location).build();
         if (safeLocation != null) {
            entityQueue.add(new TeleportationAction<>(entity, safeLocation));
            Bukkit.getPluginManager().callEvent(new TeleportEntityEvent(entity, safeLocation));
         } else {
            if (entity instanceof TeleportUser) {
               ((TeleportUser) entity).sendFormattedMessage("&7Could not teleport to an unsafe location!");
            } else if (entity instanceof CommandSender) {
               ((CommandSender) entity).sendMessage(TeleportQueuePlugin.COLORED_PREFIX + "&7 Could not teleport to an unsafe location!");
            }
         }
      }
   }

   /**
    * Check if an entity is queued
    *
    * @param entity entity to check
    * @return whether the entity is queued
    */
   @Override
   public boolean isQueued(T entity) {
      synchronized (entityQueue) {
         return getFromQueues(entity) != null;
      }
   }

   /**
    * Get the identifier of the queue
    *
    * @return queue identifier
    */
   public String getIdentifier() { return this.identifier; }

   @Override
   public List<TeleportationAction<T>> getQueues() {
      return Arrays.asList(entityQueue.toArray());
   }

   @Override
   public void remove(TeleportationAction<T> action) {
      synchronized (entityQueue) {
         entityQueue.remove(action);
      }
   }

   @Override
   public TeleportationAction<T> getFromQueues(ServerOperator parent) {
      return getQueues().stream().filter(queue -> queue.getEntity().equals(parent)).findFirst().orElse(null);
   }

}