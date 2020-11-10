package advisor;

import advisor.categories.CategoriesModel;
import advisor.categories.CategoriesView;
import advisor.featured.FeaturedModel;
import advisor.featured.FeaturedView;
import advisor.newSongs.NewSongsModel;
import advisor.newSongs.NewSongsView;
import advisor.playlists.PlaylistsModel;
import advisor.playlists.PlaylistsView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.HttpServer;

import java.util.Scanner;

public class Controller {
    private static AccessToken accessToken;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(AccessToken accessToken) {
        Controller.accessToken = accessToken;
    }

    Scanner scanner;
    String resource;
    String access;
    int page;

    private static int pageIndex;
    private int maxPageIndex;
    static HttpServer server;

    char playCategory = 'O';
    String genre;

    public static int getPageIndex() {
        return pageIndex;
    }

    public static void setPageIndex(int pageIndex) {
        Controller.pageIndex = pageIndex;
    }

    public void setMaxPageIndex(JsonArray arraySize){
        if (arraySize.get(0).isJsonArray()){
            JsonArray array = new JsonArray();
            for (JsonElement element: arraySize){
                JsonArray ja = element.getAsJsonArray();
                for (JsonElement el: ja) {
                    array.add(el);
                }
            }
            if (array.size() % page != 0){
                maxPageIndex = (array.size()/page)+1;
            } else {
                maxPageIndex = array.size()/page;
            }
        } else {
            if(arraySize.size() % page !=  0) {
                maxPageIndex = (arraySize.size()/page)+1;
            } else {
                maxPageIndex = arraySize.size()/page;
            }
        }
    }

    public int getMaxPageIndex(){
        return maxPageIndex;
    }

    public Controller(String access, String resource, int page){
        this.access = access;
        this.resource = resource;
        this.page = page;

        scanner = new Scanner(System.in);
    }

    void start(){
        boolean checkAuth = false;
        while (true){
            String input = scanner.nextLine();
            if (input.startsWith("playlists") && !input.equals("playlists"))
            {
                genre = input.substring(10);
                input = "playlists";
            }

            switch (input.toLowerCase()){
                case "auth": if(checkAuth){ System.out.println("Already authenticated"); } else {checkAuth = true; authenticate(); } break;
                case "featured": if (checkAuth) {setPageIndex(0); featured();} else {noAuth();} break;
                case "next": if (checkAuth) {
                                if(playCategory != 'O'){
                                    change(1);
                                } else {
                                    System.out.println("Select one option");
                                }
                            } else {
                                noAuth();} break;
                case "prev": if (checkAuth) {
                                if(playCategory != 'O'){
                                    change(-1);
                                } else {
                                    System.out.println("Select one option");
                                }
                            } else {
                                noAuth();} break;
                case "new":  if(checkAuth) {setPageIndex(0); newSongs();} else {noAuth();} break;
                case "categories": if(checkAuth) {setPageIndex(0); categories();} else {noAuth();} break;
                case "playlists": if(checkAuth) {setPageIndex(0); playlists(genre);} else {noAuth();} break;
                case "exit": exit(); break;
                default: if (checkAuth) {System.out.println("Select one option");} else { noAuth(); }
            }
        }
    }

    void exit(){
        System.out.println("---GOODBYE!---");
        System.exit(0);
    }
    void authenticate(){
     new AuthModel(access).auth();
    }
    void noAuth(){
        System.out.println("Please, provide access for application.");
    }

    public void change(int newPageIndex){
        if (newPageIndex > 0 &&  getPageIndex() < getMaxPageIndex()){
            setPageIndex(getPageIndex()+1);
        }
        if (newPageIndex < 0 &&  getPageIndex() >= 0) {
            setPageIndex(getPageIndex() - 1);
        }
        switch (playCategory){
            case 'F': featured(); break;
            case 'S': newSongs(); break;
            case 'C': categories();break;
            case 'P': playlists(genre); break;
            case 'O': break;
        }
    }

    void featured(){
        playCategory = 'F';

        FeaturedModel featuredModel = FeaturedModel.getInstance(this.resource);
        JsonArray ja = featuredModel.getData(getAccessToken());

        setMaxPageIndex(ja);
        FeaturedView.show(ja, getMaxPageIndex(), getPageIndex(), page);
    }

    void newSongs(){
        playCategory = 'S';

        NewSongsModel newSongsModel = NewSongsModel.getInstance(this.resource);
        JsonArray ja = newSongsModel.getData(getAccessToken());

        setMaxPageIndex(ja);
        NewSongsView.show(ja, getMaxPageIndex(), getPageIndex(), page);
    }

    void categories(){
        playCategory = 'C';

        CategoriesModel categoriesModel = CategoriesModel.getInstance(this.resource);
        JsonArray ja = categoriesModel.getData(getAccessToken());

        setMaxPageIndex(ja);
        CategoriesView.show(ja, getMaxPageIndex(), getPageIndex(), page);
    }

    void playlists(String genre){
        playCategory = 'P';

        CategoriesModel categoriesModel = CategoriesModel.getInstance(this.resource);
        String category_id = categoriesModel.getCategory(getAccessToken(),genre);
        if(category_id.equals("not found")){
            System.out.println("Unknown category name.");
        } else {
            PlaylistsModel playlistsModel = PlaylistsModel.getInstance(this.resource, category_id);
            JsonArray ja = playlistsModel.getData(getAccessToken());

            setMaxPageIndex(ja);
            PlaylistsView.show(ja, getMaxPageIndex(), getPageIndex(), page);
        }

    }
}
