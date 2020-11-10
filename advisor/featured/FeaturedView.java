package advisor.featured;

import advisor.View;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class FeaturedView extends View {
    public static void show(JsonArray ja, int maxPages, int pageIndex, int page){
        if(!checkPageIndexes(maxPages, pageIndex)){
            JsonObject itemObject;
            for(int i = pageIndex * page; i < (pageIndex*page) + page && i < ja.size(); i++){
                itemObject = ja.get(i).getAsJsonObject();

                JsonObject external_url_object = itemObject.getAsJsonObject("external_urls");
                System.out.println(itemObject.get("name").getAsString() + "\n" + external_url_object.get("spotify").getAsString() + "\n");
            }
            System.out.println("---PAGE " + (pageIndex+1)  + " OF " + maxPages + "---");
        }
    }
}
