package ru.sortix.parkourbeat.commands;

import java.util.Collection;
import java.util.List;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.sortix.parkourbeat.ParkourBeat;
import ru.sortix.parkourbeat.activity.ActivityManager;
import ru.sortix.parkourbeat.activity.type.EditActivity;
import ru.sortix.parkourbeat.levels.LevelsManager;
import ru.sortix.parkourbeat.levels.settings.GameSettings;

public class CommandEdit extends ParkourBeatCommand implements TabCompleter {
    private final LevelsManager levelsManager;

    public CommandEdit(@NonNull ParkourBeat plugin) {
        super(plugin);
        this.levelsManager = plugin.get(LevelsManager.class);
    }

    @Override
    public boolean onCommand(
            @NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (!sender.hasPermission("parkourbeat.command.edit")) {
            sender.sendMessage("Недостаточно прав");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Команда только для игроков");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("Недостаточно аргументов! Используйте: /edit <имя уровня>");
            return false;
        }

        String levelName = String.join(" ", args);
        GameSettings settings = this.levelsManager.findLevelSettingsByName(levelName);

        if (settings == null) {
            sender.sendMessage("Уровень \"" + levelName + "\" не найден!");
            return true;
        }

        if (!settings.isOwner(sender, true, true)) {
            player.sendMessage("Вы не являетесь владельцем этого уровня!");
            return true;
        }

        this.levelsManager.loadLevel(settings.getLevelId()).thenAccept(level -> {
            if (level == null) {
                sender.sendMessage("Не удалось загрузить данные уровня");
                return;
            }

            if (level.isEditing()) {
                player.sendMessage("Данный уровень уже редактируется");
                return;
            }

            ActivityManager activityManager = this.plugin.get(ActivityManager.class);

            Collection<Player> playersOnLevel = activityManager.getPlayersOnTheLevel(level);
            playersOnLevel.removeIf(player1 -> settings.isOwner(player1, true, true));

            if (!playersOnLevel.isEmpty()) {
                player.sendMessage("Нельзя редактировать уровень, на котором находятся игроки");
                return;
            }

            EditActivity.createAsync(this.plugin, player, level).thenAccept(editActivity -> {
                if (editActivity == null) {
                    player.sendMessage("Не удалось запустить редактор уровня");
                    return;
                }
                activityManager.setActivity(player, editActivity);
            });
        });
        return true;
    }

    @Override
    public List<String> onTabComplete(
            @NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (args.length == 0) return null;
        return this.levelsManager.getValidLevelNames(String.join(" ", args), sender);
    }
}
