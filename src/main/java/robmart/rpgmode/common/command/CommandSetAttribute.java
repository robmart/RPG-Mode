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
public class CommandSetAttribute extends CommandBase {
    private static final String NAME = "setattribute";
    private static final int    PERMISSION_LEVEL = 2;
    private static final String COMMAND_USAGE = "commands." + Reference.MOD_ID.toLowerCase() + ".setattribute.usage";

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
        IAttribute attribute;
        String[] info;

        if (args.length > 3 || args.length == 1 || args.length == 0)
            throw new WrongUsageException(COMMAND_USAGE);
        else if (args.length == 3)
            player = getPlayer(server, sender, args[2]);
        else
            player = getCommandSenderAsPlayer(sender);

        attribute = AttributeCapability.getAttributes(player);

        setAttribute(args[0], Integer.valueOf(args[1]), attribute);
        info = getInfoFromString(args[0], attribute);

        if (info == null)
            throw new WrongUsageException(COMMAND_USAGE);

        notifyCommandListener(player, this, "commands.rpgmode.setattribute.success1", info[0], info[1]);

        if (!player.getName().equalsIgnoreCase(getCommandSenderAsPlayer(sender).getName()))
            notifyCommandListener(
                    sender, this, "commands.rpgmode.setattribute.success2", player.getName(), info[0], info[1]);
    }

    public int getPermissionLevel() {
        return PERMISSION_LEVEL;
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
            case "points":
                return getInfoFromString("attributepoints", attribute);
            default:
                return new String[] {"attribute points", floatToString(attribute.getAttributePoint())};
        }
    }

    private void setAttribute(String string, int amount, IAttribute attribute) {
        switch (string.toLowerCase()) {
            case "strength":
                attribute.setStrength(amount, true);
                break;
            case "str":
                setAttribute("strength", amount, attribute);
                break;
            case "dexterity":
                attribute.setDexterity(amount, true);
                break;
            case "dex":
                setAttribute("dexterity", amount, attribute);
                break;
            case "intelligence":
                attribute.setIntelligence(amount, true);
                break;
            case "int":
                setAttribute("intelligence", amount, attribute);
                break;
            case "constitution":
                attribute.setConstitution(amount, true);
                break;
            case "con":
                setAttribute("constitution", amount, attribute);
                break;
            case "wisdom":
                attribute.setWisdom(amount, true);
                break;
            case "wis":
                setAttribute("wisdom", amount, attribute);
                break;
            case "attributepoints":
                attribute.setAttributePoint(amount, true);
                break;
            case "points":
                setAttribute("attributepoints", amount, attribute);
                break;
            default:
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
            return getListOfStringsMatchingLastWord(args, "strength", "dexterity", "intelligence",
                                                    "constitution", "wisdom", "attributepoints");
        return (args.length == 3 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) :
                Collections.emptyList());
    }
}
