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

package robmart.rpgmode.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Robmart
 * Created on 9/14/2018
 */
public class ReflectionUtil {
    public static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw e;
            }
            else {
                return getField(superClass, fieldName);
            }
        }
    }

    public static void setField(
            Class clazz, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(clazz, fieldName);
        if (value.getClass().isAssignableFrom(field.getType())) {
            throw new ClassCastException(value.getClass() + " can't be cast to " + field.getType());
        }

        if (!Modifier.isPublic(field.getModifiers()) ||
            !Modifier.isPublic(field.getDeclaringClass().getModifiers()))
            makeAccessible(field);

        if (Modifier.isFinal(field.getModifiers())) {
            Field modifiersField = null;
            modifiersField = Field.class.getDeclaredField("modifiers");
            makeAccessible(modifiersField);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        }

        field.set(null, value);
    }

    public static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) ||
            !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    private ReflectionUtil() {
    }
}