/*
 * <p>
 * This software is a modification for the game Minecraft, intended to give the game RPG elements.
 * Copyright (C) 2018 Robmart
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package robmart.rpgmode.common.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeHellDecorator;
import robmart.rpgmode.api.block.RPGBlocks;
import robmart.rpgmode.common.helper.WorldHelper;

import java.util.Random;

/**
 * @author Robmart
 */
public class BiomeHellDecoratorWrapper extends BiomeHellDecorator {
    private BiomeDecorator wrappedDecorator;

    public BiomeHellDecoratorWrapper(BiomeDecorator decorator) {
        this.wrappedDecorator = decorator;
    }

    @Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
        int flowerPatchSize = 6;
        int flowerQuantity = 2;
        int flowerPatchChance = 10;
        int flowerDensity = 2;

        wrappedDecorator.decorate(worldIn, random, biome, pos);

        int dist = Math.min(8, Math.max(1, flowerPatchSize));
        for (int i = 0; i < flowerQuantity; i++) {
            if (random.nextInt(flowerPatchChance) == 0) {
                int x = pos.getX() + random.nextInt(16) + 8;
                int z = pos.getZ() + random.nextInt(16) + 8;

                for (int j = 0; j < flowerDensity * flowerPatchChance; j++) {
                    int x1 = x + random.nextInt(dist * 2) - dist;

                    BlockPos highest = WorldHelper.getHighestBlock(worldIn, pos, Blocks.SOUL_SAND);
                    int y = highest != null ? highest.getY() : 0;

                    int z1 = z + random.nextInt(dist * 2) - dist;
                    BlockPos pos2 = new BlockPos(x1, y, z1);

                    if (worldIn.isAirBlock(pos2) && RPGBlocks.HELL_FLOWER.canPlaceBlockAt(worldIn, pos2)) {
                        worldIn.setBlockState(pos2, RPGBlocks.HELL_FLOWER.getDefaultState());
                    }
                }
            }
        }
    }
}
