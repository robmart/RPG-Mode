package robmart.rpgmode.client.init;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import robmart.rpgmode.client.gui.*;

public class InitGui {

    public static void init(){
        MinecraftForge.EVENT_BUS.register(new GuiHealth(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(new GuiMana(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(new GuiAir(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(new GuiFood(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(new GuiArmor(Minecraft.getMinecraft()));
    }
}
