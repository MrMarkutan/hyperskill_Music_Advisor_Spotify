package advisor.newSongs;

import advisor.View;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class NewSongsView extends View {
    public static void show(JsonArray ja, int maxPages, int pageIndex, int page) {
        if (!checkPageIndexes(maxPages, pageIndex)) {
            JsonObject itemObject;
            for (int i = pageIndex * page; i < (pageIndex * page) + page && i < ja.size(); i++) {
                itemObject = ja.get(i).getAsJsonObject();
                JsonObject external_url_object = itemObject.getAsJsonObject("external_urls");
                List<String> show_artist = new ArrayList<>();
                JsonArray artists = itemObject.getAsJsonArray("artists");
                for (JsonElement artist : artists) {
                    JsonObject name = artist.getAsJsonObject();
                    show_artist.add(name.get("name").getAsString());
                }
                System.out.println(itemObject.get("name").getAsString() + "\n" +
                        show_artist + "\n"
                        + external_url_object.get("spotify").getAsString() + "\n");
            }
            System.out.println("---PAGE " + (pageIndex + 1) + " OF " + maxPages + "---");
        }
    }
}
