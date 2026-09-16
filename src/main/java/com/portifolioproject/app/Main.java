package com.portifolioproject.app;

import com.portifolioproject.model.User;
import com.portifolioproject.model.Stock;
import com.portifolioproject.model.MutualFund;
import com.portifolioproject.model.Holding;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User user = null;

        int choice;

        System.out.println("=====================================");
        System.out.println("   STOCK PORTFOLIO MANAGEMENT SYSTEM");
        System.out.println("=====================================");

        do {

            System.out.println("\n----------- MENU -----------");
            System.out.println("1. Create User");
            System.out.println("2. Add Stock Holding");
            System.out.println("3. Add Mutual Fund Holding");
            System.out.println("4. Display User");
            System.out.println("5. Display Holdings");
            System.out.println("6. Exit");
            System.out.println("----------------------------");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.println("\n--- Create User ---");

                    System.out.print("Enter User ID: ");
                    String userid = sc.nextLine();

                    System.out.print("Enter User Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    user = new User(userid, name, email);

                    System.out.println("User created successfully!");

                    break;

                case 2:

                    if (user == null) {
                        System.out.println("Please create a user first.");
                        break;
                    }

                    System.out.println("\n--- Add Stock Holding ---");

                    System.out.print("Enter Holding ID: ");
                    String stockHoldingId = sc.nextLine();

                    System.out.print("Enter Stock ID: ");
                    String stockId = sc.nextLine();

                    System.out.print("Enter Stock Name: ");
                    String stockName = sc.nextLine();

                    System.out.print("Enter Purchase Price: ");
                    double purchasePrice = sc.nextDouble();

                    System.out.print("Enter Current Price: ");
                    double currentPrice = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();

                    sc.nextLine();

                    Stock stock = new Stock(
                            stockId,
                            stockName,
                            purchasePrice,
                            currentPrice
                    );

                    Holding stockHolding = new Holding(
                            stockHoldingId,
                            stock,
                            quantity
                    );

                    user.addHolding(stockHolding);

                    System.out.println("Stock holding added successfully!");

                    break;

                case 3:

                    if (user == null) {
                        System.out.println("Please create a user first.");
                        break;
                    }

                    System.out.println("\n--- Add Mutual Fund Holding ---");

                    System.out.print("Enter Holding ID: ");
                    String mfHoldingId = sc.nextLine();

                    System.out.print("Enter Mutual Fund ID: ");
                    String mfId = sc.nextLine();

                    System.out.print("Enter Mutual Fund Name: ");
                    String mfName = sc.nextLine();

                    System.out.print("Enter Purchase Price: ");
                    double mfPurchasePrice = sc.nextDouble();

                    System.out.print("Enter NAV: ");
                    double nav = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int mfQuantity = sc.nextInt();

                    sc.nextLine();

                    MutualFund mutualFund = new MutualFund(
                            mfId,
                            mfName,
                            mfPurchasePrice,
                            nav
                    );

                    Holding mfHolding = new Holding(
                            mfHoldingId,
                            mutualFund,
                            mfQuantity
                    );

                    user.addHolding(mfHolding);

                    System.out.println("Mutual fund holding added successfully!");

                    break;

                case 4:

                    if (user == null) {

                        System.out.println("No user created.");

                    } else {

                        System.out.println("\n--- User Details ---");
                        user.display();
                    }

                    break;

                case 5:

                    if (user == null) {
                        System.out.println("Please create a user first.");
                        break;
                    }

                    System.out.println("\n--- Holdings ---");

                    if (user.getHoldings().isEmpty()) {

                        System.out.println("No holdings available.");

                    } else {

                        for (Holding holding : user.getHoldings()) {
                            System.out.println(holding);
                        }
                    }

                    break;

                case 6:

                    System.out.println(
                            "\nThank you for using Stock Portfolio Management System."
                    );

                    break;

                default:

                    System.out.println(
                            "Invalid choice. Please enter 1 to 6."
                    );
            }

        } while (choice != 6);

        sc.close();
    }
}