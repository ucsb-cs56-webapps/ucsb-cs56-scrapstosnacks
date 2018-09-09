package edu.ucsb.cs56.pconrad;

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

public class MongoDBDemo01 {

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
	
    public static void main(String[] args) {
	
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
    }

    static ArrayList<String> initDatabase(String uriString) {
        ArrayList<String> dbQuery = new ArrayList<>();
        try {
            dbQuery = createDocument(uriString);
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host thrown");
        }
        return dbQuery;
    }

    static String makeString(ArrayList<String> text) {
        String resultString = "";
        for (String s: text) {
            resultString += "<b> " + s + "</b><br/>";
        }
        return resultString;
    }

//	public static void getForm(String s) {
//		ingredients.add(s);
//	}


    public static ArrayList<String> createDocument(String uriString) throws UnknownHostException {
		
        // Create seed data
       /* Recipe re = new Recipe();
	String name = "";
	for(String s:ingredients)
	{
		Ingredient temp = new Ingredient(1,s);
		re.add(temp);
		name += s;
	}
	re.set_name(name);*/
        List<Document> seedData = new ArrayList<Document>();
		
        seedData.add(new Document("recipe_name", "???????")
					 .append("ingredient1", "Deb")
					 .append("song", "You Light Up My Life")
					 .append("weeksAtOne", 10)
					 );
		
        seedData.add(new Document("decade", "1980s")
					 .append("artist", "Olivia Newton-John")
					 .append("song", "Physical")
					 .append("weeksAtOne", 10)
					 );
		
        seedData.add(new Document("decade", "1990s")
					 .append("artist", "Mariah Carey")
					 .append("song", "One Sweet Day")
					 .append("weeksAtOne", 16)
					 );
		
        MongoClientURI uri  = new MongoClientURI(uriString); 
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        
        /*
         * First we'll add a few songs. Nothing is required to create the
         * songs collection; it is created automatically when we insert.
         */
        
        MongoCollection<Document> songs = db.getCollection("songs");
        
        /*
         *  Dropping the table right before the new setup. 
         *  This way you can see the changes on mLab, wihtout duplicates.
         */
		
        songs.drop();
		
        // Note that the insert method can take either an array or a document.
        
        songs.insertMany(seedData);
		
        /*
         * Then we need to give Boyz II Men credit for their contribution to
         * the hit "One Sweet Day".
         */
		
        Document updateQuery = new Document("song", "One Sweet Day");
        songs.updateOne(updateQuery, new Document("$set", new Document("artist", "Mariah Carey ft. Boyz II Men")));
        
        /*
         * Finally we run a query which returns all the hits that spent 10 
         * or more weeks at number 1.
         */
		
        Document findQuery = new Document("weeksAtOne", new Document("$gte",10));
        Document orderBy = new Document("decade", 1);
		
        MongoCursor<Document> cursor = songs.find(findQuery).sort(orderBy).iterator();
        
        ArrayList<String> prettyQuery = new ArrayList<>();
        
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String currSong = "In the " + doc.get("decade") + ", " + doc.get("song") + 
                    " by " + doc.get("artist") + " topped the charts for " + 
                    doc.get("weeksAtOne") + " straight weeks.";
                prettyQuery.add(currSong);
            }
        } finally {
            cursor.close();
        }
        
        // Only close the connection when your app is terminating
        
        client.close();
		
        return prettyQuery;
    }

}
