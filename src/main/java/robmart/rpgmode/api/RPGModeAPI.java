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

package robmart.rpgmode.api;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.IAnimals;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Robmart
 * Created on 9/13/2018
 */
public class RPGModeAPI {
    /**
     * List of mobs who are not classified as a monster {@link EnumCreatureType#MONSTER} but should still have the
     * attributes capability.
     * <p>
     * Examples include: golems, wolfs.
     */
    private static List<Class<? extends IAnimals>> mobWithAttributeList = new ArrayList<>();

    static {
        addMobWithAttribute(EntityIronGolem.class);
        addMobWithAttribute(EntityLlama.class);
        addMobWithAttribute(EntityPolarBear.class);
        addMobWithAttribute(EntityWolf.class);
        addMobWithAttribute(EntitySnowman.class);
    }

    /**
     * Adds non-hostile mob to the list of entities that should have attributes.
     *
     * @param mobClass Class of the mob in question
     */
    public static void addMobWithAttribute(Class<? extends IAnimals> mobClass) {
        if (!mobWithAttributeList.contains(mobClass) && !mobClass.isAssignableFrom(IMob.class))
            mobWithAttributeList.add(mobClass);
    }

    public static boolean shouldMobHaveAttributes(Class mobClass) {
        return mobWithAttributeList.contains(mobClass);
    }

    private RPGModeAPI() {
    }
}
