package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView alsoKnown, ingredients, place, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnown = findViewById(R.id.also_known_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        place = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //run the method and pass the sandwich object
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.no_image_svg)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //If Also Known attribute is available,we set it in the textView
        if (sandwich.getAlsoKnownAs() != null) {
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                alsoKnown.append(sandwich.getAlsoKnownAs().get(i));
            }
        } else {
            alsoKnown.setText("-");
        }

        //if ingredients are available we set them in the text view
        if (sandwich.getIngredients().size() > 0 && sandwich.getIngredients() != null) {
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                ingredients.append(sandwich.getIngredients().get(i));
            }
        } else {
            ingredients.setText("-");
        }

        //we dont know if place is available so we make an if/else statement
        if (sandwich.getPlaceOfOrigin().equals("")) {
            place.setText("-");
        } else {
            place.setText(sandwich.getPlaceOfOrigin());
        }

        //we dont know if description is available so we make an if/else statement
        if(sandwich.getDescription().equals("")){
            description.setText("");
        }else{
            description.setText(sandwich.getDescription());
        }

    }
}
