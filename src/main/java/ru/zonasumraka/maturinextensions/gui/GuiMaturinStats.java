package ru.zonasumraka.maturinextensions.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import codersafterdark.reskillable.api.ReskillableRegistries;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.skill.Skill;
import codersafterdark.reskillable.client.gui.GuiSkills;
import codersafterdark.reskillable.client.gui.handler.InventoryTabHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.zonasumraka.maturinextensions.capability.MECapabilities;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.IMECapabilities;
import ru.zonasumraka.maturinextensions.capability.MECapabilities.MECapabilityWrapper;
import ru.zonasumraka.maturinextensions.network.StatRequestPacket;
import ru.zonasumraka.maturinextensions.proxy.CommonProxy;
import ru.zonasumraka.maturinextensions.util.ClientUtils;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiMaturinStats extends GuiScreen {
	private int guiWidth, guiHeight;
	private int left;
	private int top;
	private List<Skill> skills = new ArrayList<>();

	public static final ResourceLocation STATBAR = new ResourceLocation("maturinextensions:textures/gui/statbar.png");

	public GuiMaturinStats() {
		super();
		ReskillableRegistries.SKILLS.getValuesCollection().stream().filter(Skill::isEnabled).forEach(skills::add);
	}

	@Override
	public void initGui() {
		guiWidth = 176;
		guiHeight = 166;

		buttonList.clear();

		InventoryTabHandler.addTabs(this, buttonList);
		CustomInventoryTabHandler.addTabs(this, buttonList);
		
		CommonProxy.NETWORK.sendToServer(new StatRequestPacket());
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		mc.renderEngine.bindTexture(GuiSkills.SKILLS_RES);
		GlStateManager.color(1F, 1F, 1F);
		left = width / 2 - guiWidth / 2;
		top = height / 2 - guiHeight / 2;
		drawTexturedModalRect(left, top, 0, 0, guiWidth, guiHeight);

		String statsStr = I18n.format("skillable.tab.stats");

		int y = top + 5;

		fontRenderer.drawString(statsStr, left + guiWidth / 2 - fontRenderer.getStringWidth(statsStr) / 2, y, 4210752);

		y += fontRenderer.FONT_HEIGHT;

		List<String> tooltip = null;
		
		IMECapabilities caps = mc.player.getCapability(MECapabilities.MECapabilitiesProvider.MATURIN_CAP, null);
		
		int widthWW = fontRenderer.getStringWidth("WW");
		int leftoffs = left + guiWidth / 2 - (5 + 16 + 2 + widthWW + 2 + 111 + 2 + widthWW) / 2;

		for (Skill skill : skills) {
			GlStateManager.color(1F, 1F, 1F);
			mc.renderEngine.bindTexture(skill.getSpriteLocation());
			GuiSkills.drawSkill(leftoffs + 5, y, skill);
			int level = PlayerDataHandler.get(mc.player).getSkillInfo(skill).getLevel();
			fontRenderer.drawString(String.valueOf(level),
					leftoffs + 5 + 16 + 2, y + 16 / 2 - fontRenderer.FONT_HEIGHT / 2, 0x055F65);
			mc.renderEngine.bindTexture(STATBAR);
			ClientUtils.drawNonStandardTexturedRect(leftoffs + 5 + 16 + 2 + widthWW + 2, y + 16 / 2 - 5 / 2, 0, 0, 111, 5, 111, 10);
			double maxStat = MECapabilityWrapper.getPointsToNextLevel(level);
			double statVal = caps.getStat(skill.getRegistryName());
			ClientUtils.drawNonStandardTexturedRect(leftoffs + 5 + 16 + 2 + widthWW + 2, y + 16 / 2 - 5 / 2, 0, 5, (int)((statVal / maxStat) * 111), 5, 111, 10);
			String nextString = String.valueOf(level >= skill.getCap() ? "" : level + 1);
			fontRenderer.drawString(nextString,
					leftoffs + 5 + 16 + 2 + widthWW + 2 + 111 + 2 + widthWW - fontRenderer.getStringWidth(nextString), y + 16 / 2 - fontRenderer.FONT_HEIGHT / 2, 0x055F65);
			if (mouseX <= leftoffs + 171 && mouseX >= leftoffs + 5 && mouseY >= y && mouseY < y + 16) {
				tooltip = Arrays.asList(skill.getName(), I18n.format("maturinextensions.tooltip.current", statVal),
						I18n.format("maturinextensions.tooltip.required", maxStat), I18n.format("maturinextensions.tooltip.level", level));
			}
			y += 16;
		}
		
		if(tooltip != null) {
			ClientUtils.renderTooltip(mouseX, mouseY, tooltip);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);

	}
}
