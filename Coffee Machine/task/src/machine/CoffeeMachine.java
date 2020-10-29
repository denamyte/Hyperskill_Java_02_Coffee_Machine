package machine;

import java.util.Scanner;

public class CoffeeMachine {

    static Amount[] beverageAmounts = {
            new Amount(-250,    0, -16, -1, 4),  // espresso
            new Amount(-350,  -75, -20, -1, 7),  // latte
            new Amount(-200, -100, -12, -1, 6)   // cappuccino
    };
    static String[] resourceNames = new String[]{"water", "milk", "coffee", "cups"};

    private Amount storeAmount = new Amount(400, 540, 120, 9, 550);
    private Amount fillAmount;
    private State state;

    public CoffeeMachine() {
        topLevelCommandOn();
    }

    public boolean takeCommand(String command) {
        switch (state) {
            case TOP_LEVEL_COMMAND:
                return acceptTopLevelCommand(TopLevelCommand.valueOf(command));
            case BUY_COMMAND:
                acceptBuyCommand(command);
                topLevelCommandOn();
                break;
            case FILL_WATER:
                acceptFillWaterCommand(command);
                fillMIlkCommandOn();
                break;
            case FILL_MILK:
                acceptFillMilkCommand(command);
                fillCoffeeCommandOn();
                break;
            case FILL_COFFEE:
                acceptFillCoffeeCommand(command);
                fillCupsCommandOn();
                break;
            case FILL_CUPS:
                acceptFillCupsCommand(command);
                topLevelCommandOn();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + state);
        }
        return true;
    }

    public boolean acceptTopLevelCommand(TopLevelCommand command) {
        switch (command) {
            case buy:
                buyCommandOn();
                break;
            case fill:
                fillWaterCommandOn();
                break;
            case take:
                take();
                topLevelCommandOn();
                break;
            case remaining:
                showAmounts();
                topLevelCommandOn();
                break;
            case exit: default:
                return false;
        }
        return true;
    }

    public void acceptBuyCommand(String command) {
        if ("back".equals(command)) {
            System.out.println();
            return;
        }
        Amount amount = beverageAmounts[Integer.parseInt(command) - 1];
        int runOutIndex = storeAmount.whichResourceRunOut(amount);
        if (runOutIndex > -1) {
            System.out.printf("Sorry, not enough %s!\n\n", resourceNames[runOutIndex]);
            return;
        }
        System.out.println("I have enough resources, making you a coffee!\n");
        storeAmount.plus(amount);
    }

    private void acceptFillWaterCommand(String command) {
        fillAmount.water = Integer.parseInt(command);
    }

    private void acceptFillMilkCommand(String command) {
        fillAmount.milk = Integer.parseInt(command);
    }

    private void acceptFillCoffeeCommand(String command) {
        fillAmount.beans = Integer.parseInt(command);
    }

    private void acceptFillCupsCommand(String command) {
        fillAmount.cups = Integer.parseInt(command);
        storeAmount.plus(fillAmount);
        System.out.println();
    }

    private void topLevelCommandOn() {
        turnOnState(State.TOP_LEVEL_COMMAND, "Write action (buy, fill, take, remaining, exit): ");
    }

    private void buyCommandOn() {
        turnOnState(State.BUY_COMMAND, "\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
    }

    private void fillWaterCommandOn() {
        fillAmount = new Amount();
        turnOnState(State.FILL_WATER, "\nWrite how many ml of water do you want to add: ");
    }

    private void fillMIlkCommandOn() {
        turnOnState(State.FILL_MILK, "Write how many ml of milk do you want to add: ");
    }

    private void fillCoffeeCommandOn() {
        turnOnState(State.FILL_COFFEE, "Write how many grams of coffee beans do you want to add: ");
    }
    
    private void fillCupsCommandOn() {
        turnOnState(State.FILL_CUPS, "Write how many disposable cups of coffee do you want to add: ");
    }

    private void turnOnState(State state, String msg) {
        this.state = state;
        System.out.println(msg);
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

    enum TopLevelCommand {
        buy, fill, take, remaining, exit
    }

    enum State {
        TOP_LEVEL_COMMAND,
        BUY_COMMAND,
        FILL_WATER,
        FILL_MILK,
        FILL_COFFEE,
        FILL_CUPS
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        while (true) {
            if (!coffeeMachine.takeCommand(scanner.nextLine())) {
                break;
            }
        }
    }
}
