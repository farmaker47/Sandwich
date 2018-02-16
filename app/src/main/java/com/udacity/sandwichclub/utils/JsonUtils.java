package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = null;

        try {
            JSONObject root = new JSONObject(json);
            JSONObject nameRoot = root.getJSONObject("name");
            //Getting the name of Sandwich to set it in ToolBar
            String name = nameRoot.getString("mainName");

            //Getting the Also Known As attribute from the Json Array
            String alsoKnownAs = "";
            ArrayList<String> alsoKnownList = null;
            if (nameRoot.has("alsoKnownAs")) {
                JSONArray alsoKnown = nameRoot.getJSONArray("alsoKnownAs");
                if (alsoKnown.length() > 0) {
                    for (int j = 0; j < alsoKnown.length(); j++) {
                        alsoKnownAs = alsoKnown.getString(j);
                        alsoKnownList = new ArrayList<>();
                        alsoKnownList.add(alsoKnownAs);
                    }
                    Log.d("ALSOKNOWN", alsoKnownAs);
                }
            }

            //Getting the place attribute
            String place = root.getString("placeOfOrigin");
            //Getting the descritpion
            String descripion = root.getString("description");
            //Getting the image url to load it with Picasso
            String image = root.getString("image");

            //Getting the ingredients
            String ingredients = "";
            ArrayList<String> ingredientsList = null;
            if (root.has("ingredients")) {
                JSONArray ingredientsRoot = root.getJSONArray("ingredients");
                if (ingredientsRoot.length() > 0) {
                    for (int j = 0; j < ingredientsRoot.length(); j++) {
                        ingredients = ingredientsRoot.getString(j);
                        ingredientsList = new ArrayList<>();
                        ingredientsList.add(ingredients);
                    }
                    Log.d("ingredients", ingredients);
                }
            }

            //Inserting all atributes into sandwich object
            sandwich = new Sandwich(name, alsoKnownList, place, descripion, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JsonUtils", "Problem parsing JSON results", e);
        }

        return sandwich;
    }
}
