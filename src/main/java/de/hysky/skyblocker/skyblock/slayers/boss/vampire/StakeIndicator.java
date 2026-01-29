package de.hysky.skyblocker.skyblock.slayers.boss.vampire;

import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.skyblock.slayers.SlayerManager;
import de.hysky.skyblocker.skyblock.slayers.SlayerType;
import de.hysky.skyblocker.utils.render.title.Title;
import de.hysky.skyblocker.utils.render.title.TitleContainer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;

public class StakeIndicator {
	private static final Title title = new Title("skyblocker.rift.stakeNow", ChatFormatting.RED);
	private static final Minecraft CLIENT = Minecraft.getInstance();
	private static final SoundEvent sound = SoundEvents.TOTEM_USE;
	private static final float indicatorVolume = 100f;
	private static final float indicatorPitch = 0.1f;

	public static void updateStake() {
		if (!SkyblockerConfigManager.get().slayers.vampireSlayer.enableSteakStakeIndicator || !SlayerManager.isInSlayerType(SlayerType.VAMPIRE)) {
			TitleContainer.removeTitle(title);
			return;
		}
		Entity slayerEntity = SlayerManager.getSlayerBossArmorStand();
		if (slayerEntity != null && SlayerManager.getBossFight() != null && slayerEntity.getDisplayName().toString().contains("҉") && !SlayerManager.getBossFight().slain) {
			TitleContainer.addTitleAndPlayCustomSound(title, sound, indicatorVolume, indicatorPitch);
		} else {
			TitleContainer.removeTitle(title);
		}
	}
}
