package de.hysky.skyblocker.skyblock.slayers.boss.vampire;

import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.Entity;

import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.skyblock.slayers.SlayerManager;
import de.hysky.skyblocker.skyblock.slayers.SlayerType;
import de.hysky.skyblocker.utils.render.title.Title;
import de.hysky.skyblocker.utils.render.title.TitleContainer;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;


public class StakeIndicator {
    private static final Title title = new Title("skyblocker.rift.stakeNow", ChatFormatting.RED);
	private static final Minecraft CLIENT = Minecraft.getInstance();

	public static void updateStake() {
		if (!SkyblockerConfigManager.get().slayers.vampireSlayer.enableSteakStakeIndicator || !SlayerManager.isFightingSlayerType(SlayerType.VAMPIRE)) {
            TitleContainer.removeTitle(title);
            return;
        }
		Entity slayerEntity = SlayerManager.getSlayerArmorStand();
        if (slayerEntity != null && slayerEntity.getDisplayName().toString().contains("҉")) {
            TitleContainer.addTitle(title);
			if (CLIENT.player != null) {
				CLIENT.player.playSound(SoundEvents.TOTEM_USE, 100f, 1f);
			}
        } else {
            TitleContainer.removeTitle(title);
        }
    }
}
