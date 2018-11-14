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

package robmart.rpgmode.common.capability.chunklevel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import robmart.rpgmode.api.capability.chunklevel.IChunkLevel;
import robmart.rpgmode.client.network.SyncChunkLevel;
import robmart.rpgmode.common.network.PacketDispatcher;

import java.util.Random;

/**
 * @author Robmart
 * Created on 11/14/2018
 */
public class ChunkLevelImplementation implements IChunkLevel {
    private Chunk chunk;
    private int   level = 0;

    public ChunkLevelImplementation() {
        this.chunk = null;
    }

    public ChunkLevelImplementation(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public void calculateLevel() {
        World world = this.chunk.getWorld();
        ChunkPos chunkPos = this.chunk.getPos();
        int totalLevel = 0;
        int chunksWithLevels = 0;

        if (getLevel() > 0)
            return;

        for (int i = 0; i < 9; i++) {
            IChunkLevel chunkLevel =
                    ChunkLevelCapability.getChunkLevel(world.getChunkFromChunkCoords(
                            (chunkPos.x - 1) + i / 3,
                            (chunkPos.z - 1) + i % 3));

            if (chunkLevel == null || i == 5)
                continue;

            if (chunkLevel.getLevel() != 0) {
                totalLevel += chunkLevel.getLevel();
                chunksWithLevels++;
            }
        }

        if (chunksWithLevels > 0) {
            this.setLevel(Math.round(totalLevel / (float) chunksWithLevels));
        }
        else {
            this.setLevel(1);
        }

        if (new Random().nextInt(14) == 0)
            setLevel(getLevel() + 1);
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;

        synchronise();
    }

    @Override
    public void synchronise() {
        if (!this.chunk.getWorld().isRemote)
            PacketDispatcher.sendToAll(new SyncChunkLevel(this.chunk));
    }

    @Override
    public NBTTagCompound saveNBTData() {
        return saveNBTData(new NBTTagCompound());
    }

    @Override
    public NBTTagCompound saveNBTData(NBTTagCompound compound) {
        compound.setInteger("level", getLevel());
        compound.setInteger("posX", this.chunk.getPos().x);
        compound.setInteger("posZ", this.chunk.getPos().z);
        return compound;
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        this.level = compound.getInteger("level");
    }
}
