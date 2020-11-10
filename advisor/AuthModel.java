package advisor;

import java.io.IOException;

public class AuthModel extends Model {
    private String access;

    public AuthModel(String access) {
        this.access = access;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public void auth(){
        System.out.println("use this link to request the access code:");
        String redirect_uri =  getAccess() +
                    "/authorize?client_id=47b5a38115e341179407bf5b0fb8899c" +
                    "&redirect_uri=http://localhost:8080" +
                    "&response_type=code";
        System.out.println(redirect_uri +
                    "\nwaiting for code...");
        startServer();
    }
    private void startServer(){
        try{
            new Server().initServer(getAccess());
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
}
