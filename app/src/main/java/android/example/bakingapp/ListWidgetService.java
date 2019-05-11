package android.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        Context context;
        List<Ingredients> ingredient;

        public ListRemoteViewsFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

            SharedPreferences preferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = preferences.getString("recipeIngredients", null);
            Type type = new TypeToken<List<Ingredients>>() {
            }.getType();
            ingredient = gson.fromJson(json, type);

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredient.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_items);
            views.setTextViewText(R.id.widget_items, String.valueOf(i + 1) + "- " + ingredient.get(i).getIngredient() +
                    ", " + ingredient.get(i).getQuantity() + " " + ingredient.get(i).getMeasure());
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
