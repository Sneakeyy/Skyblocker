package de.hysky.skyblocker.skyblock;

import net.minecraft.sounds.SoundEvents;
import org.jspecify.annotations.Nullable;

import de.hysky.skyblocker.annotations.Init;
import de.hysky.skyblocker.config.SkyblockerConfigManager;
import de.hysky.skyblocker.utils.Utils;
import de.hysky.skyblocker.utils.scheduler.Scheduler;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;

public class QuiverWarning {
	private static @Nullable Type warning = null;
	private static final Minecraft CLIENT = Minecraft.getInstance();

	@Init
	public static void init() {
		ClientReceiveMessageEvents.ALLOW_GAME.register(QuiverWarning::onChatMessage);
		Scheduler.INSTANCE.scheduleCyclic(QuiverWarning::update, 10);
	}

	public static boolean onChatMessage(Component text, boolean overlay) {
		String message = text.getString();
		if (SkyblockerConfigManager.get().general.quiverWarning.enableQuiverWarning && message.startsWith("QUIVER! You")) {
			CLIENT.gui.resetTitleTimes();
			if (message.startsWith("QUIVER! You only have 50")) {
				onChatMessage(Type.FIFTY_LEFT);
			} else if (message.startsWith("QUIVER! You only have 10")) {
				onChatMessage(Type.TEN_LEFT);
			} else if (message.startsWith("QUIVER! You have run out of")) {
				onChatMessage(Type.EMPTY);
			}
		}
		return true;
	}

	private static void onChatMessage(Type warning) {
		if (!Utils.isInDungeons()) {
			CLIENT.gui.setTitle(Component.translatable(warning.key).withStyle(ChatFormatting.RED));
		} else if (SkyblockerConfigManager.get().general.quiverWarning.enableQuiverWarningInDungeons) {
			CLIENT.gui.setTitle(Component.translatable(warning.key).withStyle(ChatFormatting.RED));
			QuiverWarning.warning = warning;
		}
		playQuiverSounds(warning);
	}

	public static void update() {
		if (warning != null && SkyblockerConfigManager.get().general.quiverWarning.enableQuiverWarning && SkyblockerConfigManager.get().general.quiverWarning.enableQuiverWarningAfterDungeon && !Utils.isInDungeons()) {
			Gui inGameHud = CLIENT.gui;
			inGameHud.resetTitleTimes();
			inGameHud.setTitle(Component.translatable(warning.key).withStyle(ChatFormatting.RED));
			playQuiverSounds(warning);
			warning = null;
		}
	}

	private static void playQuiverSounds(Type warning) {
		if (CLIENT.player != null) {
			switch (warning) {
				case Type.FIFTY_LEFT: CLIENT.player.playSound(SoundEvents.NOTE_BLOCK_PLING.value(), 100f, 1f); break;
				case Type.TEN_LEFT: CLIENT.player.playSound(SoundEvents.NOTE_BLOCK_PLING.value(), 100f, 1.5f); break;
				case Type.EMPTY: CLIENT.player.playSound(SoundEvents.ARROW_HIT, 100f, 0.1f); break;
			}
		}
	}

	private enum Type {
		NONE(""),
		FIFTY_LEFT("50Left"),
		TEN_LEFT("10Left"),
		EMPTY("empty");
		private final String key;

		Type(String key) {
			this.key = "skyblocker.quiverWarning." + key;
		}
	}
}
