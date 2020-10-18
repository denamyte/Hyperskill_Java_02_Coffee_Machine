package machine;

import java.util.Scanner;

public class CoffeeMachine {
    static final String
            prompt = "Write how many cups of coffee you will need: ",
            caption = "For %d cups of coffee you will need:%n",
            water = "%d ml of water%n",
            milk = "%d ml of milk%n",
            beans = "%d g of coffee beans%n";

    public static void main(String[] args) {
        System.out.println(prompt);
        int cups = Integer.parseInt(new Scanner(System.in).nextLine());
        System.out.printf(caption, cups);
        System.out.printf(water, calcWater(cups));
        System.out.printf(milk, calcMilk(cups));
        System.out.printf(beans, calcBeans(cups));
    }

    static int calcWater(int cups) {
        return cups * 200;
    }

    static int calcMilk(int cups) {
        return cups * 50;
    }

    static int calcBeans(int cups) {
        return cups * 15;
    }
}
