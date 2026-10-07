package com.portifolioproject.app;

import com.portifolioproject.model.User;
import com.portifolioproject.concurrent.PriceUpdateTask;
import com.portifolioproject.model.Asset;
import com.portifolioproject.model.Holding;
import com.portifolioproject.model.Stock;
import com.portifolioproject.model.MutualFund;
import com.portifolioproject.service.PortifolioService;
import com.portifolioproject.util.JsonUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;
import java.util.Comparator;
import com.portifolioproject.util.JVMInfo;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PortifolioService portfolioService = new PortifolioService();

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
            System.out.println("6. Sort Holdings");
            System.out.println("7. Save Data");
            System.out.println("8. Load Data");
            System.out.println("9. Update Stock Prices Concurrently");
            System.out.println("10. JVM Information");
            System.out.println("11. Exit");
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

                    if (portfolioService.userExists(userid)) {

                        System.out.println("User already exists!");

                    } else {

                        User user = new User(userid, name, email);

                        portfolioService.addUser(user);

                        System.out.println("User created successfully!");
                    }

                    break;


                case 2:

                    System.out.println("\n--- Add Stock Holding ---");

                    System.out.print("Enter User ID: ");
                    String stockUserId = sc.nextLine();

                    User stockUser =
                            portfolioService.getUser(stockUserId);

                    if (stockUser == null) {

                        System.out.println("User not found.");
                        break;
                    }

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

                    stockUser.addHolding(stockHolding);

                    System.out.println(
                            "Stock holding added successfully!"
                    );

                    break;


                case 3:

                    System.out.println(
                            "\n--- Add Mutual Fund Holding ---"
                    );

                    System.out.print("Enter User ID: ");
                    String mfUserId = sc.nextLine();

                    User mfUser =
                            portfolioService.getUser(mfUserId);

                    if (mfUser == null) {

                        System.out.println("User not found.");
                        break;
                    }

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

                    mfUser.addHolding(mfHolding);

                    System.out.println(
                            "Mutual fund holding added successfully!"
                    );

                    break;


                case 4:

                    System.out.println(
                            "\n--- Display All Users ---"
                    );

                    boolean found = false;

                    for (User user1 :
                            portfolioService.getAllUsers()) {

                        user1.display();

                        System.out.println(
                                "----------------------------"
                        );

                        found = true;
                    }

                    if (!found) {
                        System.out.println("No users created.");
                    }

                    break;


                case 5:

                    System.out.println("\n--- Holdings ---");

                    boolean userFound = false;

                    for (User user1 :
                            portfolioService.getAllUsers()) {

                        userFound = true;

                        System.out.println(
                                "\nUser ID: "
                                        + user1.getUserid()
                        );

                        System.out.println(
                                "User Name: "
                                        + user1.getName()
                        );

                        if (user1.getHoldings().isEmpty()) {

                            System.out.println(
                                    "No holdings available."
                            );

                        } else {

                            for (Holding holding :
                                    user1.getHoldings()) {

                                System.out.println(holding);
                            }
                        }

                        System.out.println(
                                "----------------------------"
                        );
                    }

                    if (!userFound) {

                        System.out.println("No users created.");
                    }

                    break;


                case 6:

                    System.out.println("\n--- Sort Holdings ---");

                    System.out.println(
                            "1. Sort by Holding ID"
                    );

                    System.out.println(
                            "2. Sort by Quantity"
                    );

                    System.out.print("Enter your choice: ");
                    int sortChoice = sc.nextInt();

                    sc.nextLine();

                    boolean sorted = false;

                    for (User user1 :
                            portfolioService.getAllUsers()) {

                        if (user1.getHoldings().isEmpty()) {
                            continue;
                        }

                        if (sortChoice == 1) {

                            user1.getHoldings().sort(
                                    Comparator.comparing(
                                            Holding::getHoldingId
                                    )
                            );

                            sorted = true;

                        } else if (sortChoice == 2) {

                            user1.getHoldings().sort(
                                    Comparator.comparingInt(
                                            Holding::getQuantity
                                    )
                            );

                            sorted = true;

                        } else {

                            System.out.println(
                                    "Invalid sorting choice."
                            );

                            break;
                        }
                    }

                    if (sorted) {

                        System.out.println(
                                "Holdings sorted successfully."
                        );
                    }

                    break;


                case 7:

                    System.out.println("\n--- Save Data ---");

                    JsonUtil.saveUsers(
                            portfolioService.getAllUsers()
                    );

                    break;


                case 8:

                    System.out.println("\n--- Load Data ---");

                    User[] loadedUsers =
                            JsonUtil.loadUsers();

                    portfolioService.loadUsers(
                            java.util.Arrays.asList(
                                    loadedUsers
                            )
                    );

                    System.out.println(
                            "Data loaded successfully."
                    );

                    break;


                case 9:

                    System.out.println(
                            "\n--- Update Stock Prices Concurrently ---"
                    );

                    ExecutorService executor =
                            Executors.newFixedThreadPool(4);

                    for (User user :
                            portfolioService.getAllUsers()) {

                        for (Holding holding :
                                user.getHoldings()) {

                            Asset asset = holding.getAsset();

                            if (asset instanceof Stock) {

                                Stock stockForUpdate =
                                        (Stock) asset;

                                PriceUpdateTask task =
                                        new PriceUpdateTask(
                                                stockForUpdate
                                        );

                                executor.submit(task);

                                System.out.println(
                                        "Task submitted for: "
                                        + stockForUpdate.getAssetName()
                                );
                            }
                        }
                    }

                    executor.shutdown();

                    break;


                case 10:

                    JVMInfo.displayJVMInfo();

                    break;


                case 11:

                    System.out.println(
                            "\nThank you for using "
                                    + "Stock Portfolio Management System."
                    );

                    break;


                default:

                    System.out.println(
                            "Invalid choice. Please enter 1 to 11."
                    );
            }

        } while (choice != 11);

        sc.close();
    }
}