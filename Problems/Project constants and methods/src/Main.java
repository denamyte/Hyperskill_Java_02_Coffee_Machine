import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Stream.of(ConstantsAndUtilities.A_CONSTANT_TTT,
                  ConstantsAndUtilities.B_CONSTANT_QQQ,
                  ConstantsAndUtilities.getMagicString(),
                  ConstantsAndUtilities.convertStringToAnotherString("aa"))
                .forEach(System.out::println);
    }

}

// Don't change this class
class ConstantsAndUtilities {

    public static final String A_CONSTANT_TTT = "1234";

    public static final String B_CONSTANT_QQQ = "7890";

    public static String getMagicString() {
        return A_CONSTANT_TTT + B_CONSTANT_QQQ;
    }

    public static String convertStringToAnotherString(String s) {
        return A_CONSTANT_TTT + s;
    }
}