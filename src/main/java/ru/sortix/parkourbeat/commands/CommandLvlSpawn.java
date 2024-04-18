package ru.sortix.parkourbeat.commands;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import ru.sortix.parkourbeat.ParkourBeat;
import ru.sortix.parkourbeat.activity.type.EditActivity;
import ru.sortix.parkourbeat.world.TeleportUtils;

import static ru.sortix.parkourbeat.constant.PermissionConstants.COMMAND_PERMISSION;

@Command(
    name = "lvlspawn",
    aliases = {"levelspawn"})
@RequiredArgsConstructor
public class CommandLvlSpawn {

    private final ParkourBeat plugin;

    @Execute
    @Permission(COMMAND_PERMISSION + ".lvlspawn")
    public void onCommand(@Context Player player) {
        if (!(plugin.getActivityManager().getActivity(player) instanceof EditActivity)) {
            player.sendMessage("Вы не в редакторе");
            return;
        }

        EditActivity activity = (EditActivity) this.plugin.getActivityManager().getActivity(player);
        TeleportUtils.teleportAsync(this.plugin, player, activity.getLevel().getLevelSettings().getWorldSettings().getSpawn());
    }
}