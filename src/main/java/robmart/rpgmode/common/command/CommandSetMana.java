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
import robmart.rpgmode.common.capability.mana.IMana;
import robmart.rpgmode.common.capability.mana.ManaCapability;
import robmart.rpgmode.common.reference.Reference;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Robmart
 */
public class CommandSetMana extends CommandBase {
    public String name = "setmana";
    public int permissionLevel = 2;
    String commandUsage = "commands." + Reference.MOD_ID.toLowerCase() + ".setmana.usage";

    @Override
    public String getName() {
        return name;
    }

    public int getPermissionLevel(){
        return permissionLevel;
    }

    @Override
    public String getUsage(ICommandSender sender){
        return commandUsage;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player;
        IMana mana;
        String[] info;

        if (args.length > 3 || args.length == 1 || args.length == 0)
            throw new WrongUsageException(commandUsage);
        else if (args.length == 3)
            player = getPlayer(server, sender, args[2]);
        else
            player = getCommandSenderAsPlayer(sender);

        mana = ManaCapability.get(player);

        setMana(args[0], Float.valueOf(args[1]), mana);
        info = getInfoFromString(args[0], mana);

        if (info == null)
            throw new WrongUsageException(commandUsage);

        notifyCommandListener(player, this, "commands.rpgmode.setmana.success1", info[0], info[1]);

        if (!player.getName().equalsIgnoreCase(getCommandSenderAsPlayer(sender).getName()))
            notifyCommandListener(sender, this, "commands.rpgmode.setmana.success2", player.getName(), info[0], info[1]);
    }

    private String[] getInfoFromString(String string, IMana mana){
        switch (string.toLowerCase()) {
            case "mana":
                return new String[]{"mana", floatToString(mana.getMana())};
            case "m":
                return getInfoFromString("mana", mana);
            case "maxmana":
                return new String[]{"max mana", floatToString(mana.getMaxMana())};
            case "max":
                return getInfoFromString("maxmana", mana);
            case "regenspeed":
                return new String[]{"regeneration speed", floatToString(mana.getRegenSpeed())};
            case "speed":
                return getInfoFromString("regenspeed", mana);
            case "regenamount":
                return new String[]{"regeneration amount", floatToString(mana.getRegenAmount())};
            case "amount":
                return getInfoFromString("regenamount", mana);
        }
        return null;
    }

    private void setMana(String string, float amount, IMana mana) {
        switch (string.toLowerCase()) {
            case "mana":
                mana.setMana(amount);
                break;
            case "m":
                setMana("mana", amount, mana);
                break;
            case "maxmana":
                mana.setMaxMana(amount);
                break;
            case "max":
                setMana("maxmana", amount, mana);
                break;
            case "regenspeed":
                mana.setRegenSpeed(amount);
                break;
            case "speed":
                setMana("regenspeed", amount, mana);
                break;
            case "regenamount":
                mana.setRegenAmount(amount);
                break;
            case "amount":
                setMana("regenamount", amount, mana);
                break;
        }
    }

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "mana", "maxmana", "regenspeed", "regenamount") : (args.length == 3 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()): Collections.emptyList());
    }
}
