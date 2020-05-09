package cs6367Project;
import java.util.*;
import java.io.*;
public class ComputeFieldAndLocal {
    public static ArrayList<String> lList;
    public static ArrayList<String> fList;
    public static void calculate_fv(String name_of_the_class, String name_of_function, String var_name, String type , String val){

            if(fList ==null){
                fList = new ArrayList<String>();
                String Addstr= name_of_the_class.replaceAll(File.separator,".") + File.separator + name_of_function + File.separator + var_name + File.separator + type + File.separator + val+"\n";
                fList.add(Addstr);
            }
            else{ String Addstr= name_of_the_class.replaceAll(File.separator,".") + File.separator + name_of_function + File.separator + var_name + File.separator + type + File.separator + val+"\n";
            fList.add(Addstr);
        }
    }
    public static void calculate_lv(String name_of_the_class, String name_of_function, String var_name, String type , String val)
        {
            if(lList ==null){
                lList = new ArrayList<String>();
                String Addstr= name_of_the_class.replaceAll(File.separator,".") + File.separator + name_of_function + File.separator + var_name + File.separator + type + File.separator + val+"\n";
                lList.add(Addstr);
            }
            else { String Addstr= name_of_the_class.replaceAll(File.separator,".") + File.separator + name_of_function + File.separator + var_name + File.separator + type + File.separator + val+"\n";
            lList.add(Addstr);
        }
    }
}

