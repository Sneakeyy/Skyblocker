package de.hysky.skyblocker.skyblock;

import de.hysky.skyblocker.annotations.Init;
import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.utils.Utils;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

public class RngMeterNotifier {
	private static final String rngMeterProc = "RNG METER! Reselected the";

	@Init
	public static void init() {
		ClientReceiveMessageEvents.ALLOW_GAME.register(RngMeterNotifier::onChatMessage);
	}

	private static boolean onChatMessage(Component text, boolean overlay) {
		if (Utils.isOnSkyblock() && SkyblockerConfigManager.get().general.rngMeterNotifier.enableRngMeterNotifier) {
			String message = ChatFormatting.stripFormatting(text.getString());
			Minecraft client = Minecraft.getInstance();
			if (client.player != null && message.startsWith(rngMeterProc)) {
				client.gui.setTimes(5, 75, 20);
				client.gui.setTitle(Component.translatable("skyblocker.rngMeterNotifier.rngMeterProc").withStyle(ChatFormatting.LIGHT_PURPLE));
				client.player.playSound(SoundEvents.TOTEM_USE, 100f, 0.1f);
				client.player.playSound(SoundEvents.FIREWORK_ROCKET_LAUNCH, 100f, 0.1f);
			}
		}
		return true;
	}
}
