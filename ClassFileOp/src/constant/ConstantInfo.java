package constant;

/**
 * 常量池信息通用结构,用以提供抽象
 * @author hulang
 */
public class ConstantInfo {
    /** 项目大小为u1，表示信息类型, 但通过instanceof运算符等也可以得到类型信息 */
    public int tag;
    
    public ConstantInfo() {}
    public ConstantInfo(int tag) {
        this.tag = tag;
    }
}
