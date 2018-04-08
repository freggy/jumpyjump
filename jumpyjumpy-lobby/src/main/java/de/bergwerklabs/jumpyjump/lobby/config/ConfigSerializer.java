package de.bergwerklabs.jumpyjump.lobby.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackUtil;

import java.lang.reflect.Type;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class ConfigSerializer implements JsonSerializer<Config> {

    @Override
    public JsonElement serialize(Config config, Type type, JsonSerializationContext jsonSerializationContext) {
        System.out.println("hello");
        JsonObject jsonObject = new JsonObject();

        System.out.println(ItemStackUtil.itemStackToJson(config.getLobbyItem()));
        System.out.println(ItemStackUtil.itemStackToJson(config.getChallengeItem()));
        System.out.println(ItemStackUtil.itemStackToJson(config.getQuickJoinItem()));

        jsonObject.add("lobby-item", ItemStackUtil.itemStackToJson(config.getLobbyItem()));
        jsonObject.add("challenge-item", ItemStackUtil.itemStackToJson(config.getChallengeItem()));
        jsonObject.add("quick-join-item", ItemStackUtil.itemStackToJson(config.getQuickJoinItem()));
        jsonObject.add("quick-join-leave-item", ItemStackUtil.itemStackToJson(config.getLeaveQuickJoin()));
        return jsonObject;
    }
}
