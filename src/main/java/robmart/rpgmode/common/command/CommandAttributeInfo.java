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
import robmart.rpgmode.api.capability.attribute.IAttribute;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Robmart
 */
public class CommandAttributeInfo extends CommandBase {
    protected static final String NAME             = "attributeinfo";
    private static final   int    PERMISSION_LEVEL = 1;
    private final          String commandUsage     = "commands." + Reference.MOD_ID.toLowerCase() +
                                                     ".attributeinfo.usage";

    @Override
    public String getName() {
        return NAME;
    }

    public int getPermissionLevel() {
        return PERMISSION_LEVEL;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return commandUsage;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player;
        IAttribute attribute;
        String[] info;

        if (args.length > 2 || args.length == 0)
            throw new WrongUsageException(commandUsage);
        else if (args.length == 2)
            player = getPlayer(server, sender, args[1]);
        else
            player = getCommandSenderAsPlayer(sender);

        attribute = AttributeCapability.getAttributes(player);
        info = getInfoFromString(args[0], attribute);

        if (info == null)
            throw new WrongUsageException(commandUsage);

        notifyCommandListener(
                sender, this, "commands.rpgmode.attributeinfo.success", player.getName(), info[0], info[1]);
    }

    private String[] getInfoFromString(String string, IAttribute attribute) {
        switch (string.toLowerCase()) {
            case "strength":
                return new String[] {"strength", floatToString(attribute.getStrength(true))};
            case "str":
                return getInfoFromString("strength", attribute);
            case "dexterity":
                return new String[] {"dexterity", floatToString(attribute.getDexterity(true))};
            case "dex":
                return getInfoFromString("dexterity", attribute);
            case "intelligence":
                return new String[] {"intelligence", floatToString(attribute.getIntelligence(true))};
            case "int":
                return getInfoFromString("intelligence", attribute);
            case "constitution":
                return new String[] {"constitution", floatToString(attribute.getConstitution(true))};
            case "con":
                return getInfoFromString("constitution", attribute);
            case "wisdom":
                return new String[] {"wisdom", floatToString(attribute.getWisdom(true))};
            case "wis":
                return getInfoFromString("wisdom", attribute);
            case "attributepoints":
                return new String[] {"attribute points", floatToString(attribute.getAttributePoint())};
            case "points":
                return getInfoFromString("attributepoints", attribute);
            default:
                return new String[] {};
        }
    }

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    @Override
    public List<String> getTabCompletions(
            MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "strength", "dexterity", "intelligence",
                                                    "constitution", "wisdom", "attributepoints");
        return (args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) :
                Collections.emptyList());
    }
}
