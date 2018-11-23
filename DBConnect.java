import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class DBConnect {
    private Connection con;
    private Statement st;
    private ResultSet rs;

    public int[] arr = new int[3];

    public DBConnect(){
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/textminingrss", "root", "");
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("Error " + ex);
        }
    }

    public void insertQ(String term, int C_id){
        try
        {

            PreparedStatement preparedStatement =
                    con.prepareStatement("INSERT into term_table (C_id, Term) VALUES (?,?)",
                            Statement.RETURN_GENERATED_KEYS);


            preparedStatement.setInt(1,C_id);
            preparedStatement.setString(2,term);

            preparedStatement.executeUpdate();

        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            System.out.println(e);
        }
    }

    public void deleteTable(){

        try {
            st = con.createStatement();
            st.executeUpdate("DROP TABLE temptable");
        }catch(Exception io){
            System.out.println(io);
        }
    }

    public void createnewtable(){

        try{
            st = con.createStatement();
            st.execute("CREATE TABLE temptable AS select Term,C_id,count(*) as freq from term_table GROUP by C_id,Term");

        }catch (Exception io){
            System.out.println(io);
        }
    }

    public void formulate(ArrayList<String> article){

        for(String var : article){
            getTermCid(var);
        }


    }

    public void getTermCid(String term){
        try{

            ArrayList<Integer> freqq= new ArrayList<>();

            ArrayList<Integer> cid= new ArrayList<>();
            String query = "SELECT  Term,C_id,freq FROM `temptable` WHERE term = '"+term+"'";
            rs = st.executeQuery(query);
            while (rs.next()){

                System.out.println("Term :"+rs.getString("Term")+" Freq :"+rs.getInt("freq") +" C_ID:"+rs.getInt("C_id"));
                cal(rs.getInt("freq"),rs.getInt("C_id"));
            }




        }catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    public void cal(int fre, int cid){

        if(cid == 1)
        {
            arr[0] = arr[0] + fre;
        }else if(cid == 2){
            arr[1] = arr[1] + fre;
        }else{
            arr[2] = arr[2] + fre;
        }
    }

    public int[] AnswerArr(){
        return arr;
    }

    public int getIndexOfLargest( int[] array )
    {
        if ( array == null || array.length == 0 ) return -1; // null or empty

        int largest = 0;
        for ( int i = 1; i < array.length; i++ )
        {
            if ( array[i] > array[largest] ) largest = i;
        }
        return largest; // position of the first largest found
    }

    public void AnswerCategory(int ans){
        if(ans == 0){
            System.out.println("The string is of category SCIENCE");
        }
        else if(ans == 1){
            System.out.println("The string is of category POLITICS");
        }else{
            System.out.println("The string is of category SPORT");
        }
    }

    public ArrayList<ForExcel> check(ArrayList<String> abc,int cid){
        ArrayList<ForExcel> exp = new ArrayList<>();
        for(String var : abc){
            exp.add(InsertingCSVvalues(cid,var ));
        }

        return exp;
    }

    public ForExcel InsertingCSVvalues(int cid,String term){
        ForExcel exl = null;
        try {
            exl = new ForExcel();
            String query = "select * from temptable where term = '" + term+"' and C_id = '"+cid+"'";
            rs = st.executeQuery(query);
            while(rs.next()){
                //System.out.println(rs.getString("Term")+"    "+rs.getInt("freq")+"  "+rs.getInt("C_id"));
                exl.setTerm1(rs.getString("Term"));
                exl.setTerm2(rs.getInt("freq"));
            }
        }catch(Exception e){
            System.out.println(e);
        }

        return exl;
    }

    public ArrayList<String> removeDup(ArrayList<String> ex){

        ArrayList<String> temp = new ArrayList<>();
        Set<String> setString = new LinkedHashSet<>();
        for(int i=0;i<ex.size();i++){
            setString.add(ex.get(i));
        }

        for(String exx : setString){
            temp.add(exx);
        }

        return temp;
    }


}
