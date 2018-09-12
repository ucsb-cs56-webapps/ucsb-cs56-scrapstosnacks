package edu.ucsb.cs56.scrapstosnacks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;


import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.net.HttpURLConnection;


public class API{

	public static ArrayList<RecipeModel> get_recipes(String urlString) {

		ArrayList<RecipeModel> recipes = new ArrayList<>();

		//create url
		URL url = null;
		try {	
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			System.out.println("URL malformed");
		}

		//open connection

		HttpsURLConnection connection = null;
		try {
			connection = (HttpsURLConnection) url.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20130401 Firefox/31.0");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("oh no");
		}


		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			System.out.println("cannot set request method");
		}
		try {
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ahh");
		}
		try {
			int responseCode = connection.getResponseCode();
		} catch (IOException e) {
			System.out.println("response code failed");
		}

		//use scanner to make string thats in that url
		Scanner sc = null;
		try {
			sc = new Scanner(connection.getInputStream());
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("crap");
		}
		String inline = "";
		while (sc.hasNext()) {
			inline += sc.nextLine();
		}
		sc.close();


		//create json object
		JSONObject json = new JSONObject(inline);

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
		return recipes;
	}


	public static ArrayList<RecipeModel> search_by_ingredients(String ingredients) {
		ArrayList<RecipeModel> recipes = new ArrayList<>();
		HashMap<String,String> envVars = MongoDB.getNeededEnvVars(new String []{"API_KEY"});
		String key = envVars.get("API_KEY");
		String urlString = "https://www.food2fork.com/api/search?key=" + key + "&q=" + ingredients + "";
		System.out.println("apiURL=" + urlString);
		return  API.get_recipes(urlString);
	}


	public static ArrayList<RecipeModel> display_all(){

		ArrayList<RecipeModel> recipes = new ArrayList<>();
		HashMap<String,String> envVars = MongoDB.getNeededEnvVars(new String []{"API_KEY"});
		String key = envVars.get("API_KEY");

		String urlString = "https://www.food2fork.com/api/search?key=" + key +"";
		
		return API.get_recipes(urlString);
	}

}
