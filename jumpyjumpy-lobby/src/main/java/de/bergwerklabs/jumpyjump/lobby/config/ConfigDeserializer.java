package de.bergwerklabs.jumpyjump.lobby.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackUtil;
import de.bergwerklabs.framework.commons.spigot.location.LocationUtil;
import de.bergwerklabs.util.NPC;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class ConfigDeserializer implements JsonDeserializer<Config> {

    @Override
    public Config deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)  {
        JsonObject object = jsonElement.getAsJsonObject();
        ItemStack challengeItem = ItemStackUtil.createItemStackFromJson(object.get("challenge-item").getAsJsonObject());
        ItemStack quickJoin = ItemStackUtil.createItemStackFromJson(object.get("quick-join-item").getAsJsonObject());
        ItemStack lobby = ItemStackUtil.createItemStackFromJson(object.get("lobby-item").getAsJsonObject());
        ItemStack quickJoinLeave = ItemStackUtil.createItemStackFromJson(object.get("quick-join-leave-item").getAsJsonObject());

        NPC quickJoinNpc = this.npcFromJson(object.get("quick-join-npc").getAsJsonObject());
        Location statsNpcLocation = LocationUtil.locationFromJson(object.get("stats-npc-location").getAsJsonObject());

        return new Config(challengeItem, lobby, quickJoin, quickJoinLeave, statsNpcLocation, quickJoinNpc);
    }

    private NPC npcFromJson(JsonObject json) {
        boolean displayFirst = false;
        boolean displaySecond = false;
        String line1 = "", line2 = "", signature = "", value = "";

        if (json.has("line1")) {
            line1 = json.get("line1").getAsString();
            displayFirst = true;
        }

        if (json.has("line2")) {
            line2 = json.get("line2").getAsString();
            displaySecond = true;
        }

        if (json.has("signature")) {
            signature = json.get("signature").getAsString();
        }

        if (json.has("value")) {
            value = json.get("value").getAsString();
        }

        Location location = LocationUtil.locationFromJson(json.get("location").getAsJsonObject());

        try {
            NPC npc = new NPC(line1, line2, displayFirst, displaySecond, location);
            if (!value.equals("") && !signature.equals("")) {
                npc.updateSkin(value, signature);
            }
            return npc;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
