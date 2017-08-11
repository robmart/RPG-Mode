package robmart.rpgmode.client.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import robmart.rpgmode.common.capability.mana.ManaCapability;
import robmart.rpgmode.common.network.AbstractMessage;

import java.io.IOException;

/**
 * Created by Robmart.
 *
 *   This software is a modification for the game Minecraft, intended to give the game RPG elements.
 *   Copyright (C) 2017 Robmart
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class SyncPlayerMana extends AbstractMessage.AbstractClientMessage<SyncPlayerMana> {
    private NBTTagCompound data;

    public SyncPlayerMana() {}

    public SyncPlayerMana(EntityPlayer player) {
        data = new NBTTagCompound();
        ManaCapability.get(player).saveNBTData(data);
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        data = buffer.readCompoundTag();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeCompoundTag(data);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        ManaCapability.get(player).loadNBTData(data);
    }
}
