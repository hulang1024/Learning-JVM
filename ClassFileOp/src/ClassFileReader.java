import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import constant.*;

/**
 * 
 * @author hulang
 */
public class ClassFileReader {
    private DataInput in;
    
    public ClassFile read(String classFileName) throws Exception {
        in = new DataInputStream(new FileInputStream(classFileName));
        
        ClassFile classFile = new ClassFile(); 
        
        classFile.magic = in.readInt();
        
        classFile.minorVersion = in.readUnsignedShort();
        classFile.majorVersion = in.readUnsignedShort();
        
        readConstantPool(classFile);
        
        classFile.accessFlags = in.readUnsignedShort();
        
        classFile.thisClass = in.readUnsignedShort();
        
        classFile.superClass = in.readUnsignedShort();
        
        readInterfaces(classFile);
        
        readFields(classFile);
        
        readMethods(classFile);
        
        readAttributes(classFile);
        
        return classFile;
    }

    private void readAttributes(ClassFile classFile) throws IOException {
        classFile.attributesCount = in.readUnsignedShort();
        classFile.attributes = readAttributes(classFile.attributesCount);
    }

    private void readMethods(ClassFile classFile) throws IOException {
        classFile.methodsCount = in.readUnsignedShort();
        if (classFile.methodsCount > 0) {
            MethodInfo[] methods = classFile.methods = new MethodInfo[classFile.methodsCount];
            MethodInfo methodInfo;
            
            for (int i = 0, len = methods.length; i < len; i++) {
                methodInfo = new MethodInfo();
                methodInfo.accessFlags = in.readUnsignedShort();
                methodInfo.nameIndex = in.readUnsignedShort();
                methodInfo.descriptorIndex = in.readUnsignedShort();
                methodInfo.attributesCount = in.readUnsignedShort();
                
                methodInfo.attributes = readAttributes(methodInfo.attributesCount);
                
                methods[i] = methodInfo;
            }
        }
    }

    private void readFields(ClassFile classFile) throws IOException {
        classFile.fieldsCount = in.readUnsignedShort();
        if (classFile.fieldsCount > 0) {
            FieldInfo[] fields = classFile.fields = new FieldInfo[classFile.fieldsCount];
            FieldInfo fieldInfo;
            
            for (int i = 0, len = fields.length; i < len; i++) {
                fieldInfo = new FieldInfo();
                fieldInfo.accessFlags = in.readUnsignedShort();
                fieldInfo.nameIndex = in.readUnsignedShort();
                fieldInfo.descriptorIndex = in.readUnsignedShort();
                
                fieldInfo.attributesCount = in.readUnsignedShort();
                fieldInfo.attributes = readAttributes(fieldInfo.attributesCount);
                
                fields[i] = fieldInfo;
            }
        }
    }

    private AttributeInfo[] readAttributes(int attributesCount) throws IOException {
        if (attributesCount == 0)
            return null;

        AttributeInfo[] attributes = new AttributeInfo[attributesCount];
        AttributeInfo attrInfo;
        for (int i = 0; i < attributesCount; i++) {
            attrInfo = new AttributeInfo();
            attrInfo.attributeNameIndex = in.readUnsignedShort();
            attrInfo.attributeLength = in.readInt();
            
            // read info
            if (attrInfo.attributeLength > 0) {
                attrInfo.info = new int[attrInfo.attributeLength];
                for (int j = 0, alen = attrInfo.attributeLength; j < alen; j++) {
                    attrInfo.info[j] = in.readUnsignedByte();
                }
            }
            attributes[i] = attrInfo;
        }
        return attributes;
    }

    private void readInterfaces(ClassFile classFile) throws IOException {
        classFile.interfacesCount = in.readUnsignedShort();
        
        if (classFile.interfacesCount > 0) {
            int[] interfaces = classFile.interfaces = new int[classFile.interfacesCount];
            for (int i = 0, len = interfaces.length; i < len; i++) {
                interfaces[i] = in.readUnsignedShort();
            }
        }
    }

