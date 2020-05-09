package cs6367Project;
import java.io.*;
import java.util.*;
public class CalculateInvariants {
    ArrayList<String> allVarSet;
    public CalculateInvariants(ArrayList<String> allVarSet) {
        this.allVarSet =allVarSet;
    }
    public static HashMap<String,ArrayList<String>> patternStore = new HashMap<String, ArrayList<String>>();
    public void calculate_invariant_patterns() {
        List<String>  pList = new ArrayList<String>(allVarSet);
        Collections.sort(pList);
        for(String n :pList){
            String[] str = n.split(File.separator);
            format(str[0],str[1],str[2],str[3],str[4]);
        }
        String dir ="target"+File.separator+"Phase2";
        String fPath = dir + File.separator + "invariant-log.txt";
        Compute_pattern_logic(dir,fPath, patternStore);

    }
    public void Compute_pattern_logic(String fold, String locate, HashMap<String, ArrayList<String>> m){
        try {
            File file = new File(locate);
            BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            StringBuilder builder = new StringBuilder();
            File folder = new File(fold);
            if (! folder.exists()){
            folder.mkdir();
            }
            if (!file.exists())
            file.createNewFile();
            for(String name :m.keySet()){
                String[] temp = name.split(File.separator);

                builder.append("*********************Patterns**********************").append("\n");
                builder.append("Java Class:").append("\t").append(temp[0]).append("\t");
                builder.append("Java Method:").append("\t").append(temp[1]).append("\t");
                builder.append("Variable:").append("\t").append(temp[2]).append("\t");
                builder.append("Variable Data Type:").append("\t").append(temp[3]).append("\n");
                ArrayList<String> values = m.get(name);
                List<String> valueList = new ArrayList<String>(values);
                builder.append("Range Limits:").append("\t");
                if(valueList.size()!=1) {
                    builder.append("{").append(valueList.get(0)).append(",").append(valueList.get(valueList.size() - 1)).append("}").append(";;;\t");

                }
                else{
                    builder.append(valueList.get(0)).append("\n");
                }

                builder.append("Is it a constant").append("\t");
                if(valueList.size()!=1){
                    builder.append("It is not a constant").append("\n").append("\t");
                }
                else{
                    builder.append("It is a constant").append("\n").append("\t");
                }
                for (String v : valueList) {
                    builder.append(v).append(";;;\t");

                }
                builder.append("Zero Check").append("\t");
                if(valueList.contains("0")||valueList.contains("0.0")){
                    builder.append(temp[2]).append("is 0 valued").append("\n");
                }
                else{
                    builder.append(temp[2]).append("is not zero valued").append(";;;\t");
                }
                builder.append("Modulo check").append("\t");
                boolean flag = false;
                ArrayList<Integer> x = new ArrayList<Integer>();
                if(!temp[3].equals("int")){
                    builder.append("It is not a modulo").append(";;;\t");
                }
                else{
                    for(String str:valueList){
                        int n = Integer.parseInt(str);
                        ArrayList<Integer> cf = new ArrayList<Integer>();
                        if (n <= 1) cf.add(-1);
                        if(n>200){
                            cf.add(-1);
                        }
                        else{
                            for (int i = 2; i < n; i++) {
                                if (n % i == 0) cf.add(i);
                            }
                        }
                        for(int i:cf){
                            if(i!=-1){
                                x.add(i);
                                flag = true;
                            }
                        }
                    }
                    if(flag){
                        builder.append("The modulo values are: ");
                        for(int i:x){
                            builder.append(i);
                        }
                    }
                    else{
                        builder.append("It is not a modulo");
                    }
                }
                builder.append("\n");
            }
            bwriter.write(builder.toString());
            bwriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void format(String className, String methodName, String variableName, String variableType, String variableValue){
        String form_str= className.replaceAll(File.separator,".") + File.separator + methodName + File.separator + variableName + File.separator + variableType;
        ArrayList<String> store = patternStore.get(form_str);
        if(store==null){
            store= new ArrayList<String>();
        }
        store.add(variableValue.substring(0,variableValue.length()-1));
        patternStore.put(form_str,store);
    }
}
