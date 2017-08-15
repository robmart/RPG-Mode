package robmart.rpgmode.common.init;

import net.minecraftforge.common.MinecraftForge;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.capability.character.CharacterCapability;
import robmart.rpgmode.common.capability.health.MaxHealthCapability;
import robmart.rpgmode.common.capability.mana.ManaCapability;
import robmart.rpgmode.common.handlers.CapabilityHandler;

public class InitCapabilities {

    public static void init(){
        ManaCapability.register();
        MaxHealthCapability.register();
        AttributeCapability.register();
        CharacterCapability.register();
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
    }
}
