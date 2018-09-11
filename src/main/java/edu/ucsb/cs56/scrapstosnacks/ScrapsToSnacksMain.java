package edu.ucsb.cs56.scrapstosnacks;

import static spark.Spark.port;

import org.apache.log4j.Logger;


import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Simple example of using Mustache Templates
 *
 */

//database imports
import java.net.UnknownHostException;
import java.util.ArrayList;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.List;

//json imports
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class ScrapsToSnacksMain {


	public static final String CLASSNAME="ScrapsToSnacksMain";
	
	public static final Logger log = Logger.getLogger(CLASSNAME);
	public static void main(String[] args) {

        port(getHerokuAssignedPort());
	// initializing database
        HashMap<String,String> envVars =
		MongoDB.getNeededEnvVars(new String []{
                                        "MONGODB_USER",
                                        "MONGODB_PASS",
                                        "MONGODB_NAME",
                                        "MONGODB_HOST",
                                        "MONGODB_PORT"
                                });
	String uriString = MongoDB.mongoDBUri(envVars);
	MongoDB.initDatabase(uriString);
	Views.home_view();	
	Views.form_view();
	Views.submitted_view(uriString);
	Views.search_view(uriString);
	Views.display_view(uriString);
	Views.result_view_byName(uriString);
	Views.result_view_byIngredients(uriString);

	//trying to implement api
	
	ArrayList<RecipeModel> recipes = new ArrayList<>(); //create arraylist that holds the recipes
	get("/results", (req, res) -> {
                Map<String, Object> attributes = new HashMap<>();

		//String key = System.getenv("F2F_KEY");
		String urlString = "https://www.food2fork.com/api/search?key=83fab007b63aaf5344a8a877f54c2c5b";
		
		//creat url
		 URL url = new URL(urlString);

		 //open connection
            	HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		//retrieving..
            	connection.setRequestMethod("GET");
            	connection.connect();

		//issue idk
           	int responsecode = connection.getResponseCode();
            	if (responsecode != 200) { System.out.println("oh no");}
		
		//use scanner to make string thats in that url
		Scanner sc = new Scanner(url.openStream());
		String inline = "";
		while (sc.hasNext()) {
			inline += sc.nextLine();
            	}
            	sc.close();
		

		//create json object
		JSONObject json = new JSONObject(inline);

		json = (JSONObject) json.get("count"); 
		
		//get array of pets from the JSON.  iterate through and store in the model
		JSONArray json_recipes = (JSONArray) json.get("recipes");
		for (Object o : json_recipes) {
			RecipeModel recipe = new RecipeModel();
			JSONObject json_recipe = (JSONObject) o;
			
			recipe.publisher = json_recipe.get("publisher").toString();
			recipe.f2f_url = json_recipe.get("f2f_url").toString();
			recipe.title = json_recipe.get("title").toString();
			recipe.source_url = json_recipe.get("source_url").toString();
			recipe.recipe_id = json_recipe.get("recipe_id").toString();
			recipe.image_url = json_recipe.get("image_url").toString();
			recipe.social_rank = json_recipe.get("social_rank").toString();
			recipe.publisher_url= json_recipe.get("publisher_url").toString();

			recipes.add(recipe); //add model to arrayList recipes
		}
		attributes.put("recipes", recipes); //add recipes to Map attributes

		//sendn it all out
		return new ModelAndView(attributes, "sample.mustache");}, new MustacheTemplateEngine());
	}

	public static int getHerokuAssignedPort() {
        	ProcessBuilder processBuilder = new ProcessBuilder();
        	if (processBuilder.environment().get("PORT") != null) {
            	return Integer.parseInt(processBuilder.environment().get("PORT"));
        	}
        	return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    	}
}	
