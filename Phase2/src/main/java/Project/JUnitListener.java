package cs6367Project;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.engine.TestExecutionResult;

public class JUnitListener extends RunListener implements TestExecutionListener{

    @Override
    public void executionFinished(TestIdentifier tIdentifierobj, TestExecutionResult tExecutionobj) {
    }
    @Override
    public void testPlanExecutionFinished(TestPlan Planobj) {
    }
    @Override
    public void testRunStarted(Description description) throws Exception {
        super.testRunStarted(description);
        ComputeFieldAndLocal.fList=new ArrayList<String>();
        ComputeFieldAndLocal.lList=new ArrayList<String>();
    }
    @Override
    public void testRunFinished(Result result) throws Exception {
        super.testRunFinished(result);
        String folder_name ="target"+File.separator+"Phase2";
        String fvPath = folder_name + File.separator + "fv.txt";
        String lvPath = folder_name + File.separator + "lv.txt";
        File folder = new File(folder_name);
        if (!folder.exists()){
                folder.mkdir();
        }
        File file = new File(lvPath);
        File file2 = new File(fvPath);
        if (!file.exists())
            file.createNewFile();
        if (!file2.exists())
            file2.createNewFile();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2)));
        Iterator<String> it = ComputeFieldAndLocal.lList.iterator();
        while(it.hasNext()){
            String lvariable = it.next();
            bw.write(lvariable);
        }
        bw.close();
        Iterator<String> it2 = ComputeFieldAndLocal.fList.iterator();
        while(it2.hasNext()){
            String fvariable = it2.next();
            bw2.write(fvariable);
        }
        bw2.close();
        ArrayList<String> list=new ArrayList<String>();
        list.addAll(ComputeFieldAndLocal.lList);
        list.addAll(ComputeFieldAndLocal.fList);
        ArrayList<String> varsList = new ArrayList<>(list);
        CalculateInvariants pattern = new CalculateInvariants(varsList);
        pattern.calculate_invariant_patterns();
    }
}


