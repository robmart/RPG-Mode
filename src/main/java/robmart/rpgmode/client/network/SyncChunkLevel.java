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

package robmart.rpgmode.client.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import robmart.rpgmode.common.capability.chunklevel.ChunkLevelCapability;
import robmart.rpgmode.common.network.AbstractMessage;

import java.io.IOException;

/**
 * @author Robmart
 * Created on 11/14/2018
 */
public class SyncChunkLevel extends AbstractMessage.AbstractClientMessage<SyncChunkLevel> {
    private NBTTagCompound data;

    public SyncChunkLevel() {
    }

    public SyncChunkLevel(Chunk chunk) {
        data = new NBTTagCompound();
        ChunkLevelCapability.getChunkLevel(chunk).saveNBTData(data);
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        data = buffer.readCompoundTag();
    }

    @Override
    protected void write(PacketBuffer buffer) {
        buffer.writeCompoundTag(data);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        ChunkLevelCapability.getChunkLevel(player.world.getChunkFromChunkCoords(
                data.getInteger("posX"),
                data.getInteger("posZ")))
                            .loadNBTData(data);
    }
}
