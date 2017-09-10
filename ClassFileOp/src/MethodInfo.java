
/**
 * 
 * @author hulang
 */
public class MethodInfo {
    /** u2 */
    public int accessFlags;
    /** cp_index */
    public int nameIndex;
    /** cp_index */
    public int descriptorIndex;
    /** u2 */
    public int attributesCount;
    /** attributes[attributesCount] */
    public AttributeInfo[] attributes;
}
