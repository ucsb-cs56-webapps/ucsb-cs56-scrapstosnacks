package edu.ucsb.cs56.scrapstosnacks;

/*
 * Some portions:
 * Copyright (c) 2017 ObjectLabs Corporation
 * Distributed under the MIT license - http://opensource.org/licenses/MIT
 *
 * Written with mongo-3.4.2.jar
 * Documentation: http://api.mongodb.org/java/
 * A Java class connecting to a MongoDB database given a MongoDB Connection URI.
 */

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.port;

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
import java.util.ArrayList;
import java.util.List;


/**
 * Hello world!
 *
 */

public class MongoDB {

  /**
       return a HashMap with values of all the environment variables
       listed; print error message for each missing one, and exit if any
       of them is not defined.
    */
   public static ArrayList<String> ingredients = new ArrayList<String>();
    public static HashMap<String,String> getNeededEnvVars(String [] neededEnvVars) {

        ProcessBuilder processBuilder = new ProcessBuilder();
    		
		HashMap<String,String> envVars = new HashMap<String,String>();
		
		boolean error=false;		
		for (String k:neededEnvVars) {
			String v = processBuilder.environment().get(k);
			if ( v!= null) {
				envVars.put(k,v);
			} else {
				error = true;
				System.err.println("Error: Must define env variable " + k);
			}
        }
		
		if (error) { System.exit(1); }

		System.out.println("envVars=" + envVars);
		return envVars;	 
    }
	
	public static String mongoDBUri(HashMap<String,String> envVars) {

		System.out.println("envVars=" + envVars);
		
		// mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
		String uriString = "mongodb://" +
			envVars.get("MONGODB_USER") + ":" +
			envVars.get("MONGODB_PASS") + "@" +
			envVars.get("MONGODB_HOST") + ":" +
			envVars.get("MONGODB_PORT") + "/" +
			envVars.get("MONGODB_NAME");
		System.out.println("uriString=" + uriString);
		return uriString;
	}
	
 /*   public static void main(String[] args) {
	
		HashMap<String,String> envVars =  
			getNeededEnvVars(new String []{
					"MONGODB_USER",
					"MONGODB_PASS",
					"MONGODB_NAME",
					"MONGODB_HOST",
					"MONGODB_PORT"				
				});
		
		String uriString = mongoDBUri(envVars);
			
        ArrayList<String> dbText = initDatabase(uriString);
        final String displayText = makeString(dbText);
        port(getHerokuAssignedPort());
		spark.Spark.get("/", (req, res) -> displayText);
		System.out.println("Spark is listening for connections...");
	}
	
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }*/

    static void initDatabase(String uriString) {
        try {
             MongoDB.initCollection(uriString);
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host thrown");
        }
    }

    static String makeString(ArrayList<String> text) {
        String resultString = "";
        for (String s: text) {
            resultString += "<b> " + s + "</b><br/>";
        }
        return resultString;
    }

	public static void getForm(String s) {
		ingredients.add(s);
	}


    public static void initCollection (String uriString) throws UnknownHostException {
        MongoDatabase db = connect_database(uriString); //get database access
        MongoCollection<Document> songs = db.getCollection("recipes");
        
        // Only close the connection when your app is terminating
        
     //   client.close();
	disconnect_database(uriString);
		
    }
    public static MongoDatabase connect_database(String uriString)
    {
        MongoClientURI uri  = new MongoClientURI(uriString); 
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
	return db;
    }
    public static void disconnect_database(String uriString)
    {
	MongoClientURI uri  = new MongoClientURI(uriString); 
        MongoClient client = new MongoClient(uri);
	client.close();
    }
    public static void add_document(String uriString,Document a)
    {
	MongoDatabase db = MongoDB.connect_database(uriString);
        MongoCollection<Document> songs = db.getCollection("recipes");
	songs.insertOne(a);
    }
    public static Document createOne(ArrayList<String> t)
    {
	    ArrayList temp = new ArrayList();
	    for(int i=1;i<t.size();i++)
	    {
		    temp.add(t.get(i));
	    }
	    return (new Document(
			    "recipename",t.get(0))
			    .append("ingredient",temp)
			    .append("ready","y")
			    );
    }
	    

/*    public static void delete_document(String uriString, String recipe_name)
    {
	MongoDatabase db = MongoDB.connect_database(uriString);
	MongoCollection<Document> songs = db.getCollection("recipes");
	db.songs.deleteOne({"recipename : "+recipe_name});
    }*/

    public static ArrayList<String> display_all (String uriString)
    {
	MongoDatabase db = MongoDB.connect_database(uriString);
	MongoCollection<Document> songs = db.getCollection("recipes");
	Document findQuery = new Document("ready","y");
	MongoCursor<Document> cursor = songs.find(findQuery).iterator();
	ArrayList<String> result = new ArrayList<String>();
        try 
	{
            	while (cursor.hasNext()) 
		{
               		Document doc = cursor.next();
			String recipe = "The recipe is: "+ doc.get("recipename");
			for(String s:(ArrayList<String>)(doc.get("ingredient")))
			{
				recipe += ("\nIngredient: " + s);
			}
			result.add(recipe);
		}
	}
	finally
		{
    		cursor.close();
		}
	return result;
    }
    public static ArrayList<String> searchByName(String uriString,String recipe_name)
    {
	MongoDatabase db = MongoDB.connect_database(uriString);
	MongoCollection<Document> songs = db.getCollection("recipes");
	ArrayList<String> result = new ArrayList<String>();
	Document findQuery = new Document("recipename",recipe_name);
        MongoCursor<Document> cursor = songs.find(findQuery).iterator();
        try 
	{
		while (cursor.hasNext()) 
		{
               		Document doc = cursor.next();
			String recipe = "the recipe is: "+ doc.get("recipename");
			for(String s:(ArrayList<String>)(doc.get("ingredient")))
			{
				recipe += ("\ningredient: " + s);
			}
			result.add(recipe);
		}
	}
	finally
	{
	    cursor.close();
	}
	return result;
    }

    public static ArrayList<String> searchByIngredients(String uriString, String ingredients)
    {
	    /*
	    MongoDatabase db = MongoDB.connect_database(uriString);
	    MongoCollection<Document> songs = db.getCollection("recipes");
	    ArrayList<String> result = new ArrayList<String>();
	    ArrayList<String> i = (ArrayList<String>)(Arrays.asList(ingredients.split(","))); //make an arraylist of strins of the ingredients
	    Document findQuery = new Document("ingredients", i);
	    MongoCursor<Document> cursor = songs.find(findQuery).iterator();
	    try
	    {
		    while (cursor.hasNext())
		    {
			    Document doc = cursor.next();
			    String recipe = "the recipe is: "+ doc.get("recipename");
			for(String s:(ArrayList<String>)(doc.get("ingredient")))
			{
				recipe += ("\ningredient: " + s);
			}
			result.add(recipe);
		}
	}
	finally
	{
	    cursor.close();
	}
	return result;
*/
	    ArrayList<String> test = new ArrayList<String>("hello");
	    return test;
    }
	    
}
