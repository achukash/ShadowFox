package Beginner;

import java.util.Scanner;

public class EnhancedCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nEnhanced Console Calculator");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Square Root");
            System.out.println("6. Exponentiation");
            System.out.println("7. Temperature Conversion (Celsius to Fahrenheit)");
            System.out.println("8. Currency Conversion (USD to INR)");
            System.out.println("9. Exit");
            System.out.print("Choose an operation (1-9): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    performAddition(scanner);
                    break;
                case 2:
                    performSubtraction(scanner);
                    break;
                case 3:
                    performMultiplication(scanner);
                    break;
                case 4:
                    performDivision(scanner);
                    break;
                case 5:
                    performSquareRoot(scanner);
                    break;
                case 6:
                    performExponentiation(scanner);
                    break;
                case 7:
                    performTemperatureConversion(scanner);
                    break;
                case 8:
                    performCurrencyConversion(scanner);
                    break;
                case 9:
                    running = false;
                    System.out.println("Exiting the calculator. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void performAddition(Scanner scanner) {
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        System.out.println("Result: " + (num1 + num2));
    }

    private static void performSubtraction(Scanner scanner) {
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        System.out.println("Result: " + (num1 - num2));
    }

    private static void performMultiplication(Scanner scanner) {
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        System.out.println("Result: " + (num1 * num2));
    }

    private static void performDivision(Scanner scanner) {
        System.out.print("Enter numerator: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter denominator: ");
        double num2 = scanner.nextDouble();
        if (num2 == 0) {
            System.out.println("Error: Division by zero is not allowed.");
        } else {
            System.out.println("Result: " + (num1 / num2));
        }
    }

    private static void performSquareRoot(Scanner scanner) {
        System.out.print("Enter a number: ");
        double num = scanner.nextDouble();
        if (num < 0) {
            System.out.println("Error: Square root of a negative number is not allowed.");
        } else {
            System.out.println("Result: " + Math.sqrt(num));
        }
    }

    private static void performExponentiation(Scanner scanner) {
        System.out.print("Enter base: ");
        double base = scanner.nextDouble();
        System.out.print("Enter exponent: ");
        double exponent = scanner.nextDouble();
        System.out.println("Result: " + Math.pow(base, exponent));
    }

    private static void performTemperatureConversion(Scanner scanner) {
        System.out.print("Enter temperature in Celsius: ");
        double celsius = scanner.nextDouble();
        double fahrenheit = (celsius * 9 / 5) + 32;
        System.out.println("Temperature in Fahrenheit: " + fahrenheit);
    }

    private static void performCurrencyConversion(Scanner scanner) {
        System.out.print("Enter amount in USD: ");
        double usd = scanner.nextDouble();
        double inr = usd * 86.62;
        System.out.println("Amount in INR: " + inr);
    }
}