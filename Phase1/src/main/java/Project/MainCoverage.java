package cs6367Project;
import java.util.*;
import java.util.HashMap;
public class MainCoverage
{
    public static String ttName;
    public static HashMap<String, HashSet<Integer>> tsCase;
    public static HashMap<String, HashMap<String, HashSet<Integer>>> tsSuite;
    public static void visitorLine(String clNamer, int lineNumber)
    {
        if(nullChk(tsCase,clNamer))
            return;
        HashSet<Integer> setLine = tsCase.get(clNamer);
        int aline;
        if(setLine == null)
        {
            setLine = new HashSet<Integer>();
        }
        setLine.add(lineNumber);
        tsCase.put(clNamer, setLine);
    }
    private static boolean nullChk(HashMap<String, HashSet<Integer>> map, String str )
    {
        return map == null || str == null;
    }
}
