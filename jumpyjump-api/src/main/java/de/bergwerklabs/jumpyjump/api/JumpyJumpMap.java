package de.bergwerklabs.jumpyjump.api;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class JumpyJumpMap {

    /**
     *
     */
    public Set<String> getBuilder() {
        return builder;
    }

    /**
     *
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public Set<Course> getCourses() {
        return courses;
    }

    /**
     *
     */
    public Set<Material> getCheckpointMaterial() {
        return checkpointMaterial;
    }

    /**
     *
     */
    public Set<Material> getAllowedBlocks() {
        return allowedBlocks;
    }

    private Set<String> builder;
    private Set<Course> courses;
    private Set<Material> checkpointMaterial;
    private Set<Material> allowedBlocks;
    private String name;

    /**
     * @param builder
     * @param courses
     * @param checkpointMaterial
     * @param allowedBlocks
     * @param name
     */
    public JumpyJumpMap(
            @NotNull Set<String> builder,
            @NotNull Set<Course> courses,
            @NotNull Set<Material> checkpointMaterial,
            @NotNull Set<Material> allowedBlocks,
            @NotNull String name
    ) {
        this.builder = builder;
        this.name = name;
        this.courses = courses;
        this.checkpointMaterial = checkpointMaterial;
        this.allowedBlocks = allowedBlocks;
    }

    /**
     *
     * @param object
     * @return
     */
    public static JumpyJumpMap fromJson(@NotNull JsonObject object) {
        Preconditions.checkNotNull(object);
        String name = object.get("name").getAsString();
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

        return new JumpyJumpMap(builders, courses, checkpointMaterial, allowedBlocks, name);
    }

    /**
     *
     * @param array
     * @param consumer
     */
    private static void processArray(JsonArray array, Consumer<JsonElement> consumer) {
        for (JsonElement element : array) {
            consumer.accept(element);
        }
    }
}
