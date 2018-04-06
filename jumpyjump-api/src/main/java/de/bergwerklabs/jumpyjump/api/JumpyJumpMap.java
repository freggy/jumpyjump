package de.bergwerklabs.jumpyjump.api;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.builder.Diff;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Data class that represents a JumpyJump map.
 *
 * @author Yannic Rieger
 */
public class JumpyJumpMap {

    /**
     * Gets the builders that built the map.
     */
    public Set<String> getBuilder() {
        return builder;
    }

    /**
     * Gets the name of the map.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the different courses of this map.
     */
    public Set<Course> getCourses() {
        return courses;
    }

    /**
     * Gets the {@link Material}s that is used for checkpoint detection.
     */
    public Set<Material> getCheckpointMaterial() {
        return checkpointMaterial;
    }

    /**
     * Gets the {@link Material}s where the player is allowed to step on.
     */
    public Set<Material> getAllowedBlocks() {
        return allowedBlocks;
    }

    /**
     * Determines whether or not the players will see each other.
     */
    public boolean isMirror() {
        return isMirror;
    }

    private Set<String> builder;
    private Set<Course> courses;
    private Set<Material> checkpointMaterial;
    private Set<Material> allowedBlocks;
    private String name;
    private Difficulty difficulty;
    private boolean isMirror;

    /**
     * @param builder            the builders that built the map.
     * @param courses            the {@link Material}s where the player is allowed to step on.
     * @param checkpointMaterial the {@link Material} that is used for checkpoint detection.
     * @param allowedBlocks      the {@link Material}s where the player is allowed to step on.
     * @param name               the name of the map.
     */
    private JumpyJumpMap(
            @NotNull Set<String> builder,
            @NotNull Set<Course> courses,
            @NotNull Set<Material> checkpointMaterial,
            @NotNull Set<Material> allowedBlocks,
            @NotNull String name,
            @NotNull Difficulty difficulty,
            boolean isMirror
    ) {
        this.builder = builder;
        this.name = name;
        this.courses = courses;
        this.checkpointMaterial = checkpointMaterial;
        this.allowedBlocks = allowedBlocks;
        this.difficulty = difficulty;
        this.isMirror = isMirror;
    }

    /**
     * Creates a {@code JumpyJumpMap} from JSON.
     *
     * @param object {@link JsonObject} representing a {@code JumpyJumpMap}.
     * @return a {@code JumpyJumpMap}
     */
    public static JumpyJumpMap fromJson(@NotNull JsonObject object) {
        Preconditions.checkNotNull(object);
        String name = object.get("name").getAsString();
        Difficulty difficulty = Difficulty.valueOf(object.get("difficulty").getAsString());
        boolean isMirror = object.get("mirror").getAsBoolean();
        Set<String> builders = new HashSet<>();
        Set<Course> courses = new HashSet<>();
        Set<Material> checkpointMaterial = new HashSet<>();
        Set<Material> allowedBlocks = new HashSet<>();

        object.get("builder").getAsJsonArray().forEach(element -> {
            builders.add(element.getAsString());
        });

        object.get("courses").getAsJsonArray().forEach(element -> {
            courses.add(Course.fromJson(element.getAsJsonObject()));
        });

        object.get("checkpoint-material").getAsJsonArray().forEach(element -> {
            checkpointMaterial.add(Material.getMaterial(element.getAsString()));
        });

        object.get("allowed-blocks").getAsJsonArray().forEach(element -> {
            allowedBlocks.add(Material.getMaterial(element.getAsString()));
        });

        return new JumpyJumpMap(builders, courses, checkpointMaterial, allowedBlocks, name, difficulty, isMirror);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
