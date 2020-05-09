package cs6367Project;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.LocalVariableNode;

class ClassTransformVisitor extends ClassVisitor implements Opcodes {
    List<FieldNode> fNodeList;
    ClassNode cNode;
    String[] variables = null;
    String name_of_class;
    public ClassTransformVisitor(final ClassVisitor classVisitorobj, final String name_of_class) {
        super(Opcodes.ASM5, classVisitorobj);
        this.cNode = new ClassNode();
        this.name_of_class = name_of_class;
        try {
            ClassReader cread = new ClassReader(name_of_class);
            cread.accept(cNode, 0);
            this.fNodeList = (List<FieldNode>) cNode.fields;
        } catch(IOException ignored) {
        }
    }
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        List<MethodNode> MList = new ArrayList<>((List<MethodNode>) cNode.methods);
        for(int i = 0; i < MList.size(); i++){
            final MethodNode methodnodeobj = MList.get(i);
            int msize = methodnodeobj.localVariables.size();
            if ( methodnodeobj.name.equals(name)) {
                variables = new String[msize];
                List<LocalVariableNode> llist = new ArrayList<>((List<LocalVariableNode>) methodnodeobj.localVariables);
                for(int j = 0; j < llist.size();j++) {
                    LocalVariableNode lvar = llist.get(j);
                    variables[lvar.index] = lvar.name;
                }
            }
        }
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if(mv== null) {
            return null;
        }
        else {
            return new MethodTransformVisitor(mv, name_of_class,name,desc,access, variables, fNodeList);
        }

    }

}