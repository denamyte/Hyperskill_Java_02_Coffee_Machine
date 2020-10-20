package machine;

import java.util.Scanner;
import java.util.stream.IntStream;

public class CoffeeMachine {

    static final int CUPS_INDEX = 3;
    static final String[] QUERIES = {
            "Write how many ml of water the coffee machine has: ",
            "Write how many ml of milk the coffee machine has: ",
            "Write how many grams of coffee beans the coffee machine has: ",
            "Write how many cups of coffee you will need: "
    };
    static final String[] MESSAGES = {
            "No, I can make only %d cup(s) of coffee",
            "Yes, I can make that amount of coffee",
            "Yes, I can make that amount of coffee (and even %d more than that)"
    };
    static final int[] PER_CUP = {200, 50, 15};
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int[] data = obtainData();
        int maxCups = defineMaxCups(data);
        String result = defineResult(maxCups, data[CUPS_INDEX]);
        System.out.println(result);
    }

    static int[] obtainData() {
        return IntStream.range(0, 4).map(CoffeeMachine::requestAmount).toArray();
    }

    static int requestAmount(int index) {
        System.out.println(QUERIES[index]);
        return scanner.nextInt();
    }

    static int defineMaxCups(int[] data) {
        return IntStream.range(0, 3)
                .map(i -> data[i] / PER_CUP[i])
                .min()
                .orElseThrow();
    }

    static String defineResult(int maxCups, int orderedCups) {
        switch (Integer.compare(maxCups, orderedCups)) {
            case -1: return String.format(MESSAGES[0], maxCups);                //  < 0:  not enough
            case  0: return MESSAGES[1];                                        // == 0: exact amount
            default: return String.format(MESSAGES[2], maxCups - orderedCups);  //  > 0:  surplus
        }
    }
}
