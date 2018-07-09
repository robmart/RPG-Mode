/*
 * Copyright 2018, Vazkii
 *
 * This work is licensed under the Botania licence
 *
 * To view the license visit https://botaniamod.net/license.php
 */

package robmart.rpgmode.client.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Vazkii
 */
public interface IModelRegister {
    @SideOnly(Side.CLIENT)
    void registerModels();
}