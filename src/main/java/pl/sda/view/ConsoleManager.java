package pl.sda.view;

import java.io.IOException;
import java.util.Scanner;

public class ConsoleManager {

    private final EmployeeManager employeeManager = new EmployeeManager();

    public static void clrscr() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    public void start() {
        char userChoice = ' ';
        while (userChoice != 'q') {
            printMenu();
            userChoice = readChar();
            executeAction(userChoice);
        }

    }

    private void printMenu() {
        clrscr();
        System.out.println("Menu:");
        System.out.println("1 - Lista pracowników");;
        System.out.println("2 - Dodaj pracownika");
        System.out.println("3 - Wyświetl pracownika");
        System.out.println("4 - Usuń pracownika");
        System.out.println("5 - Odświerz dane pracownika");
        System.out.println();
        System.out.println("q - wyjście");

    }

    private void executeAction(char userChoice) {
        switch (userChoice) {
            case '1':
                employeeManager.printList();
                pressEnterKeyToContinue();
                break;
            case '2':
                employeeManager.addEmployee();
                break;
            case '3':
                employeeManager.printEmployee();
                pressEnterKeyToContinue();
                break;
            case '4':
                employeeManager.removeEmployee();
                break;
            case '5':
                employeeManager.updateEmployee();
                break;
        }
    }

    public void pressEnterKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        readChar();
    }

    private char readChar() {
        Scanner s = new Scanner(System.in);
        return (char) s.nextLine().chars().findFirst().orElse(0);

    }
}
