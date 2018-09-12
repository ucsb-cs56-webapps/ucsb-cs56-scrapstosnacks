package edu.ucsb.cs56.scrapstosnacks;

public class RecipeModel {
	public String publisher = "";
	public String f2f_url = "";
	public String title = "";
	public String source_url = "";
	public String recipe_id = "";
	public String image_url = "";
	public String social_rank = "";
	public String publisher_url = "";

	public String getF2f_url() {
		return f2f_url;
	}
	public String getTitle() {
		return title;
	}
	public String getSourceURL() {
		return source_url;
	}
	public String getRecipeID() {
		return recipe_id;
	}
	public String getImageURL() {
		return image_url;
	}
	public String getSocialRank() {
		return social_rank;
	}
	public String getPublisherURL() {
		return publisher_url;
	}

    @Override
    public String toString() {
	String s = "";

	s += f2f_url;
	s += ", ";
	s += title;
	s += ", ";
	s += source_url;
	s += recipe_id;
	s += image_url;
	s += social_rank;
	s += publisher_url;

	return s;
    }

}
