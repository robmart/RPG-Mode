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

package robmart.rpgmode.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import robmart.rpgmode.api.capability.attribute.IAttribute;
import robmart.rpgmode.api.reference.Reference;
import robmart.rpgmode.common.capability.attribute.AttributeCapability;
import robmart.rpgmode.common.capability.attribute.AttributeImplementation;

/**
 * @author Robmart
 * Created on 9/14/2018
 */
public class GuiAttributes extends RPGGuiScreen {
    private EntityPlayer player;

    public GuiAttributes(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        drawBackgroundLayer();

        drawStringWithBackdrop(player.getName(), getGuiLeft() + 7, getGuiTop() + 7, 0xffffff, 0);
        drawIcons();
        drawStats();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        addAPButtons();
    }

    private void addAPButtons() {
        try {
            addButton(new GuiAddPointButton(this, AttributeCapability.getAttributes(player),
                                            AttributeImplementation.class.getMethod("setStrength", int.class,
                                                                                    boolean.class),
                                            AttributeImplementation.class.getMethod("getStrength", boolean.class),
                                            buttonList.size() + 1, 70, 33));
            addButton(new GuiAddPointButton(this, AttributeCapability.getAttributes(player),
                                            AttributeImplementation.class.getMethod("setDexterity", int.class,
                                                                                    boolean.class),
                                            AttributeImplementation.class.getMethod("getDexterity", boolean.class),
                                            buttonList.size() + 1, 70, 53));
            addButton(new GuiAddPointButton(this, AttributeCapability.getAttributes(player),
                                            AttributeImplementation.class.getMethod("setConstitution", int.class,
                                                                                    boolean.class),
                                            AttributeImplementation.class.getMethod("getConstitution", boolean.class),
                                            buttonList.size() + 1, 70, 73));
            addButton(new GuiAddPointButton(this, AttributeCapability.getAttributes(player),
                                            AttributeImplementation.class.getMethod("setIntelligence", int.class,
                                                                                    boolean.class),
                                            AttributeImplementation.class.getMethod("getIntelligence", boolean.class),
                                            buttonList.size() + 1, 70, 93));
            addButton(new GuiAddPointButton(this, AttributeCapability.getAttributes(player),
                                            AttributeImplementation.class.getMethod("setWisdom", int.class,
                                                                                    boolean.class),
                                            AttributeImplementation.class.getMethod("getWisdom", boolean.class),
                                            buttonList.size() + 1, 70, 113));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private void drawIcons() {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glPushMatrix();

        GlStateManager.scale(0.140625, 0.140625, 1f);
        GlStateManager.color(0F, 0F, 0F, 1.0f);
        mc.getTextureManager().bindTexture(new ResourceLocation(
                Reference.MOD_ID, "textures/gui/icons/attribute_points.png"));
        this.draw((int) Math.round((getGuiLeft() + 6) * 7.111), (int) Math.round((getGuiTop() + 20) * 7.111), 64, 64);
        GlStateManager.scale(7.111, 7.111, 1f);

        GlStateManager.scale(0.25, 0.25, 1f);
        mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/icons/strength.png"));
        this.draw((getGuiLeft() + 6) * 4, (getGuiTop() + 33) * 4, 64, 64);
        mc.getTextureManager()
          .bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/icons/dexterousness.png"));
        this.draw((getGuiLeft() + 6) * 4, (getGuiTop() + 53) * 4, 64, 64);
        mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/icons/fortitude.png"));
        this.draw((getGuiLeft() + 6) * 4, (getGuiTop() + 73) * 4, 64, 64);
        mc.getTextureManager()
          .bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/icons/intelligence.png"));
        this.draw((getGuiLeft() + 6) * 4, (getGuiTop() + 93) * 4, 64, 64);
        mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/icons/wisdom.png"));
        this.draw((getGuiLeft() + 6) * 4, (getGuiTop() + 113) * 4, 64, 64);
        GlStateManager.scale(4, 4, 1f);
        GlStateManager.color(1f, 1f, 1f, 1f);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    private void drawStats() {
        IAttribute playerAttributes = AttributeCapability.getAttributes(player);

        GlStateManager.scale(0.75, 0.75, 1);
        drawStringWithBackdrop(
                playerAttributes.getAttributePoint(), Math.round((getGuiLeft() + 18) * 100 / 75),
                Math.round((getGuiTop() + 22) * 100 / 75), 8453920, 0);
        drawStringWithBackdrop(playerAttributes.getStrength(false), Math.round((getGuiLeft() + 25) * 100 / 75),
                               Math.round((getGuiTop() + 34) * 100 / 75), 10164248, 0);
        drawStringWithBackdrop(
                String.format("+ %s", playerAttributes.getStrMod()), Math.round((getGuiLeft() + 25) * 100 / 75),
                Math.round((getGuiTop() + 43) * 100 / 75), 10164248, 0);
        drawStringWithBackdrop(playerAttributes.getDexterity(false), Math.round((getGuiLeft() + 25) * 100 / 75),
                               Math.round((getGuiTop() + 54) * 100 / 75), 7449644, 0);
        drawStringWithBackdrop(
                String.format("+ %s", playerAttributes.getDexMod()), Math.round((getGuiLeft() + 25) * 100 / 75),
                Math.round((getGuiTop() + 63) * 100 / 75), 7449644, 0);
        drawStringWithBackdrop(playerAttributes.getConstitution(false), Math.round((getGuiLeft() + 25) * 100 / 75),
                               Math.round((getGuiTop() + 74) * 100 / 75), 15338024, 0);
        drawStringWithBackdrop(
                String.format("+ %s", playerAttributes.getConMod()), Math.round((getGuiLeft() + 25) * 100 / 75),
                Math.round((getGuiTop() + 83) * 100 / 75), 15338024, 0);
        drawStringWithBackdrop(playerAttributes.getIntelligence(false), Math.round((getGuiLeft() + 25) * 100 / 75),
                               Math.round((getGuiTop() + 94) * 100 / 75), 2754524, 0);
        drawStringWithBackdrop(
                String.format("+ %s", playerAttributes.getIntMod()), Math.round((getGuiLeft() + 25) * 100 / 75),
                Math.round((getGuiTop() + 103) * 100 / 75), 2754524, 0);
        drawStringWithBackdrop(playerAttributes.getWisdom(false), Math.round((getGuiLeft() + 25) * 100 / 75),
                               Math.round((getGuiTop() + 114) * 100 / 75), 6693065, 0);
        drawStringWithBackdrop(
                String.format("+ %s", playerAttributes.getWisMod()), Math.round((getGuiLeft() + 25) * 100 / 75),
                Math.round((getGuiTop() + 123) * 100 / 75), 6693065, 0);
        GlStateManager.scale(1.33, 1.33, 1);
    }

    @Override
    public int getXSize() {
        return 248;
    }

    @Override
    public int getYSize() {
        return 138;
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(Reference.MOD_ID, "textures/gui/character_background.png");
    }
}
