package edu.ucsb.cs56.scrapstosnacks;


import static spark.Spark.port;

import org.apache.log4j.Logger;

import java.util.HashMap;


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


	}

	public static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
	}
}	
