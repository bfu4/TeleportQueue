/**
 * MIT License
 *
 * Copyright (c) 2021 bfu4
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.bfu4.teleportqueue;

import com.github.bfu4.teleportqueue.abs.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import com.github.bfu4.teleportqueue.abs.StandalonePlugin;
import com.github.bfu4.teleportqueue.command.TeleportQueuePluginCommand;
import com.github.bfu4.teleportqueue.teleport.listener.EssentialsTeleportListener;
import com.github.bfu4.teleportqueue.teleport.listener.TeleportEntityEventListener;
import com.github.bfu4.teleportqueue.teleport.queue.Queue;
import com.github.bfu4.teleportqueue.teleport.queue.TeleportQueues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TeleportQueuePlugin extends StandalonePlugin {

   public static final String COLORED_PREFIX = "&8(&3TELEPORT&7QUEUE&8)&r";
   private static TeleportQueuePlugin INSTANCE;

   private CommandManager manager;
   private static final HashMap<String, Listener> listeners = new HashMap<>();
   private static final List<Queue<?>> teleportQueues = new ArrayList<>(Arrays.asList(TeleportQueues.SAFE_CORE_PLAYER_TELEPORT_QUEUE, TeleportQueues.UNSAFE_CORE_PLAYER_TELEPORT_QUEUE));

   @Override
   public void onEnable() {
      INSTANCE = this;
      manager = new CommandManager(this);

      if (essentials()) {
         registerListener("EssentialsTeleportListener", new EssentialsTeleportListener());
      }

      registerListener("TeleportEntityEventListener", new TeleportEntityEventListener());

      manager.register(new TeleportQueuePluginCommand("teleportqueue", this));

   }

   @Override
   public void onDisable() {

   }

   public boolean essentials() {
      return Bukkit.getPluginManager().getPlugin("Essentials") != null;
   }

   @Override
   public CommandManager getManager() {
      return manager;
   }

   @Override
   public List<Queue<?>> getTeleportQueues() {
      return teleportQueues;
   }

   /**
    * Get the listener list
    *
    * @return list of listeners
    */
   public static HashMap<String, Listener> getListenerList() { return listeners; }

   /**
    * Get listener instance by name
    *
    * @param listenerName name of the listener
    * @return listener by name, null if it doesn't exist
    */
   public Listener getListener(String listenerName) {
      return listeners.get(listenerName);
   }

   /**
    * Register a listener
    *
    * @param listenerName name of the listener
    * @param listener listener to register
    */
   public void registerListener(String listenerName, Listener listener) {
      Bukkit.getPluginManager().registerEvents(listener, this);
      listeners.put(listenerName, listener);
   }

   /**
    * Get a list of {@link RegisteredListener}s registered to the plugin
    *
    * @return list of registered listeners
    */
   public ArrayList<RegisteredListener> getRegisteredListeners() {
      return HandlerList.getRegisteredListeners(this);
   }

   /**
    * Get a registered listener by listener instance
    *
    * @param instance instance of listener to get the {@link RegisteredListener} of
    * @return registered listener in respect to the instance
    */
   public RegisteredListener getRegisteredListener(Listener instance) {
      return getRegisteredListeners().stream().filter(
         listener -> listener.getListener().equals(instance)
      ).findFirst().orElse(null);
   }

   /**
    * Get a registered listener by name registered as
    *
    * @param name name of the listener
    * @return registered listener in respect to the name
    */
   public RegisteredListener getRegisteredListener(String name) {
      return getRegisteredListener(getListener(name));
   }

   /**
    * Deregister a listener
    *
    * @param listenerClass class of the listener to deregister
    * @param plugin plugin that the listener is registered to.
    */
   public void deregisterListener(Class<? extends Listener> listenerClass, Plugin plugin) {
      HandlerList.getRegisteredListeners(plugin).stream().forEach(listener -> {
         if (listener.getListener().getClass().equals(listenerClass)) {
            HandlerList.unregisterAll(listener.getListener());
         }
      });
   }

   public static void runTaskLater(Runnable runnable, long later) {
      Bukkit.getScheduler().runTaskLater(INSTANCE, runnable, later);
   }

}
