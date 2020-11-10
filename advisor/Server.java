    package advisor;

    import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

    public class Server {

        public  void initServer(String access) throws IOException {
            Controller.server = HttpServer.create();
            Controller.server.bind(new InetSocketAddress(8080),0);
            Controller.server.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange httpExchange) throws IOException {
                    String query = httpExchange.getRequestURI().getQuery();

                    String response = (query == null || query.contains("error")) ? "Authorization code not found. Try again."
                                : "Got the code. Return back to your program.";
                    httpExchange.sendResponseHeaders(200, response.length());
                    httpExchange.getResponseBody().write(response.getBytes());
                    httpExchange.getResponseBody().close();

                    if(response.equals("Got the code. Return back to your program.")){
                        String code = query.substring(5);
                        System.out.println("code received\nmaking http request for access_token...");
                        new Client(code, access);
                    }
                }
            });
            Controller.server.start();
        }
    }
