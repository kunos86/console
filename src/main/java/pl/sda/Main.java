package pl.sda;

import pl.sda.view.ConsoleManager;

public class Main {

    public static void main(String[] args) {
        EmployeeEntityManager.open();
        ConsoleManager consoleManager = new ConsoleManager();
        consoleManager.start();
        EmployeeEntityManager.close();
    }


}
