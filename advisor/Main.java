package advisor;

public class Main {
        static String access = "https://accounts.spotify.com";
        static String resource = "https://api.spotify.com";
        static int page = 5;

        public static void main(String[] args) {
            if(args.length > 0){
                for (int i = 0; i < args.length; i++) {
                    if(args[i].contains("-access")) access = args[i+1];
                    if(args[i].contains("-resource")) resource = args[i+1];
                    if(args[i].contains("-page")) try{
                        page = Integer.parseInt(args[i+1]);
                    }catch (NumberFormatException e){
                        System.out.println("NaN");
                    }
                }
            }
            Controller controller = new Controller(access, resource, page);
            controller.start();
        }
}
