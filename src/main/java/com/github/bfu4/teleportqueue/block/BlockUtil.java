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

import com.cryptomorin.xseries.XBlock;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.Collection;

/**
 * BlockUtil - Simple block utilities, all from {@link XBlock}
 *
 * @apiNote this would obviously be inefficient, however, is optimal for aesthetic.
 */

@SuppressWarnings("all")
public final class BlockUtil {

   /**
    * Check if a block is of this type of material
    *
    * @param block
    * @param material
    * @return
    */
   public static boolean isType(Block block, XMaterial material) { return XBlock.isType(block, material); }

   /**
    * Check if the block is similar to the material
    *
    * @param block
    * @param material
    * @return
    */
   public static boolean isSimilar(Block block, XMaterial material) { return XBlock.isSimilar(block, material); }

   /**
    * Check if a block is air
    *
    * @param block
    * @return
    */
   public static boolean isAir(Block block) { return XBlock.isAir(block.getType()); }

   /**
    * Check if the block is powered
    *
    * @param block
    * @return
    */
   public static boolean isPowered(Block block) { return XBlock.isPowered(block); }

   /**
    * Set a block to be powered
    *
    * @param block
    * @param isPowered
    */
   public static void setPowered(Block block, boolean isPowered) { XBlock.setPowered(block, isPowered); }

   /**
    * Check is a block is open
    *
    * @param block
    */
   public static void isOpen(Block block) { XBlock.isOpen(block); }

   /**
    * Set a block to be open
    *
    * @param block
    * @param isOpen
    */
   public static void setOpen(Block block, boolean isOpen) { XBlock.setOpened(block, isOpen);}

   /**
    * Get the rotation of a block
    *
    * @param block
    * @return
    */
   public static BlockFace getRotation(Block block) { return XBlock.getRotation(block); }

   /**
    * Set the rotation of a block
    *
    * @param block
    * @param facing
    */
   public static void setRotation(Block block, BlockFace facing) { XBlock.setRotation(block, facing); }

   /**
    * Set an enderpearl on a frame
    *
    * @param endPortalFrame
    * @param eye if the eye is to be set on the frame
    */
   public static void setEnderPearlOnFrame(Block endPortalFrame, boolean eye) { XBlock.setEnderPearlOnFrame(endPortalFrame, eye); }

   /**
    * Set a block to be wooden
    *
    * @param block
    * @param species
    * @return
    */
   public static boolean setWooden(Block block, XMaterial species) { return XBlock.setWooden(block, species); }

   /**
    * Set the amount of cake slices
    *
    * @param block
    * @param amount
    */
   public static void setCakeSlices(Block block, int amount) { XBlock.setCakeSlices(block, amount); }

   /**
    * Check if a block is any of the given blocks
    *
    * @param block
    * @param blocks
    * @return
    */
   public static boolean isOneOf(Block block, Collection<String> blocks) { return XBlock.isOneOf(block, blocks); }

   /**
    * Check if a block is lava
    *
    * @param material
    * @return
    */
   public static boolean isLava(Material material) { return XBlock.isLava(material); }

   /**
    * Check if a block is water
    *
    * @param material
    * @return
    */
   public static boolean isWater(Material material) { return XBlock.isWater(material); }

   /**
    * Check if the water is stationary
    *
    * @param block
    */
   public static boolean isWaterStationary(Block block) { return XBlock.isWaterStationary(block); }

   /**
    * Get the fluid level of a block
    *
    * @param block
    * @return
    */
   public static int getFluidLevel(Block block) { return XBlock.getFluidLevel(block); }

   /**
    * Set the fluid level of a block
    *
    * @param block
    * @param level
    * @return
    */
   public static boolean setFluidLevel(Block block, int level) { return XBlock.setFluidLevel(block, level); }

   /**
    * Set the color of a block
    *
    * @param block
    * @param color
    * @return
    */
   public static boolean setColor(Block block, DyeColor color) { return XBlock.setColor(block, color); }

   /**
    * Set the age of a block
    *
    * @param block
    * @param age
    */
   public static void setAge(Block block, int age) { XBlock.setAge(block, age); }

   /**
    * Get the age of a block
    *
    * @param block
    * @return age
    */
   public static int getAge(Block block) { return XBlock.getAge(block); }

   /**
    * Set the direction of a block
    *
    * @param block
    * @param facing direction the block is facing
    * @return
    */
   public static boolean setDirection(Block block, BlockFace facing) { return XBlock.setDirection(block, facing); }

   /**
    * Get the direction of a block
    *
    * @param block
    * @return direction
    */
   public static BlockFace getDirection(Block block) { return XBlock.getDirection(block); }

   /**
    * Check if a block is lit
    *
    * @param block
    * @return
    */
   public static boolean isLit(Block block) { return XBlock.isLit(block); }

   /**
    * Check if a block is a container
    *
    * @param block
    * @return
    */
   public static boolean isContainer(Block block) { return XBlock.isContainer(block); }

   /**
    * Set a block to be lit
    *
    * @param block
    * @param lit
    */
   public static void setLit(Block block, boolean lit) { XBlock.setLit(block, lit); }

   /**
    * Check if a material is a crop
    *
    * @param material
    * @return
    */
   public static boolean isCrop(XMaterial material) {
      return XBlock.isCrop(material);
   }

   /**
    * Check if a material is dangerous
    *
    * @param material
    * @return
    */
   public static boolean isDangerous(XMaterial material) {
      return XBlock.isDangerous(material);
   }

   /**
    * Check if a block is teleport safe
    *
    * @param block block to check
    */
   public static boolean isSafe(Block block) {
      Material material = block.getType();
      boolean waterUnsafe = isWater(material) && !isWaterStationary(block);
      return !isLava(material) && !waterUnsafe && !isAir(block);
   }

}