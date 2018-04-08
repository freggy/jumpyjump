package de.bergwerklabs.jumpyjump.lobby.config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackUtil;
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
        return new Config(challengeItem, lobby, quickJoin, quickJoinLeave);
    }
}
