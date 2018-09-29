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

package robmart.rpgmode.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.client.network.SyncPlayerMana;
import robmart.rpgmode.server.network.OpenGuiMessage;

/**
 * @author Robmart
 */
public class PacketDispatcher {
    // a simple counter will allow us to get rid of 'magic' numbers used during packet registration
    private static byte packetId = 0;

    /**
     * The SimpleNetworkWrapper instance is used both to register and send packets.
     * Since I will be adding wrapper methods, this field is private, but you should
     * make it public if you plan on using it directly.
     */
    private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    /**
     * Call this during pre-init or loading and register all of your packets (messages) here
     */
    public static final void registerPackets() {
        // Packets handled on CLIENT
        registerMessage(SyncPlayerMana.class);

        // Packets handled on SERVER
        registerMessage(OpenGuiMessage.class);

        // Packets handled on both
        registerMessage(SyncPlayerAttributes.class);
    }

    /**
     * Registers an {@link AbstractMessage} to the appropriate side(s)
     */
    private static final <T extends AbstractMessage<T> & IMessageHandler<T, IMessage>> void registerMessage(
            Class<T> clazz) {
        if (AbstractMessage.AbstractClientMessage.class.isAssignableFrom(clazz)) {
            PacketDispatcher.dispatcher.registerMessage(clazz, clazz, packetId++, Side.CLIENT);
        }
        else if (AbstractMessage.AbstractServerMessage.class.isAssignableFrom(clazz)) {
            PacketDispatcher.dispatcher.registerMessage(clazz, clazz, packetId++, Side.SERVER);
        }
        else {
            PacketDispatcher.dispatcher.registerMessage(clazz, clazz, packetId++, Side.CLIENT);
            PacketDispatcher.dispatcher.registerMessage(clazz, clazz, packetId++, Side.SERVER);
        }
    }

    //========================================================//
    // The following methods are the 'wrapper' methods; again,
    // this just makes sending a message slightly more compact
    // and is purely a matter of stylistic preference
    //========================================================//

    /**
     * Send this message to the specified player's client-side counterpart.
     * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
     */
    public static final void sendTo(IMessage message, EntityPlayerMP player) {
        PacketDispatcher.dispatcher.sendTo(message, player);
    }

    /**
     * Send this message to everyone.
     * See {@link SimpleNetworkWrapper#sendToAll(IMessage)}
     */
    public static void sendToAll(IMessage message) {
        PacketDispatcher.dispatcher.sendToAll(message);
    }

    /**
     * Send this message to everyone within a certain range of a point.
     * See {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
     */
    public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
        PacketDispatcher.dispatcher.sendToAllAround(message, point);
    }

    /**
     * Sends a message to everyone within a certain range of the player provided.
     * Shortcut to {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
     */
    public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
        PacketDispatcher
                .sendToAllAround(message, player.world.provider.getDimension(), player.posX, player.posY, player.posZ,
                                 range);
    }

    /**
     * Sends a message to everyone within a certain range of the coordinates in the same dimension.
     * Shortcut to {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
     */
    public static final void sendToAllAround(
            IMessage message, int dimension, double x, double y, double z, double range) {
        PacketDispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }

    /**
     * Send this message to everyone within the supplied dimension.
     * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
     */
    public static final void sendToDimension(IMessage message, int dimensionId) {
        PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
    }

    /**
     * Send this message to the server.
     * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
     */
    public static final void sendToServer(IMessage message) {
        PacketDispatcher.dispatcher.sendToServer(message);
    }
}