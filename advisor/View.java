package advisor;

public class View {
    protected static boolean checkPageIndexes(int maxPages, int pageIndex){
        boolean check = false;
        if(pageIndex < 0){
            System.out.println("No more pages.");
            Controller.setPageIndex(++pageIndex);
            check = true;
        }else if (pageIndex >= maxPages){
            System.out.println("No more pages.");
            Controller.setPageIndex(--pageIndex);
            check = true;
        }
        return check;
    }
}
