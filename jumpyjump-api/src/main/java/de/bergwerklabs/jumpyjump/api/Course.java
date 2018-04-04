package de.bergwerklabs.jumpyjump.api;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Contains data for a players jump and run course.
 *
 * @author Yannic Rieger
 */
public class Course {

    /**
     * Gets the checkpoints for this course.
     */
    public Location getCheckpoint(Vector vector) {
        this.checkpoints.keySet().forEach(System.out::println);
        System.out.println(vector.toString());
        return this.checkpoints.get(vector.toString());
    }

    /**
     * Gets the spawn for this course.
     */
    public Location getSpawn() {
        return spawn;
    }

    private Map<String, Location> checkpoints;
    private Location spawn;

    /**
     * @param checkpoints
     * @param spawn
     */
    public Course(Map<String, Location> checkpoints, Location spawn) {
        this.checkpoints = checkpoints;
        this.spawn = spawn;
    }

    /**
     * Creates an {@code Course} from JSON.
     *
     * @param object {@link JsonObject} representing a {@code Course}.
     * @return the {@code Course}
     */
    public static Course fromJson(JsonObject object) {
        Location spawn = locationFromJson(object.get("spawn").getAsJsonObject());
        Map<String, Location> checkpoints = new HashMap<>();
        object.get("checkpoints").getAsJsonArray().forEach(element -> {
            Location location = locationFromJson(element.getAsJsonObject());
            checkpoints.put(location.toVector().toString(), location);
        });
        return new Course(checkpoints, spawn);
    }

    /**
     * Creates a location from JSON.
     *
     * @param json JsonObject representing the Location
     * @return Location created from JSON.
     */
    private static Location locationFromJson(@NotNull JsonObject json) {
        if (!json.has("x"))     throw new IllegalStateException("Parameter x is not present");
        if (!json.has("y"))     throw new IllegalStateException("Parameter y is not present");
        if (!json.has("z"))     throw new IllegalStateException("Parameter z is not present");
        if (!json.has("world")) throw new IllegalStateException("Parameter world is not present");

        Double x = json.get("x").getAsDouble();
        Double y = json.get("y").getAsDouble();
        Double z = json.get("z").getAsDouble();
        String world = json.get("world").getAsString();

        Location location = new Location(Bukkit.getWorld(world), x, y, z);

        if (json.has("direction"))
            location.setDirection(vectorFromJson(json.get("direction").getAsJsonObject()));

        if (json.has("yaw"))
            location.setYaw(json.get("yaw").getAsFloat());

        if (json.has("pitch"))
            location.setPitch(json.get("pitch").getAsFloat());

        return location;
    }

    /**
     * Creates a vector from JSON
     *
     * @param json JsonObject representing the vector.
     * @return vector created from JSON.
     */
    private static Vector vectorFromJson(@NotNull JsonObject json) {
        if (!json.has("x")) throw new IllegalStateException("Parameter x is not present");
        if (!json.has("y")) throw new IllegalStateException("Parameter y is not present");
        if (!json.has("z")) throw new IllegalStateException("Parameter z is not present");
        return new Vector(json.get("x").getAsDouble(), json.get("y").getAsDouble(), json.get("z").getAsDouble());
    }
}
