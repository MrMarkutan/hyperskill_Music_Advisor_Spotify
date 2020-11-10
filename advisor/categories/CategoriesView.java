package advisor.categories;

import advisor.View;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CategoriesView extends View {
    public static void show(JsonArray ja, int maxPages, int pageIndex, int page){
        if(!checkPageIndexes(maxPages, pageIndex)){
            JsonObject itemObject;
            for(int i = pageIndex * page; i < (pageIndex*page) + page && i < ja.size(); i++) {
                itemObject = ja.get(i).getAsJsonObject();

                JsonObject category_object = itemObject.getAsJsonObject();
                System.out.println(category_object.get("name").getAsString());
            }
            System.out.println("---PAGE " + (pageIndex+1)  + " OF " + maxPages + "---");
        }
    }
}
