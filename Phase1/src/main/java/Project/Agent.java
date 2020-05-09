package cs6367Project;
import java.io.*;
import java.util.*;
import java.lang.instrument.Instrumentation;
import java.util.HashSet;
import java.util.Iterator;
import java.io.File;
import java.io.FilenameFilter;
public class Agent {
    public static void premain(String agentArgs, Instrumentation instrumentObject) {
        String mainpath = obtainPath()+File.separator;
        instrumentObject.addTransformer(new ClassTransformer(mainpath));
    }
    private static void obtainAllPaths(File[] filenames, HashSet<String> pathnames)
    {
        for(int i = 0; i < filenames.length;i++) {
            File f = filenames[i];
            if (f.isDirectory()) {
                obtainAllPaths(f.listFiles(), pathnames);
            }
            else {
               String r;
                if(f.getName().endsWith(".java")){
                    String p = f.getParent();
                    r = f.getParent();
                    r = p.substring(14);
                    pathnames.add(r);
                }
            }
        }
    }
    private static int PerfromSimilarity(String t)
    {
        if ( t == null || t.length() == 0 || File.separator == null || File.separator.length() == 0)
        {
            return 0;
        }
        int ind = 0;
        int cnt = 0;
        while (true)
        {
            ind = t.indexOf(File.separator, ind);
            if (ind != -1)
            {

                cnt++;
                ind = ind + File.separator.length();
            } else {
                break;
            }
        }
        return cnt;
    }
    private static String obtainPath() {
        String outputPath = null;
        int answer = 0;
        String mainDirPath = "src" + File.separator + "main" + File.separator + "java";
        File rDir = new File(mainDirPath);
        File[] flist = rDir.listFiles(new FilenameFilter() {
            @Override //overriding this function
            public boolean accept(File fobj, String str) {
                return !str.equals(".DS_Store");
            }
        });
        HashSet<String> pnames = new HashSet<String>();
        assert flist != null;
        obtainAllPaths(flist, pnames);
        Iterator<String> it = pnames.iterator();
        while(it.hasNext())
        {
            String p = it.next();
            int a;
            a = PerfromSimilarity(p);
            if (answer == 0 || a<answer){
                answer = a;
                outputPath = p;
            }
        }
        return outputPath;
    }
}




