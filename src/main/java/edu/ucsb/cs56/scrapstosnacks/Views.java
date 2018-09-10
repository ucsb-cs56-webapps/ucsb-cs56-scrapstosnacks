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
                ArrayList<String> recipes = MongoDB.display_all(uriString);
                get("/display",(rq,rs) -> {
                        Map<String,Object> model = new HashMap<>();
			model.put("recipes",recipes);
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
		       return	new ModelAndView(model, "searchRecipes.mustache");}, new MustacheTemplateEngine());
	}
	public static void result_view_byName(String uriString)
	{
		get("/result/byname",(rq, rs) ->
				{
					Map<String,Object> model = new HashMap<>();
					model.put("recipes",MongoDB.searchByName(uriString,rq.queryParams("recipename")));
				return new ModelAndView(model,"searchResultsByName.mustache");}, new MustacheTemplateEngine());
	}

}
