package com.example.copperadmin.testingadapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Copper Admin on 10/6/2014.
 */
public class SimpleDeserializer implements JsonDeserializer<ArrayList<String>> {

    @Override
    public ArrayList<String> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement dataElement = jsonObject.get("data");
        JsonArray jsonArray = dataElement.getAsJsonArray();

//        String[] urls = new String[jsonArray.size()];
        ArrayList<String> urls = new ArrayList<String>();

        for (int i = 0; i<jsonArray.size();i++){
            JsonElement element = jsonArray.get(i);
            JsonObject elementObject = element.getAsJsonObject();

            if ((elementObject.get("type").getAsString()).equals("image")) {
                JsonObject imagesObject = elementObject.getAsJsonObject("images");
                JsonObject lowResolutionObject = imagesObject.getAsJsonObject("low_resolution");

                urls.add(lowResolutionObject.get("url").getAsString());
            }else{
                urls.add("http://www.britishlegion.org.uk/ImageGen.ashx?width=800&image=/media/2019101/id23055-normandy-66th_-schools-visit-poppy-choice_-pupils-from-london-city-academy.jpg");
            }
        }
        return urls;
    }
}
