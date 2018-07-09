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
import robmart.rpgmode.common.capability.health.MaxHealthCapability;
import robmart.rpgmode.common.reference.Reference;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Robmart
 */
public class CommandSetHealth extends CommandBase {
    public String name = "sethealth";
    public int permissionLevel = 2;
    String commandUsage = "commands." + Reference.MOD_ID.toLowerCase() + ".sethealth.usage";

    @Override
    public String getName() {
        return name;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return commandUsage;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player;
        String[] info;

        if (args.length > 3 || args.length == 1 || args.length == 0)
            throw new WrongUsageException(commandUsage);
        else if (args.length == 3)
            player = getPlayer(server, sender, args[2]);
        else
            player = getCommandSenderAsPlayer(sender);

        setHealth(args[0], Float.valueOf(args[1]), player);
        info = getInfoFromString(args[0], player);

        if (info == null)
            throw new WrongUsageException(commandUsage);

        notifyCommandListener(player, this, "commands.rpgmode.sethealth.success1", info[0], info[1]);

        if (!player.getName().equalsIgnoreCase(getCommandSenderAsPlayer(sender).getName()))
            notifyCommandListener(sender, this, "commands.rpgmode.sethealth.success2", player.getName(), info[0], info[1]);
    }

    private String[] getInfoFromString(String string, EntityPlayer player) {
        switch (string.toLowerCase()) {
            case "health":
                return new String[]{"health", floatToString(player.getHealth())};
            case "h":
                return getInfoFromString("health", player);
            case "maxhealth":
                return new String[]{"max health", floatToString(player.getMaxHealth())};
            case "max":
                return getInfoFromString("maxhealth", player);
        }
        return null;
    }

    private void setHealth(String string, float amount, EntityPlayer player) {
        switch (string.toLowerCase()){
            case "health":
                player.setHealth(amount);
                break;
            case "h":
                setHealth("health", amount, player);
                break;
            case "maxhealth":
                MaxHealthCapability.get(player).setBonusMaxHealth(amount);
                break;
            case "max":
                setHealth("maxhealth", amount, player);
        }
    }

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "health", "maxhealth") : (args.length == 3 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList());
    }
}
