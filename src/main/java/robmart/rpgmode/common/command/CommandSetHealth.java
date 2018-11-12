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

package robmart.rpgmode.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.health.MaxHealthCapability;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Robmart
 */
public class CommandSetHealth extends CommandBase {
    private static final String NAME             = "sethealth";
    private static final int    PERMISSION_LEVEL = 2;
    private static final String COMMAND_USAGE    = "commands." + Reference.MOD_ID.toLowerCase() + ".sethealth.usage";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return COMMAND_USAGE;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player;
        String[] info;

        if (args.length > 3 || args.length == 1 || args.length == 0)
            throw new WrongUsageException(COMMAND_USAGE);
        else if (args.length == 3)
            player = getPlayer(server, sender, args[2]);
        else
            player = getCommandSenderAsPlayer(sender);

        setHealth(args[0], Float.valueOf(args[1]), player);
        info = getInfoFromString(args[0], player);

        if (info == null)
            throw new WrongUsageException(COMMAND_USAGE);

        notifyCommandListener(player, this, "commands.rpgmode.sethealth.success1", info[0], info[1]);

        if (!player.getName().equalsIgnoreCase(getCommandSenderAsPlayer(sender).getName()))
            notifyCommandListener(
                    sender, this, "commands.rpgmode.sethealth.success2", player.getName(), info[0], info[1]);
    }

    public int getPermissionLevel() {
        return PERMISSION_LEVEL;
    }

    private String[] getInfoFromString(String string, EntityPlayer player) {
        switch (string.toLowerCase()) {
            case "h":
                return getInfoFromString("health", player);
            case "maxhealth":
                return new String[] {"max health", floatToString(player.getMaxHealth())};
            case "max":
                return getInfoFromString("maxhealth", player);
            default:
                return new String[] {"health", floatToString(player.getHealth())};
        }
    }

    private void setHealth(String string, float amount, EntityPlayer player) {
        switch (string.toLowerCase()) {
            case "h":
                setHealth("health", amount, player);
                break;
            case "maxhealth":
                MaxHealthCapability.getMaxHealth(player).setBonusMaxHealth(amount);
                break;
            case "max":
                setHealth("maxhealth", amount, player);
                break;
            default:
                player.setHealth(amount);
                break;
        }
    }

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    @Override
    public List<String> getTabCompletions(
            MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "health", "maxhealth");
        return (args.length == 3 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) :
                Collections.emptyList());
    }
}
