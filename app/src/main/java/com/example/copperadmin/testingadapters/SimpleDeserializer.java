package com.example.copperadmin.testingadapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Copper Admin on 10/6/2014.
 */
public class SimpleDeserializer implements JsonDeserializer<String[]> {

    @Override
    public String[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {




        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement dataElement = jsonObject.get("data");
        JsonArray jsonArray = dataElement.getAsJsonArray();

        String[] urls = new String[jsonArray.size()];

        for (int i = 0; i<urls.length;i++){
            JsonElement element = jsonArray.get(i);
            JsonObject elementObject = element.getAsJsonObject();

            if ((elementObject.get("type").getAsString()).equals("image")) {
                JsonObject imagesObject = elementObject.getAsJsonObject("images");
                JsonObject lowResolutionObject = imagesObject.getAsJsonObject("low_resolution");

                urls[i] = lowResolutionObject.get("url").getAsString();
            }else{
                urls[i] = "http://www.britishlegion.org.uk/ImageGen.ashx?width=800&image=/media/2019101/id23055-normandy-66th_-schools-visit-poppy-choice_-pupils-from-london-city-academy.jpg";
            }
        }
        return urls;
    }
}
