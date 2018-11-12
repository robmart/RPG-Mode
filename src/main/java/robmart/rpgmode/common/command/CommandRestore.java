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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import robmart.rpgmode.api.capability.mana.IMana;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.mana.ManaCapability;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author Robmart
 */
public class CommandRestore extends CommandBase {
    private static final String NAME             = "restore";
    private static final int    PERMISSION_LEVEL = 2;
    private static final String COMMAND_USAGE    = "commands." + Reference.MOD_ID.toLowerCase() + ".restore.usage";

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
        IMana mana;

        if (args.length > 1)
            throw new WrongUsageException(COMMAND_USAGE);
        else if (args.length == 1)
            player = getPlayer(server, sender, args[0]);
        else
            player = getCommandSenderAsPlayer(sender);

        mana = ManaCapability.getMana(player);
        player.setHealth(player.getMaxHealth());
        mana.restoreMana();
        player.getFoodStats().addStats(20, 20);

        Potion[] effects = new Potion[player.getActivePotionEffects().toArray().length];
        for (int i = 0; i < player.getActivePotionEffects().toArray().length; i++)
            effects[i] = ((PotionEffect) player.getActivePotionEffects().toArray()[i]).getPotion();
        for (Potion potion : effects)
            if (potion.isBadEffect())
                player.removePotionEffect(potion);

        notifyCommandListener(player, this, "commands.rpgmode.restore.success1");

        if (!player.getName().equalsIgnoreCase(getCommandSenderAsPlayer(sender).getName()))
            notifyCommandListener(sender, this, "commands.rpgmode.restore.success2", player.getName());
    }

    public int getPermissionLevel() {
        return PERMISSION_LEVEL;
    }

    @Override
    public List<String> getTabCompletions(
            MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) :
               Collections.emptyList();
    }
}
