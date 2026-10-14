
package com.expensetracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Expense> expenses = new ArrayList<>();
    private static int nextId = 1;

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n-------------------------------");
            System.out.println("   PERSONAL EXPENSE TRACKER");
            System.out.println("-------------------------------");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Search by Category");
            System.out.println("4. Delete Expense");
            System.out.println("5. Show Total Expenses");
            System.out.println("6. Exit");
            System.out.println("-----------------------------------");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    searchByCategory();
                    break;
                case 4:
                    deleteExpense();
                    break;
                case 5:
                    showTotal();
                    break;
                case 6:
                    System.out.println("Thank you for using the application!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);

        sc.close();
    }

    private static void addExpense() {

        double amount = readAmount("Enter amount (Rs.): ");

        System.out.println("Categories: Food, Travel, Shopping, Bills, "
                + "Education, Health, Others");
        System.out.print("Enter category: ");
        String category = sc.nextLine().trim();

        if (category.isEmpty()) {
            System.out.println("Category cannot be empty.");
            return;
        }

        System.out.print("Enter description: ");
        String description = sc.nextLine().trim();

        if (description.isEmpty()) {
            description = "No description";
        }

        Expense expense = new Expense(
                nextId, amount, category, description, LocalDate.now()
        );

        expenses.add(expense);

        System.out.println("Expense added successfully!");
        System.out.println("Expense ID: " + nextId);
        nextId++;
    }

    private static void viewExpenses() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }

        printHeader();

        for (Expense expense : expenses) {
            expense.displayExpense();
        }
    }

    private static void searchByCategory() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses available to search.");
            return;
        }

        System.out.print("Enter category to search: ");
        String category = sc.nextLine().trim();

        boolean found = false;

        printHeader();

        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                expense.displayExpense();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No expenses found in this category.");
        }
    }

    private static void deleteExpense() {

        int id = readInt("Enter expense ID to delete: ");

        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                expenses.remove(expense);
                System.out.println("Expense deleted successfully!");
                return;
            }
        }

        System.out.println("Expense ID not found.");
    }

    private static void showTotal() {

        double total = 0;

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        System.out.printf("Total expenses: Rs. %.2f%n", total);
        System.out.println("Number of expenses: " + expenses.size());
    }

    private static void printHeader() {
        System.out.printf("%-5s %-12s %-15s %-20s %s%n",
                "ID", "Amount", "Category", "Description", "Date");
        System.out.println(
                "------------------------------------------------------------------"
        );
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readAmount(String message) {
        while (true) {
            System.out.print(message);

            try {
                double amount = Double.parseDouble(sc.nextLine().trim());

                if (Double.isFinite(amount) && amount > 0) {
                    return amount;
                }

                System.out.println("Amount must be a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
