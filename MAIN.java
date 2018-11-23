import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class MAIN {


    public static void main(String args[]){
        DBConnect db = new DBConnect();
        ArrayList<String> ListOfWords = new ArrayList<>();
        RemoveStopWords rsw = new RemoveStopWords();
        Scanner inn = new Scanner(System.in);
        LogicForCSV lcsv = new LogicForCSV();
        ArrayList<ForExcel> obj = new ArrayList<>();


        System.out.println("Do you wish to insert new words into the Database right now? 1.No 2.Yes");
        int ch = inn.nextInt();

        if(ch==2){

            System.out.println("Enter news website");
            String website=inn.next();
            System.out.println("Enter Category ID");
            int cid = inn.nextInt();


            RSSFeedParser parser = new RSSFeedParser(website);
            Feed feed = parser.readFeed();
            System.out.println(feed);
            String temp;

            for (FeedMessage message : feed.getMessages()) {
                System.out.println(message);
                temp = message.getDescription();
                ListOfWords = rsw.removeSTopwords(temp);

            }

            System.out.println(ListOfWords);

            for(String abc : ListOfWords) {
                db.insertQ(abc, cid);

            }

            db.deleteTable(); //This will delete the older temptable
            db.createnewtable(); //This will create the new temptable made from the newly added words from termtable
        }
        else{

            String article = "boobs "; //Enter article to be analysed
            System.out.println("String/Article given :" + article+"\n\n");

            ArrayList<String> array = new ArrayList<>();
            array = rsw.removeSTopwords(article);
            db.formulate(array);

            int[] final_Answer = new int[3];
            final_Answer = db.AnswerArr();

            if(final_Answer[0]==final_Answer[1]&&final_Answer[1]==final_Answer[2]&&final_Answer[0]==final_Answer[2]){
                System.out.println("Article is undecisive");
            }else {

                for (int jj = 0; jj < 3; jj++) {
                    System.out.println(final_Answer[jj]);
                }
                int ans = db.getIndexOfLargest(final_Answer);

                System.out.println("\n\n\n\n\n********************************");
                db.AnswerCategory(ans);
                System.out.println("********************************\n\n\n\n\n");

                ArrayList<String> noDub = new ArrayList<>();
                noDub = db.removeDup(array);

                obj = db.check(noDub, ans + 1);

                lcsv.WritePairOfWords(obj);

            }
        }














    }
}
