/**
 * 
 * @author hulang
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ClassFileReader reader = new ClassFileReader();
        ClassFile classFile = reader.read(Main.class.getResource(args[0]).getFile());
        ClassFilePrinter printer = new ClassFilePrinter();
        printer.print(classFile);
    }
}
