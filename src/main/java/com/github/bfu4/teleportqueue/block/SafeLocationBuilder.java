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


package com.github.bfu4.teleportqueue.block;

import org.bukkit.Location;
import org.bukkit.World;

public class SafeLocationBuilder {

   private final World world;
   private final Location initialLocation;

   private Location newLocation;
   private final int x;
   private int y;
   private final double z;


   public SafeLocationBuilder(Location location) {
      this.initialLocation = location;
      this.newLocation = location;
      this.world = location.getWorld();
      this.x = location.getBlockX();
      this.y = location.getBlockY();
      this.z = location.getBlockZ();
   }

   /**
    * Check if a location is safe
    *
    * @param location location to check
    * @return whether the location is check
    */
   private boolean isSafe(Location location) {
      return BlockUtil.isSafe(location.getBlock()) && BlockUtil.isSafe(new Location(world, x, y - 1, z).getBlock());
   }

   /**
    * Get a safe Y value
    *
    * @param location location to get a safe Y value of
    * @return a location with a safe Y value
    * @throws SafeLocationNotFoundException if a safe Y for the location cannot be found
    */
   private Location getSafeY(Location location) throws SafeLocationNotFoundException {
      if (!isSafe(location)) {
         Location update;
         if (y == 0) throw new SafeLocationNotFoundException(location);
         update = new Location(world, this.x, this.y - 1, this.z);
         boolean nextBlockAir = BlockUtil.isAir(update.getBlock());
         boolean thisBlockAir = BlockUtil.isAir(newLocation.getBlock());
         if (!nextBlockAir && thisBlockAir) {
            return this.newLocation;
         } else if (nextBlockAir && thisBlockAir) {
            this.y = this.y - 1;
            newLocation = new Location(world, this.x, this.y, this.z);
            getSafeY(newLocation);
         }
      }
      return newLocation;
   }

   /**
    * Build a safe location
    *
    * @return a safe location
    */
   public Location build() {
      try {
         return getSafeY(initialLocation);
      } catch (SafeLocationNotFoundException e) {
         return null;
      }
   }

}
