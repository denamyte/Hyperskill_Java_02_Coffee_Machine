// Don't delete this import statement
import java.util.Scanner;

class SimpleCalculator {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        long num1 = scanner.nextLong();
        String operator = scanner.next();
        long num2 = scanner.nextLong();
        switch (operator) {
            case "-":
                subtractTwoNumbers(num1, num2);
                break;
            case "+":
                sumTwoNumbers(num1, num2);
                break;
            case "/":
                divideTwoNumbers(num1, num2);
                break;
            default:
                multiplyTwoNumbers(num1, num2);
                break;
        }
    }

    // Implement your methods here
    public static void subtractTwoNumbers(long a, long b) {
        System.out.println(a - b);
    }

    public static void sumTwoNumbers(long a, long b) {
        System.out.println(a + b);
    }

    public static void divideTwoNumbers(long a, long b) {
        System.out.println(b == 0 ? "Division by 0!" : a / b);
    }

    public static void multiplyTwoNumbers(long a, long b) {
        System.out.println(a * b);
    }
}