package com.portifolioproject.app;

import com.portifolioproject.model.User;
import com.portifolioproject.model.Stock;
import com.portifolioproject.model.MutualFund;
import com.portifolioproject.model.Holding;

//To change ArrayList to HashMap add the below code
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // Store all users
   // static List<User> users = new ArrayList<>();

	//change the users declaration
	static Map<String, User>users = new HashMap<>();
	
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

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

                // =========================================
                // CASE 1: CREATE USER
                // =========================================
                case 1:

                    System.out.println("\n--- Create User ---");

                    System.out.print("Enter User ID: ");
                    String userid = sc.nextLine();

                    System.out.print("Enter User Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                  
                    // Add user to users list
                   
                    //Change Case 1
                    User newUser = new User(userid, name, email);
                    
                    //Add user to HashMap
                    users.put(userid, newUser); // userid user-->object
                    
                    System.out.println("User created successfully!");

                    break;


                // =========================================
                // CASE 2: ADD STOCK HOLDING
                // =========================================
                case 2:

                    if (users.isEmpty()) {
                        System.out.println("Please create a user first.");
                        break;
                    }

                    System.out.println("\n--- Add Stock Holding ---");

                    // Ask which user owns this holding
                    System.out.print("Enter User ID: ");
                    String stockUserId = sc.nextLine();

                    User stockUser = findUser(stockUserId);

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

                    // Add holding to selected user
                    stockUser.addHolding(stockHolding);

                    System.out.println("Stock holding added successfully!");

                    break;


                // =========================================
                // CASE 3: ADD MUTUAL FUND HOLDING
                // =========================================
                case 3:

                    if (users.isEmpty()) {
                        System.out.println("Please create a user first.");
                        break;
                    }

                    System.out.println("\n--- Add Mutual Fund Holding ---");

                    // Ask which user owns this holding
                    System.out.print("Enter User ID: ");
                    String mfUserId = sc.nextLine();

                    User mfUser = findUser(mfUserId);

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

                    // Add holding to selected user
                    mfUser.addHolding(mfHolding);

                    System.out.println("Mutual fund holding added successfully!");

                    break;


                // =========================================
                // CASE 4: DISPLAY ALL USERS
                // =========================================
                case 4:

                    if (users.isEmpty()) {

                        System.out.println("No users created.");

                    } else {

                        System.out.println("\n--- User Details ---");
                        
                        for (User user : users.values ())
                         {
                        	 user.display();
                        	 System.out.println("---------------------------");                          
                        }
                    }

                    break;


                // =========================================
                // CASE 5: DISPLAY ALL HOLDINGS USER-WISE
                // =========================================
                case 5:

                    if (users.isEmpty()) {

                        System.out.println("No users created.");

                        break;
                    }

                    System.out.println("\n--- Holdings ---");

                    for (User user : users.values())
                    {

                        System.out.println("\nUser ID: " + user.getUserid());
                        System.out.println("User Name: " + user.getName());

                        if (user.getHoldings().isEmpty()) {

                            System.out.println("No holdings available.");

                        } else {

                            for (Holding holding : user.getHoldings()) {

                                System.out.println(holding);
                            }
                        }

                        System.out.println("----------------------------");
                    }

                    break;


                // =========================================
                // CASE 6: EXIT
                // =========================================
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


    // =========================================
    // FIND USER BY USER ID
    // =========================================
    
    public static User findUser (String userid)
    {
    	return users.get(userid);
    }
    }