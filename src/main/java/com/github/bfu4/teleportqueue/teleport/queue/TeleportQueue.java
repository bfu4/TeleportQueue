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

import com.github.bfu4.teleportqueue.abs.Unsafe;
import com.github.bfu4.teleportqueue.teleport.TeleportationAction;
import org.bukkit.Location;
import org.bukkit.permissions.ServerOperator;
import com.github.bfu4.teleportqueue.teleport.except.UnsafeTeleportException;

public interface TeleportQueue<T extends ServerOperator> extends Queue<T> {

   /**
    * Increase the size of the queue.
    *
    * @param newSize new size of the queue
    */
   void increaseQueueSize(int newSize);

   /**
    * Insert an entity into the teleportation queue.
    *
    * @param entity entity to teleport
    * @throws UnsafeTeleportException if the location to teleport is unsafe.
    */
   void queueTeleport(T entity, Location location) throws UnsafeTeleportException;

   /**
    * Check if an entity is in the instances teleport queue.
    *
    * @param entity entity to check
    * @return if the entity is in the teleport queue
    */
   boolean isQueued(T entity);

   /**
    * Return whether the queue is unsafe.
    *
    * @return if the queue is unsafe
    */
   default boolean isUnsafe() { return getClass().getAnnotation(Unsafe.class) != null; }

   @SuppressWarnings("unchecked")
   default TeleportationAction<T>[] getEmptyTeleportationActionArray() {
      return (TeleportationAction<T>[]) new TeleportationAction[] {new TeleportationAction<>(null, null)};
   }

}