    private void readConstantPool(ClassFile classFile) throws IOException {        
        ConstantPool cp = new ConstantPool();
        cp.constantPoolCount = in.readUnsignedShort();
        cp.array = new ConstantInfo[cp.constantPoolCount - 1];
        
        ConstantInfo constInfo = null;
        int tag;
        for (int itemIndex = 0, cplen = cp.constantPoolCount - 1; itemIndex < cplen; itemIndex++) {
            tag = in.readUnsignedByte();
            switch (tag) {
            case ConstantPoolTag.CLASS:
                ClassConstantInfo cci = new ClassConstantInfo();
                cci.nameIndex = in.readUnsignedShort();
                constInfo = cci;
                break;
            case ConstantPoolTag.FIELD_REF:
                FieldRefConstantInfo frci = new FieldRefConstantInfo();
                frci.classIndex = in.readUnsignedShort();
                frci.nameAndTypeIndex = in.readUnsignedShort();
                constInfo = frci;
                break;
            case ConstantPoolTag.METHOD_REF:
                MethodRefConstantInfo mrci = new MethodRefConstantInfo();
                mrci.classIndex = in.readUnsignedShort();
                mrci.nameAndTypeIndex = in.readUnsignedShort();
                constInfo = mrci;
                break;
            case ConstantPoolTag.INTERFACE_METHOD_REF:
                InterfaceMethodRefConstantInfo imrci = new InterfaceMethodRefConstantInfo();
                imrci.classIndex = in.readUnsignedShort();
                imrci.nameAndTypeIndex = in.readUnsignedShort();
                constInfo = imrci;
                break;
            case ConstantPoolTag.STRING:
                StringConstantInfo sci = new StringConstantInfo();
                sci.stringIndex = in.readUnsignedShort();
                constInfo = sci;
                break;
            case ConstantPoolTag.INTEGER:
                IntegerConstantInfo ici = new IntegerConstantInfo();
                ici.bytes = in.readInt();
                constInfo = ici;
                break;
            case ConstantPoolTag.FLOAT:
                FloatConstantInfo fci = new FloatConstantInfo();
                fci.bytes = in.readInt();
                constInfo = fci;
                break;
            case ConstantPoolTag.LONG:
                LongConstantInfo lci = new LongConstantInfo();
                lci.highBytes = in.readInt();
                lci.lowBytes = in.readInt();
                constInfo = lci;
                break;
            case ConstantPoolTag.DOUBLE:
                DoubleConstantInfo dci = new DoubleConstantInfo();
                dci.highBytes = in.readInt();
                dci.lowBytes = in.readInt();
                constInfo = dci;
                break;
            case ConstantPoolTag.NAME_AND_TYPE:
                NameAndTypeConstantInfo ntci = new NameAndTypeConstantInfo();
                ntci.nameIndex = in.readUnsignedShort();
                ntci.descriptorIndex = in.readUnsignedShort();
                constInfo = ntci;
                break;
            case ConstantPoolTag.UTF8:
                UTF8ConstantInfo utf8ci = new UTF8ConstantInfo();
                utf8ci.length = in.readUnsignedShort();
                int[] bytes = utf8ci.bytes = new int[utf8ci.length];
                for (int i = 0, len = utf8ci.length; i < len; i++)
                    bytes[i] = in.readUnsignedByte();
                constInfo = utf8ci;
                break;
            case ConstantPoolTag.METHOD_HANDLE:
                MethodHandleConstantInfo mhci = new MethodHandleConstantInfo();
                mhci.referenceKind = in.readUnsignedByte();
                mhci.referenceIndex = in.readUnsignedShort();
                constInfo = mhci;
                break;
            case ConstantPoolTag.METHOD_TYPE:
                MethodTypeConstantInfo mtci = new MethodTypeConstantInfo();
                mtci.descriptorIndex = in.readUnsignedShort();
                constInfo = mtci;
                break;
            case ConstantPoolTag.INVOKE_DYNAMIC:
                InvokeDynamicConstantInfo idynci = new InvokeDynamicConstantInfo();
                idynci.bootstrapMethodAttrIndex = in.readUnsignedShort();
                idynci.nameAndTypeIndex = in.readUnsignedShort();
                constInfo = idynci;
                break;
            default:
                throw new RuntimeException("Unknown tag: " + tag);
            }
            constInfo.tag = tag;
            cp.array[itemIndex] = constInfo;
        }
        
        classFile.cp = cp;
    }
}
