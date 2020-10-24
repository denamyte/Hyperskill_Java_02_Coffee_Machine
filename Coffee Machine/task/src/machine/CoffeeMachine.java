package machine;

import java.util.Scanner;

public class CoffeeMachine {

    static Amount[] beverageAmounts = {
            new Amount(-250,    0, -16, -1, 4),  // espresso
            new Amount(-350,  -75, -20, -1, 7),  // latte
            new Amount(-200, -100, -12, -1, 6)   // cappuccino
    };
    static String[] resourceNames = new String[]{"water", "milk", "coffee", "cups"};

    Amount storeAmount = new Amount(400, 540, 120, 9, 550);
    Scanner scanner = new Scanner(System.in);

    public void turnOn() {
        do {
            switch (acceptAction()) {
                case "buy": buy();
                    break;
                case "fill": fill();
                    break;
                case "take": take();
                    break;
                case "remaining": showAmounts();
                    break;
                case "exit": return;
            }
        } while (true);
    }

    private String acceptAction() {
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        return scanner.nextLine();
    }

    private void buy() {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        String order = scanner.nextLine();
        if ("back".equals(order)) {
            System.out.println();
            return;
        }
        Amount amount = beverageAmounts[Integer.parseInt(order) - 1];
        int runOutIndex = storeAmount.whichResourceRunOut(amount);
        if (runOutIndex > -1) {
            System.out.printf("Sorry, not enough %s!\n\n", resourceNames[runOutIndex]);
            return;
        }
        System.out.println("I have enough resources, making you a coffee!\n");
        storeAmount.plus(amount);
    }

    private void fill() {
        Amount fill = new Amount();
        System.out.println("\nWrite how many ml of water do you want to add: ");
        fill.water = scanner.nextInt();
        System.out.println("Write how many ml of milk do you want to add: ");
        fill.milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        fill.beans = scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        fill.cups = scanner.nextInt();
        storeAmount.plus(fill);
        scanner.nextLine();  // For some reason the input buffer has an empty line here. This line of code just eliminates it.
        System.out.println();
    }

    private void take() {
        System.out.printf("%nI gave you %d%n%n", storeAmount.money);
        storeAmount.money = 0;
    }

    private void showAmounts() {
        System.out.printf(
                "\nThe coffee machine has:%n"
                        + "%d of water%n"
                        + "%d of milk%n"
                        + "%d of coffee beans%n"
                        + "%d of disposable cups%n"
                        + "%d of money%n%n",
                storeAmount.water, storeAmount.milk, storeAmount.beans,
                storeAmount.cups, storeAmount.money);
    }

    public static void main(String[] args) {
        new CoffeeMachine().turnOn();
    }

    static class Amount {
        int water, milk, beans, cups, money;

        Amount() { }

        Amount(int water, int milk, int beans, int cups, int money) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cups = cups;
            this.money = money;
        }

        /** Sum up this amount and another one. */
        void plus(Amount other) {
            water += other.water;
            milk += other.milk;
            beans += other.beans;
            cups += other.cups;
            money += other.money;
        }

        int whichResourceRunOut(Amount other) {
            if (water + other.water < 0) {
                return 0;
            }
            if (milk + other.milk < 0) {
                return 1;
            }
            if (beans + other.beans < 0) {
                return 2;
            }
            if (cups + other.cups < 0) {
                return 3;
            }
            return -1;
        }
    }
}
