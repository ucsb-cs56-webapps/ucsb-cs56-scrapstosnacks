package edu.ucsb.cs56.scrapstosnacks;
import static spark.Spark.port;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.post;



public class Views
{
	public static void display_view(String uriString)
	{
		get("/display",(rq,rs) -> {

			//information of recipes from database
			ArrayList<String> dbrecipes = MongoDB.display_all(uriString);
			Map<String,Object> model = new HashMap<>();
			model.put("dbrecipes", dbrecipes);

			//information of recipes from api
			ArrayList<RecipeModel> recipes = API.display_all();//create arraylist that holds the recipes
			model.put("recipes", recipes); //add recipes to Map attributes
			return new ModelAndView(model, "display.mustache");}, new MustacheTemplateEngine());
	}
	public static void home_view()
	{
		HashMap model = new HashMap();
		get("/", (rq, rs) -> new ModelAndView(model, "main.mustache"), new MustacheTemplateEngine());
	}
	public static void form_view()
	{
		HashMap model = new HashMap();
		get("/form/recipe",(rq,rs) ->new ModelAndView(model, "addRecipe.mustache"), new MustacheTemplateEngine());
	}
	public static void submitted_view(String uriString)
	{
		get("/submitted", (rq, rs) ->{
			HashMap model = new HashMap();
			model.put("recipename", rq.queryParams("recipename"));
			String i = "ingredient";
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(rq.queryParams("recipename"));
			int num = 3;
			for (int j = 0; j < num; j++) {
				String i_t = i+(j+1);
				model.put(i_t, rq.queryParams(i_t));
				temp.add(rq.queryParams(i_t));
			}
			MongoDB.add_document(uriString,MongoDB.createOne(temp));
			return new ModelAndView(model, "submitted_ingredients.mustache");}, new MustacheTemplateEngine());
	}
	public static void search_view(String uriString)
	{
		HashMap model = new HashMap();
		get("/search/recipes", (rq, rs) ->{
			ArrayList<String> temp = MongoDB.searchByName(uriString,rq.queryParams("recipename"));
			int count = 1;
			String p = "recipe";
			for(String s:temp)
			{
				String p_i = p+count;
				model.put(p_i,s);
				count++;
			}

			return new ModelAndView(model, "searchRecipes.mustache");
		}, new MustacheTemplateEngine());
	}
	public static void result_view_byName(String uriString)
	{
		get("/result/byname",(rq, rs) -> {

			Map<String,Object> model = new HashMap<>();
			ArrayList<String> temp = MongoDB.searchByName(uriString,rq.queryParams("recipename"));
			if (temp.size() !=0)
			{
				model.put("recipes",temp);
				return new ModelAndView(model,"searchResults.mustache");
			}
			else
				return new ModelAndView(model, "noResults.mustache");

		}, new MustacheTemplateEngine());
	}

	public static void result_view_byIngredients(String uriString)
	{
		get("/result/byingredients", (rq,rs) ->{

			Map<String,Object> model = new HashMap<>();
			ArrayList<String> temp = MongoDB.searchByIngredients(uriString,rq.queryParams("ingredients"));
			ArrayList<RecipeModel> apiRecipes = API.search_by_ingredients(rq.queryParams("ingredients"));
			if (temp.size() !=0)
			{
				model.put("recipes",temp);
				model.put("apirecipes", apiRecipes);
				return new ModelAndView(model,"searchResults.mustache");
			}
			else if (apiRecipes.size() !=0)
			{
				model.put("recipes",temp);
				model.put("apirecipes", apiRecipes);
				return new ModelAndView(model,"searchResults.mustache");

			}
			else
				return new ModelAndView(model, "noResults.mustache");


		}, new MustacheTemplateEngine());
	}

}
