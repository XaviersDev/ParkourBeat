package ru.sortix.parkourbeat.levels;

import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.text.Component;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import ru.sortix.parkourbeat.data.Settings;
import ru.sortix.parkourbeat.levels.settings.LevelSettings;
import ru.sortix.parkourbeat.world.Cuboid;

import java.util.UUID;

@Getter
public class Level {
    private final @NonNull LevelSettings levelSettings;
    private final @NonNull World world;
    private final @NonNull Cuboid cuboid;
    private boolean isEditing = false;

    public Level(@NonNull LevelSettings levelSettings, @NonNull World world) {
        this.levelSettings = levelSettings;
        this.world = world;
        DirectionChecker.Direction direction = this.levelSettings.getWorldSettings().getDirection();
        this.cuboid = Settings.getLevelFixedEditableArea().get(direction);
        if (this.cuboid == null) {
            throw new IllegalArgumentException("Not fond config of direction " + direction);
        }
    }

    public void setEditing(boolean isEditing) {
        this.isEditing = isEditing;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Level)) return false;
        return ((Level) other).getUniqueId().equals(this.getUniqueId());
    }

    @NonNull
    public Component getDisplayName() {
        return this.levelSettings.getGameSettings().getDisplayName();
    }

    @NonNull
    public UUID getUniqueId() {
        return this.levelSettings.getGameSettings().getUniqueId();
    }

    @NonNull
    public Location getSpawn() {
        return this.levelSettings.getWorldSettings().getSpawn();
    }

    public boolean isLocationInside(@NonNull Location location) {
        if (location.getWorld() != this.world) return false;
        return this.cuboid.isInside(location);
    }

    public boolean isPositionInside(double x, double y, double z) {
        return this.cuboid.isInside(x, y, z);
    }

    @SuppressWarnings({"PointlessBitwiseExpression", "OctalInteger", "RedundantIfStatement"})
    public boolean isChunkInside(@NonNull Chunk chunk) {
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        if (this.isPositionInside((chunkX << 4) | 00, 0, (chunkZ << 4) | 00)) return true;
        if (this.isPositionInside((chunkX << 4) | 15, 0, (chunkZ << 4) | 00)) return true;
        if (this.isPositionInside((chunkX << 4) | 00, 0, (chunkZ << 4) | 15)) return true;
        if (this.isPositionInside((chunkX << 4) | 15, 0, (chunkZ << 4) | 15)) return true;

        return false;
    }
}
