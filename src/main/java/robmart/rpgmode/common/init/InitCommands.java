package robmart.rpgmode.common.init;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import robmart.rpgmode.common.command.*;

public class InitCommands {

    public static void init(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandManaInfo());
        event.registerServerCommand(new CommandSetMana());
        event.registerServerCommand(new CommandRestore());
        event.registerServerCommand(new CommandSetHealth());
        event.registerServerCommand(new CommandHealthInfo());
        event.registerServerCommand(new CommandAttributeInfo());
        event.registerServerCommand(new CommandSetAttribute());
    }
}
