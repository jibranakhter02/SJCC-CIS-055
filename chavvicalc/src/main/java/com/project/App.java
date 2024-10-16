package com.chavvicalc;
import java.util.Scanner;
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double A = 0, B = 0;
        String input;

        while (true) {
            // Display the UI
            printMenu(A, B);
            // Get user input
            input = scanner.next();
            switch (input) {
                case "a":
                    System.out.print("Enter a number for A: ");
                    A = scanner.nextDouble();
                    break;
                case "b":
                    System.out.print("Enter a number for B: ");
                    B = scanner.nextDouble();
                    break;
                case "+":
                    A = A + B;
                    System.out.println("A = A + B. Now A = " + A);
                    break;
                case "-":
                    A = A - B;
                    System.out.println("A = A - B. Now A = " + A);
                    break;
                case "*":
                    A = A * B;
                    System.out.println("A = A * B. Now A = " + A);
                    break;
                case "/":
                    if (B != 0) {
                        A = A / B;
                        System.out.println("A = A / B. Now A = " + A);
                    } else {
                        System.out.println("Error: Division by zero is not allowed.");
                    }
                    break;
                case "c":
                    A = 0;
                    B = 0;
                    System.out.println("Values cleared. A = 0, B = 0.");
                    break;
                case "q":
                    System.out.println("Quitting ChavviCalc. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        }
    }