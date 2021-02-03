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
import com.github.bfu4.teleportqueue.abs.command.Usage;

@Permission("teleportqueue")
@Usage(TeleportQueuePlugin.COLORED_PREFIX + " &7/tpq <tp/info/dbtp>")
public class TeleportQueuePluginCommand extends CommandBase {

   public TeleportQueuePluginCommand(String commandIdentifier, TeleportQueuePlugin plugin) {
      super(commandIdentifier, plugin);

      addSubcommand("info", new InfoCommand(plugin));
      addSubcommand("tp", new TeleportSubcommand(plugin));
      addSubcommand("dbtp", new DebugTeleportCommand(plugin));
   }

   @Override
   public void execute(TeleportUser user, String[] args) {
      if (args.length == 0) {
         user.sendMessage(getUsage());
      } else {
         if (getSubcommands().containsKey(args[0])) {
            getSubcommands().get(args[0]).execute(user, args);
         } else {
            user.sendMessage(getUsage());
         }
      }
   }

}
