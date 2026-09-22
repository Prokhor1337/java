import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) {
        String jvmVersion = System.getProperty("java.version");
        String jvmVendor = System.getProperty("java.vendor");
        System.out.println("JVM: " + jvmVersion + " (" + jvmVendor + ")");

        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        System.out.println("OS: " + osName + " (" + osArch + ")");

        String original = "avaJ";
        String reversed = StringUtils.reverse(original);
        System.out.println("Reversed (Commons Lang): " + reversed);

        printUkrainianGreeting();
    }

    public static void printUkrainianGreeting() {
        System.out.println("Привіт зі збірки!");
    }
}
