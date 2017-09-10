

import constant.ConstantPool;

/**
 * 
 * @author hulang
 */
public class ClassFile {
    /** u4 */
    public int magic;
    
    /** u2 */
    public int minorVersion;
    
    /** u2 */
    public int majorVersion;
    
    public ConstantPool cp;
    
    /** u2 */
    public int accessFlags;
    
    /** cp_index */
    public int thisClass;
    
    /** cp_index */
    public int superClass;
    
    /** u2 */
    public int interfacesCount;
    
    /** u2 interfaces[interfacesCount] */
    public int[] interfaces;
    
    /** u2 */
    public int fieldsCount;
    
    /** fields[fieldsCount] */
    public FieldInfo[] fields;
    
    /** u2 */
    public int methodsCount;

    /** methods[methodsCount] */
    public MethodInfo[] methods;
    
    /** u2 */
    public int attributesCount;

    /** attributes[attributesCount] */
    public AttributeInfo[] attributes;
}
