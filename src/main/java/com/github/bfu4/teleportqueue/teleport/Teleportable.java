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
import org.bukkit.permissions.ServerOperator;
import com.github.bfu4.teleportqueue.teleport.queue.SafeTeleportQueue;
import com.github.bfu4.teleportqueue.teleport.queue.UnsafeTeleportQueue;

public interface Teleportable<T extends ServerOperator> {

   /**
    * Teleport entity unsafely to a location respective to specific queue
    *
    * @param teleportQueue queue to add teleport entity to
    * @param location location to teleport to
    * @return queue instance
    */
   UnsafeTeleportQueue<T> unsafeTeleport(UnsafeTeleportQueue<T> teleportQueue, Location location);

   /**
    * Teleport entity safely to a location respective to specific queue
    *
    * @param teleportQueue queue to add teleport entity to
    * @param location location to teleport to
    * @return queue instance
    */
   SafeTeleportQueue<T> safeTeleport(SafeTeleportQueue<T> teleportQueue, Location location);

   /**
    * Teleport entity unsafely using default queue
    *
    * @param location location to teleport to
    * @return queue instance
    */
   UnsafeTeleportQueue<T> unsafeTeleport(Location location);

   /**
    * Teleport entity safely using default queue
    *
    * @param location location to teleport to
    * @return queue instance
    */
   SafeTeleportQueue<T> safeTeleport(Location location);

}
