package constant;

/**
 * 
 * @author hulang
 */
public class ConstantPool {
    /** u2 等于成员数+1 */
    public int constantPoolCount;
    
    /** cp_index = u2, 在常量池中,索引是1到constantPoolCount-1,应映射为0~constantPoolCount-2 */
    public ConstantInfo[] array;
}
