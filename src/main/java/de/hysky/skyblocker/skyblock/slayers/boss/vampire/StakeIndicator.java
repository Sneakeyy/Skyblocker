package de.hysky.skyblocker.skyblock.slayers.boss.vampire;

import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.skyblock.slayers.SlayerManager;
import de.hysky.skyblocker.skyblock.slayers.SlayerType;
import de.hysky.skyblocker.utils.render.title.Title;
import de.hysky.skyblocker.utils.render.title.TitleContainer;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;

public class StakeIndicator {
    private static final Title title = new Title("skyblocker.rift.stakeNow", Formatting.RED);
	private static final SoundEvent sound = SoundEvents.ITEM_TOTEM_USE;
	private static final float indicatorVolume = 100f;
	private static final float indicatorPitch = 0.1f;

	public static void updateStake() {
		if (!SkyblockerConfigManager.get().slayers.vampireSlayer.enableSteakStakeIndicator || !SlayerManager.isInSlayerType(SlayerType.VAMPIRE)) {
            TitleContainer.removeTitle(title);
            return;
        }
		Entity slayerEntity = SlayerManager.getSlayerBossArmorStand();
        if (slayerEntity != null && slayerEntity.getDisplayName().toString().contains("҉") && !SlayerManager.getBossFight().slain) {
            TitleContainer.addTitleAndPlayCustomSound(title, sound, indicatorVolume, indicatorPitch);
        } else {
            TitleContainer.removeTitle(title);
        }
    }
}
