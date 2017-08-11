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

        mana = player.getCapability(ManaCapability.MANA_CAPABILITY, null);

        setMana(args[0], Float.valueOf(args[1]), mana);
        info = getInfoFromString(args[0], mana);

        if (info == null)
            throw new WrongUsageException(commandUsage);

        notifyCommandListener(player, this, "commands.rpgmode.setmana.success1", info[0], info[1]);

        if (!player.getName().equalsIgnoreCase(getCommandSenderAsPlayer(sender).getName()))
            notifyCommandListener(sender, this, "commands.rpgmode.setmana.success2", player.getName(), info[0], info[1]);
    }

    private String[] getInfoFromString(String string, IMana mana){
        return !"mana".equalsIgnoreCase(string) && !"m".equalsIgnoreCase(string) ?
                (!"max".equalsIgnoreCase(string) && !"maxmana".equalsIgnoreCase(string) ?
                        (!"speed".equalsIgnoreCase(string) && !"regenspeed".equalsIgnoreCase(string) ?
                                (!"amount".equalsIgnoreCase(string) && !"regenamount".equalsIgnoreCase(string) ?
                                        null :
                                        new String[]{"regeneration amount", floatToString(mana.getRegenAmount())}) :
                                new String[]{"regeneration speed", floatToString(mana.getRegenSpeed())}) :
                        new String[]{"max mana", floatToString(mana.getMaxMana())}) :
                new String[]{"mana", floatToString(mana.getMana())};
    }

    private void setMana(String string, float amount, IMana mana) {
        if ("mana".equalsIgnoreCase(string) || "m".equalsIgnoreCase(string))
            mana.setMana(amount);
        else if ("maxmana".equalsIgnoreCase(string) || "max".equalsIgnoreCase(string))
            mana.setMaxMana(amount);
        else if ("regenspeed".equalsIgnoreCase(string) || "speed".equalsIgnoreCase(string))
            mana.setRegenSpeed(amount);
        else if ("regenamount".equalsIgnoreCase(string) || "amount".equalsIgnoreCase(string))
            mana.setRegenAmount(amount);
    }

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "mana", "maxmana", "regenspeed", "regenamount") : (args.length == 3 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()): Collections.emptyList());
    }
}
