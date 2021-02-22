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

import com.github.bfu4.teleportqueue.abs.LimitedList;
import com.github.bfu4.teleportqueue.abs.Unsafe;
import com.github.bfu4.teleportqueue.teleport.TeleportEntityEvent;
import com.github.bfu4.teleportqueue.teleport.TeleportationAction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.permissions.ServerOperator;

import java.util.Arrays;
import java.util.List;

@Unsafe
public class UnsafeTeleportQueue<T extends ServerOperator> implements TeleportQueue<T> {

   private String identifier;
   private final LimitedList<TeleportationAction<T>> entityQueue;

   /**
    * Create private constructor to disallow creation of instances without our functions.
    */
   private UnsafeTeleportQueue() { entityQueue = new LimitedList<>(1); }

   /**
    * Create an unsafe teleport queue with the given identifier
    *
    * @param identifier name of the queue to register
    */
   protected UnsafeTeleportQueue(String identifier, int maxQueues) {
      this.identifier = identifier;
      this.entityQueue = new LimitedList<>(maxQueues);
   }

   /**
    * Create a new teleport queue
    *
    * @param identifier name of the queue
    * @param maxQueues max amount of queues that the queue can hold
    * @param <S> type restriction for the queue
    * @return a queue if the queue could be instantiated
    */
   public static <S extends ServerOperator> UnsafeTeleportQueue<S> create(String identifier, int maxQueues) {
      if (TeleportQueues.QUEUES.stream().anyMatch(queue -> queue.getIdentifier().equalsIgnoreCase(identifier))) {
         throw new InvalidQueueException("A queue with the name " + identifier + " already exists!");
      }
      UnsafeTeleportQueue<S> queue =  new UnsafeTeleportQueue<>(identifier, maxQueues);
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

   /**
    * Get the identifier of the queue
    *
    * @return queue identifier
    */
   public String getIdentifier() { return this.identifier; }

   @Override
   public List<TeleportationAction<T>> getQueues() {
      return entityQueue.asList();
   }

   @Override
   public void remove(TeleportationAction<T> action) {
      synchronized (entityQueue) {
         entityQueue.remove(action);
      }
   }

   @Override
   public TeleportationAction<T> getFromQueues(ServerOperator parent) {
      synchronized (entityQueue) {
         return getQueues().stream().filter(queue -> queue.getEntity().equals(parent)).findFirst().orElse(null);
      }
   }


   @Override
   public void queueTeleport(T entity, Location location) {
      synchronized (entityQueue) {
         entityQueue.add(new TeleportationAction<>(entity, location));
         Bukkit.getPluginManager().callEvent(new TeleportEntityEvent(entity, location));
      }
   }

   @Override
   public boolean isQueued(T entity) {
      synchronized (entityQueue) {
         return getFromQueues(entity) != null;
      }
   }

}
