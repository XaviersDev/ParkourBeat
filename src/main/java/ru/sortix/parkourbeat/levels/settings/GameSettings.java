package ru.sortix.parkourbeat.levels.settings;

import java.util.UUID;
import javax.annotation.Nullable;
import lombok.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class GameSettings {
    private final @NonNull UUID uniqueId;
    private final @Nullable String uniqueName;
    private final int uniqueNumber;

    private final @NonNull UUID ownerId;
    private final @NonNull String ownerName;

    @Setter
    private @NonNull String displayName;

    private final long createdAtMills;
    private @Nullable Song song;

    @NonNull public String getDisplayName() {
        return this.displayName + ChatColor.RESET;
    }

    @NonNull public String getRawDisplayName() {
        return this.displayName;
    }

    public void setSong(@NonNull Song song) {
        this.song = song;
    }

    public boolean isOwner(@NonNull UUID playerId) {
        return this.ownerId.equals(playerId);
    }

    public boolean isOwner(@NonNull CommandSender sender, boolean bypassForAdmins, boolean bypassMsg) {
        if (sender instanceof Player) {
            if (this.ownerId.equals(((Player) sender).getUniqueId())) {
                return true;
            }
            if (bypassForAdmins && sender.hasPermission("parkourbeat.restrictions.bypass")) {
                if (bypassMsg) sender.sendMessage("Использован обход прав, поскольку вы являетесь оператором сервера");
                return true;
            }
            return false;
        }
        return sender instanceof ConsoleCommandSender;
    }
}
