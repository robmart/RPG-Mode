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

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Robmart
 */
public class CommandHealthInfo extends CommandBase {
    protected static final String NAME             = "healthinfo";
    private static final   int    PERMISSION_LEVEL = 1;
    private static final   String COMMAND_USAGE    = "commands." + Reference.MOD_ID.toLowerCase() + ".healthinfo.usage";

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

        if (args.length > 2 || args.length == 0)
            throw new WrongUsageException(COMMAND_USAGE);
        else if (args.length == 2)
            player = getPlayer(server, sender, args[1]);
        else
            player = getCommandSenderAsPlayer(sender);

        info = getInfoFromString(args[0], player);

        if (info == null)
            throw new WrongUsageException(COMMAND_USAGE);

        notifyCommandListener(sender, this, "commands.rpgmode.healthinfo.success", player.getName(), info[0], info[1]);
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

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    @Override
    public List<String> getTabCompletions(
            MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 1)
            return getListOfStringsMatchingLastWord(args, "health", "maxhealth");
        return (args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) :
                Collections.emptyList());
    }
}
