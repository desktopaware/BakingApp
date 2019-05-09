package android.example.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipes implements Parcelable {

    private String id;
    private String name;
    private List<Ingredients> ingredients;
    private List<Steps> steps;
    private String servings;
    private String image;

    public Recipes(String id, String name, List<Ingredients> ingredients, List<Steps> steps, String servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    protected Recipes(Parcel in) {
        id = in.readString();
        name = in.readString();
        ingredients = in.readArrayList(Ingredients.class.getClassLoader());
        steps = in.readArrayList(Steps.class.getClassLoader());
        servings = in.readString();
        image = in.readString();
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeList(steps);
        parcel.writeString(servings);
        parcel.writeString(image);
    }
}
