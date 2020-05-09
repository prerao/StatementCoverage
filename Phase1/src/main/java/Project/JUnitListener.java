package cs6367Project;

import java.io.*;
import java.util.*;
import org.junit.runner.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.File;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import java.util.HashMap;
import java.io.IOException;
import org.junit.runner.notification.Failure;
import org.junit.*;
import java.io.OutputStreamWriter;
import org.junit.runner.Description;
public class JUnitListener extends RunListener
{
    @Override
    public void testFinished(Description desc) throws Exception
    {
        super.testFinished(desc);
    }
    @Override
    public void testRunStarted(Description desc) throws Exception
    {
        super.testRunStarted(desc);

        MainCoverage.tsSuite = new HashMap<String, HashMap<String, HashSet<Integer>>>();
    }
    @Override
    public void testFailure(Failure failure) throws Exception
    {
        System.out.println("Execution of test case failed : "+ failure.getMessage());
    }
    @Override
    public void testStarted(Description desc) throws Exception
    {
        super.testStarted(desc);
        MainCoverage.ttName = "[TEST] " + desc.getClassName() + ":" + desc.getMethodName();
        MainCoverage.tsCase = new HashMap<String, HashSet<Integer>>();
        MainCoverage.tsSuite.put(MainCoverage.ttName,MainCoverage.tsCase);
    }
    @Override
    public void testIgnored(Description description) throws Exception
    {
        System.out.println("Execution of test case ignored : "+ description.getMethodName());
    }

    @Override
    public void testRunFinished(Result answer) throws Exception {
        super.testRunFinished(answer);

        String folder ="target"+File.separator+"Phase1Log";

        String filePath = folder + File.separator + "stmt-cov.txt";
        try {
            File dir = new File(folder);
            //if directory doesnt exists, make new one
            if (! dir.exists()){
                dir.mkdir();
            }

            File newFile = new File(filePath);
            //if file doesnt exists, make new file
            if (!newFile.exists())
                newFile.createNewFile();

            StringBuilder strBuild = new StringBuilder();
            FileWriter fileWriter = new FileWriter(newFile);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);

            Iterator<String> iterator = MainCoverage.tsSuite.keySet().iterator();
            while(iterator.hasNext()) {
                String ttName = iterator.next();
                strBuild.append(ttName + "\n");
                HashMap<String, HashSet<Integer>> tsCase = MainCoverage.tsSuite.get(ttName);
                Iterator<String> testCaseSetIterator = tsCase.keySet().iterator();
                while(testCaseSetIterator.hasNext()) {
                    String cn = testCaseSetIterator.next();
                    for(int temp : tsCase.get(cn)) {
                        strBuild.append(cn + ":" + temp + "\n");
                    }
                }

            }
            bufWriter.write(strBuild.toString());
            bufWriter.close();


        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
