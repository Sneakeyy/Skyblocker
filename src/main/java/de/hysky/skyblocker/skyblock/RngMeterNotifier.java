package de.hysky.skyblocker.skyblock;

import de.hysky.skyblocker.annotations.Init;
import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.utils.Utils;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class RngMeterNotifier {
	private static final String rngMeterProcced = "RNG METER! Reselected the";

	@Init
	public static void init() {
		ClientReceiveMessageEvents.ALLOW_GAME.register(RngMeterNotifier::onChatMessage);
	}

	private static boolean onChatMessage(Text text, boolean overlay) {
		if (Utils.isOnSkyblock() && SkyblockerConfigManager.get().general.rngMeterNotifier.enableRngMeterNotifier) {
			String message = Formatting.strip(text.getString());
			MinecraftClient client = MinecraftClient.getInstance();
			if (client.player != null && message.startsWith(rngMeterProcced)) {
				client.inGameHud.setTitleTicks(5, 75, 20);
				client.inGameHud.setTitle(Text.translatable("skyblocker.rngMeterNotifier.rngProcced").formatted(Formatting.LIGHT_PURPLE));
				client.player.playSound(SoundEvents.ITEM_TOTEM_USE, 100f, 0.1f);
				client.player.playSound(SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, 100f, 0.1f);
			}
		}
		return true;
	}
}
