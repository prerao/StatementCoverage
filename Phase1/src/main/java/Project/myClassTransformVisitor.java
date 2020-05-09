package cs6367Project;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassVisitor;
class myClassTransformVisitor extends ClassVisitor implements Opcodes
{
    private String myClass;
    public myClassTransformVisitor(final ClassVisitor cv, final String myClass)
    {
        super(Opcodes.ASM5, cv);
        this.myClass = myClass;
    }
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions)
    {
        MethodVisitor mv= cv.visitMethod(access, name, desc, signature, exceptions);
        if(mv == null)
            return null;
        else
            return (new MethodTransformVisitor(mv, name, myClass));
    }

}