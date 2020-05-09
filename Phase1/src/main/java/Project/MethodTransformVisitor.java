package cs6367Project;
import java.util.*;
import java.util.HashSet;
import org.objectweb.asm.*;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Label;
class MethodTransformVisitor extends MethodVisitor implements Opcodes
{
    private HashSet<Integer> coveredL;
    private String clNames;
    int lineNumber;
    String methodName;
    public MethodTransformVisitor(final MethodVisitor mv, String mname, String clNames)
    {
        super(Opcodes.ASM5, mv);
        this.clNames = clNames;
        this.methodName = mname;


        coveredL = new HashSet<Integer>();
    }

    private void Capture(int lineNumber)
    {
        if (lineNumber == 0)
            return;
        String nameClass = clNames;
        mv.visitLdcInsn(nameClass);
        mv.visitLdcInsn(lineNumber);


        mv.visitMethodInsn(INVOKESTATIC, "cs6367Project/MainCoverage", "visitorLine", "(Ljava/lang/String;I)V", false);
    }
    @Override
    public void visitLineNumber(int lineNumber, Label lab)
    {
        super.visitLineNumber(lineNumber, lab);
        this.lineNumber = lineNumber;
        Capture(lineNumber);
    }
    @Override
    public void visitLabel(Label lab)
    {
        super.visitLabel(lab);
        Capture(lineNumber);

    }
}