package robmart.rpgmode.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.capability.attribute.IAttribute;
import robmart.rpgmode.common.reference.Reference;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandAttributeInfo extends CommandBase {
    protected final String name = "attributeinfo";
    private final int permissionLevel = 1;
    private final String commandUsage = "commands." + Reference.MOD_ID.toLowerCase() + ".attributeinfo.usage";

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
        IAttribute attribute;
        String[] info;

        if (args.length > 2 || args.length == 0)
            throw new WrongUsageException(commandUsage);
        else if (args.length == 2)
            player = getPlayer(server, sender, args[1]);
        else
            player = getCommandSenderAsPlayer(sender);

        attribute = player.getCapability(AttributeCapability.ATTRIBUTE_CAPABILITY, null);
        info = getInfoFromString(args[0], attribute);

        if (info == null)
            throw new WrongUsageException(commandUsage);

        notifyCommandListener(sender, this, "commands.rpgmode.attributeinfo.success", player.getName(), info[0], info[1]);
    }

    private String[] getInfoFromString(String string, IAttribute attribute){
        return !"strength".equalsIgnoreCase(string) && !"str".equalsIgnoreCase(string) ?
                (!"dexterity".equalsIgnoreCase(string) && !"dex".equalsIgnoreCase(string) ?
                        (!"intelligence".equalsIgnoreCase(string) && !"int".equalsIgnoreCase(string) ?
                                (!"constitution".equalsIgnoreCase(string) && !"con".equalsIgnoreCase(string) ?
                                        (!"wisdom".equalsIgnoreCase(string) && !"wis".equalsIgnoreCase(string) ?
                                                (!"attributepoints".equalsIgnoreCase(string) && !"points".equalsIgnoreCase(string) ?
                                                        null :
                                                        new String[]{"attribute points", floatToString(attribute.getAttributePoint())}) :
                                                new String[]{"wisdom", floatToString(attribute.getWisdom())}) :
                                        new String[]{"constitution", floatToString(attribute.getConstitution())}) :
                                new String[]{"intelligence", floatToString(attribute.getIntelligence())}) :
                        new String[]{"dexterity", floatToString(attribute.getDexterity())}) :
                new String[]{"strength", floatToString(attribute.getStrength())};
    }

    private String floatToString(float value) {
        return String.valueOf(value);
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, "strength", "dexterity", "intelligence", "constitution", "wisdom", "attributepoints") : (args.length == 2 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()): Collections.emptyList());
    }
}