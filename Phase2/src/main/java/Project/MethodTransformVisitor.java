package cs6367Project;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;

import java.util.List;

class MethodTransformVisitor extends MethodVisitor implements Opcodes {
     int acc;
     String[] vName;
     String func_name;
     String desc;
     String name_of_class;
     FieldNode[] fNode;

    public MethodTransformVisitor(final MethodVisitor mv, String name_of_class, String name, String desc, int acc, String[] vName, List<FieldNode> fNode) {
        super(Opcodes.ASM5, mv);
        this.func_name = name;
        this.name_of_class = name_of_class;
        this.vName =vName;
        this.desc =desc;
        this.acc = acc;
        if (fNode != null) {
            this.fNode = new FieldNode[fNode.size()];
            this.fNode = fNode.toArray(this.fNode);
        }
    }

    public void VisitCapture(String desc, String name, int in, String funcName, int f, boolean check){
        String type;
        int opcode;
        if ("Z".equals(desc)) {
            opcode = Opcodes.ILOAD;
            type = "boolean";
        }
        else if ("C".equals(desc)) {
            opcode = Opcodes.ILOAD;
            type = "char";
        }
        else if ("I".equals(desc)) {
            opcode = Opcodes.ILOAD;
            type = "int";
        }
        else if ("F".equals(desc)) {
            opcode = Opcodes.FLOAD;
            type = "float";
        }
        else if ("J".equals(desc)) {
            opcode = Opcodes.LLOAD;
            type = "long";
        }
        else if ("D".equals(desc)) {
            opcode = Opcodes.DLOAD;
            type = "double";
        }
        else {
            return;
        }
        mv.visitLdcInsn(name_of_class);
        mv.visitLdcInsn(func_name);
        mv.visitLdcInsn(name);
        mv.visitLdcInsn(type);

        if(f==1) {
            mv.visitVarInsn(opcode, in);
        }
        else if(f==0){
            if (check) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, name_of_class, name, desc);
            }
            else {
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(Opcodes.GETFIELD, name_of_class, name, desc);
            }
        }

        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", "(" + desc + ")" + "Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKESTATIC, "cs6367Project/ComputeFieldAndLocal", funcName, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
    }
    @Override
    public void visitCode() {
        if (fNode != null) {
            for(int i = 0; i < fNode.length; i++)
            {
                FieldNode field = fNode[i];
                if (func_name.equals("<init>") || (acc & Opcodes.ACC_STATIC) != 0) return;
                boolean fcheck = (field.access & Opcodes.ACC_STATIC) != 0;
                VisitCapture(field.desc,field.name,0,"calculate_fv", 0, fcheck);

            }

        }

        Type[] localVariableTypes = Type.getArgumentTypes(desc);

        int offset =  (this.acc & Opcodes.ACC_STATIC) != 0? 0 : 1;

        String variableName;
        if(vName !=null){
            for (int i = 0; i < localVariableTypes.length; i++) {
                if(vName != null){
                    variableName = vName[i + offset];
                }
                else variableName = "variableName";
                VisitCapture(localVariableTypes[i].getDescriptor(),variableName,i+offset,"calculate_lv", 1,true);
            }
        }

        super.visitCode();
    }


}

