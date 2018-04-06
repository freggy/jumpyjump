package de.bergwerklabs.jumpyjump.api;

import com.google.gson.JsonObject;
import de.bergwerklabs.framework.commons.spigot.location.LocationUtil;
import org.bukkit.Location;

import java.util.LinkedList;
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
    public Location inspectNextCheckpoint() {
        return this.checkpoints.peek();
    }

    public Location pollNextCheckpoint() {
        return this.checkpoints.poll();
    }

    /**
     * Gets the spawn for this course.
     */
    public Location getSpawn() {
        return spawn;
    }

    /**
     * Gets the end of this course.
     */
    public Location getEnd() {
        return end;
    }

    private Queue<Location> checkpoints;
    private Location spawn;
    private Location end;

    /**
     * @param checkpoints
     * @param spawn
     */
    public Course(Queue<Location> checkpoints, Location spawn, Location end) {
        this.checkpoints = checkpoints;
        this.spawn = spawn;
        this.end = end;
    }

    /**
     * Creates an {@code Course} from JSON.
     *
     * @param object {@link JsonObject} representing a {@code Course}.
     * @return the {@code Course}
     */
    public static Course fromJson(JsonObject object) {
        Location spawn = LocationUtil.locationFromJson(object.get("spawn").getAsJsonObject());
        Location end = LocationUtil.locationFromJson(object.get("end").getAsJsonObject());
        Queue<Location> checkpoints = new LinkedList<>();
        object.get("checkpoints").getAsJsonArray().forEach(element -> {
            Location location = LocationUtil.locationFromJson(element.getAsJsonObject());
            checkpoints.add(location);
        });
        return new Course(checkpoints, spawn, end);
    }
}
