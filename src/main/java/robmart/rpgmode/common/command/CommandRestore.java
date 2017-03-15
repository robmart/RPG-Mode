package robmart.rpgmode.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import robmart.rpgmode.common.capability.mana.IMana;
import robmart.rpgmode.common.capability.mana.ManaProvider;
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
public class CommandRestore extends CommandBase {
    private final String name = "restore";
    private final int permissionLevel = 2;
    private final String commandUsage = "commands." + Reference.MOD_ID.toLowerCase() + ".restore.usage";

    public String getCommandName() {
        return name;
    }

    public int getPermissionLevel(){
        return permissionLevel;
    }

    public String getCommandUsage(ICommandSender sender){
        return commandUsage;
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException{
        EntityPlayer player;
        IMana mana;

        if (args.length > 1)
            throw  new WrongUsageException(commandUsage);
        else if (args.length == 1)
            player = getPlayer(server, sender, args[0]);
        else
            player = getCommandSenderAsPlayer(sender);

        mana = player.getCapability(ManaProvider.MANA_CAPABILITY, null);
        player.setHealth(player.getMaxHealth());
        mana.setMana(mana.getMaxMana());
        player.getFoodStats().addStats(20, 20);

        notifyCommandListener(player, this, "commands.rpgmode.restore.success1");

        if (!player.getName().equalsIgnoreCase(getCommandSenderAsPlayer(sender).getName()))
            notifyCommandListener(sender, this, "commands.rpgmode.restore.success2", player.getName());
    }

    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()): Collections.emptyList();
    }
}
