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


package com.github.bfu4.teleportqueue.teleport.listener;

import com.github.bfu4.teleportqueue.TeleportQueuePlugin;
import com.github.bfu4.teleportqueue.teleport.TeleportEntityEvent;
import com.github.bfu4.teleportqueue.teleport.TeleportationAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.permissions.ServerOperator;
import com.github.bfu4.teleportqueue.teleport.queue.Queue;
import com.github.bfu4.teleportqueue.teleport.queue.TeleportQueues;

/**
 * TeleportEntityEventListener - Listener for the {@link TeleportEntityEvent}
 *
 * @author bfu4
 * @since 31/01/2021 @ 21.07
 */
public class TeleportEntityEventListener implements Listener {

   @SuppressWarnings("unchecked")
   @EventHandler
   public <T extends ServerOperator> void onEntityTeleport(TeleportEntityEvent event) {
      Queue<T> queueToUpdate = null;
      TeleportationAction<T> action = null;
      for (Queue<?> queue : TeleportQueues.QUEUES) {
         TeleportationAction<T> a = (TeleportationAction<T>) queue.getFromQueues(event.getEntity());
         if (a != null) {
            queueToUpdate = (Queue<T>) queue;
            action = a;
            TeleportQueuePlugin.runTaskLater(a::teleport, 2L);
            break;
         }
      }
      if (queueToUpdate != null) queueToUpdate.remove(action);
   }

}
