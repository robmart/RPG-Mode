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
public class CommandManaInfo extends CommandBase {
    protected final String name = "manainfo";
    private final int permissionLevel = 1;
    private final String commandUsage = "commands." + Reference.MOD_ID.toLowerCase() + ".manainfo.usage";

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

        if (args.length > 2 || args.length == 0)
            throw new WrongUsageException(commandUsage);
        else if (args.length == 2)
            player = getPlayer(server, sender, args[1]);
        else
            player = getCommandSenderAsPlayer(sender);

        mana = player.getCapability(ManaCapability.MANA_CAPABILITY, null);
        info = getInfoFromString(args[0], mana);

        if (info == null)
            throw new WrongUsageException(commandUsage);

        notifyCommandListener(sender, this, "commands.rpgmode.manainfo.success", player.getName(), info[0], info[1]);
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

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "mana", "maxmana", "regenspeed", "regenamount") : (args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()): Collections.emptyList());
    }
}
