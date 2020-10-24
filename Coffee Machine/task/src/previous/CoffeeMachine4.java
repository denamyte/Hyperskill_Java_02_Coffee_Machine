package previous;

import java.util.Scanner;

public class CoffeeMachine4 {

    static Amount[] beverageAmounts = {
            new Amount(-250,    0, -16, -1, 4),  // espresso
            new Amount(-350,  -75, -20, -1, 7),  // latte
            new Amount(-200, -100, -12, -1, 6)   // cappuccino
    };

    Amount storeAmount = new Amount(400, 540, 120, 9, 550);
    Scanner scanner = new Scanner(System.in);

    public void turnOn() {
        showAmounts();
        switch (acceptAction()) {
            case "buy":  buy();
                break;
            case "fill": fill();
                break;
            case "take": take();
                break;
        }
        System.out.println();
        showAmounts();
    }

    private void showAmounts() {
        System.out.printf(
                "The coffee machine has:%n"
                        + "%d of water%n"
                        + "%d of milk%n"
                        + "%d of coffee beans%n"
                        + "%d of disposable cups%n"
                        + "%d of money%n",
                storeAmount.water, storeAmount.milk, storeAmount.beans,
                storeAmount.cups, storeAmount.money);
    }

    private String acceptAction() {
        System.out.println("\nWrite action (buy, fill, take): ");
        return scanner.nextLine();
    }

    private void buy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
        Amount amount = beverageAmounts[scanner.nextInt() - 1];
        storeAmount.plus(amount);
    }

    private void fill() {
        Amount fill = new Amount();
        System.out.println("Write how many ml of water do you want to add: ");
        fill.water = scanner.nextInt();
        System.out.println("Write how many ml of milk do you want to add: ");
        fill.milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        fill.beans = scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        fill.cups = scanner.nextInt();
        storeAmount.plus(fill);
    }

    private void take() {
        System.out.printf("I gave you %d%n", storeAmount.money);
        storeAmount.money = 0;
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

    }

    public static void main(String[] args) {
        new CoffeeMachine4().turnOn();
    }
}
