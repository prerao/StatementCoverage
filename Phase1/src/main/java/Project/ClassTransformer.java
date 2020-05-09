package cs6367Project;
import java.lang.instrument.ClassFileTransformer;
import java.security.*;
import java.security.ProtectionDomain;
import org.objectweb.asm.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassReader;

public class ClassTransformer implements ClassFileTransformer
{
    private final String pkgName;
    public ClassTransformer(String pkgName)
    {
        this.pkgName = pkgName;
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer)
    {

        if (className.startsWith(pkgName))
        {
            ClassWriter cwriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassReader creader = new ClassReader(classfileBuffer);
            myClassTransformVisitor cvisitor = new myClassTransformVisitor(cwriter, className);
            creader.accept(cvisitor, 0);
            return cwriter.toByteArray();
        }
        return classfileBuffer;
    }
}

