import java.util.Scanner; // Importing Scanner Class
import java.util.ArrayList; // Importing ArrayList class

/*
 * Author: Jordan Kuyon
 * Purpose of Program: The modeling of a real-life object of a Gift Hierarchy
 * Date: 06/18/2023
 */

// Parent Class - Gift
class Gift {
	
	// Encapsulation - Private attributes for basket properties
    private String orderNumber;
    private int numFruits;
    private char size;
    private double price;

    
    // Constructor
    public Gift(String orderNumber, int numFruits, char size) {
        this.orderNumber = orderNumber;
        this.numFruits = numFruits;
        this.size = size;
    }

    // Getters
    
    public String getOrderNumber() {
        return orderNumber;
    }

    public int getNumFruits() {
        return numFruits;
    }

    public char getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    
    public void setPrice(double price) {
        this.price = price;
    }

    public void setSize(char size) {
        this.size = size;
    }

    public void displayDetails() {
        System.out.printf("%s: A %s GiftBasket with %d Fruits costing $%.2f%n", orderNumber, getSizeName(), numFruits, price);
    }

    protected String getSizeName() {
        switch (size) {
            case 'S':
                return "small";
            case 'M':
                return "medium";
            case 'L':
                return "large";
            default:
                return "unknown";
        }
    }

    // Method to validate order number
    protected boolean validateOrderNumber(String orderNumber) {
        if (orderNumber.length() != 5) {
            return false;
        }

        String prefix = orderNumber.substring(0, 2);
        String suffix = orderNumber.substring(2);

        if (!prefix.equals("FB") && !prefix.equals("SB")) {
            return false;
        }

        for (char c : suffix.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }
}

// 1st Child Class - FruitBasket
class FruitBasket extends Gift {
    private boolean hasCitrus;
    
    // Constructor
    public FruitBasket(String orderNumber, int numFruits, char size, boolean hasCitrus) {
        super(orderNumber, numFruits, size);
        this.hasCitrus = hasCitrus;
    }
    // return true or false to hasCitrus
    public boolean hasCitrus() {
        return hasCitrus;
    }

    // Method to set hasCitrus to true or false
    public void setHasCitrus(boolean hasCitrus) {
        this.hasCitrus = hasCitrus;
    }

    // Overriding Method - alternative to toString()
    @Override
    public void displayDetails() {
        String citrusInfo = hasCitrus ? "with citrus" : "without citrus";
        System.out.printf("%s: A %s FruitBasket with %d Fruits %s costing $%.2f%n", getOrderNumber(), getSizeName(),
                getNumFruits(), citrusInfo, getPrice());
    }
}

// 2nd Child Class - Sweets BAsket
class SweetsBasket extends Gift {
    private boolean hasNuts;
    
    // Constructor
    public SweetsBasket(String orderNumber, int numFruits, char size, boolean hasNuts) {
        super(orderNumber, numFruits, size);
        this.hasNuts = hasNuts;
    }

    // Method to return true or false to hasNuts
    public boolean hasNuts() {
        return hasNuts;
    }

    // Setter for Nuts
    public void setHasNuts(boolean hasNuts) {
        this.hasNuts = hasNuts;
    }

    // Overriding Method - alternative to toString()
    @Override
    public void displayDetails() {
        String nutsInfo = hasNuts ? "with nuts" : "without nuts";
        System.out.printf("%s: A %s SweetsBasket with %d Fruits %s costing $%.2f%n", getOrderNumber(), getSizeName(),
                getNumFruits(), nutsInfo, getPrice());
    }
}

// Main Class
public class GiftHeirarchy {
	
	// Create ArrayList for GiftOrders
    private static final ArrayList<Gift> giftOrders = new ArrayList<>();
    
    // User Input for private attributes
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
    	
    	// Instance of Gift Class
    	Gift gift = new Gift(null, 0, (char) 0);
    	
    	// Variable for menu selection
        int select;
        
        // Create Scanner class for menu selection
        Scanner menuSelection = new Scanner(System.in);
        
        
        
        do {
            displayMenu();
            select = menuSelection.nextInt();

            switch (select) {
                case 1: // Order Gift
                    orderGiftBasket();
                    break;
                case 2: // Change Gift
                    changeGiftBasket();
                    break;
                case 3: // Display Gift Order
                    displaySingleGiftBasket();
                    break;
                case 4: // Display All Gift Orders
                    displayAllGiftBaskets();
                    break;
                case 9: // Exit Program
                	System.out.println();
                    System.out.println("Thank you for using the program. Goodbye!");
                    break;
                default: // Re-enter value on invalid input
                	System.out.println();
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (select != 9);
    }

    // Display Menu Prompts
    private static void displayMenu() {
        System.out.println("\nMENU");
        System.out.println("1: Order a Gift Basket");
        System.out.println("2: Change Gift Basket");
        System.out.println("3: Display Gift Basket");
        System.out.println("4: Display all Gift Baskets");
        System.out.println("9: Exit program");
        System.out.print("\nEnter your selection: ");
    }

    // Method to Order Gift
    private static void orderGiftBasket() {
    	// Specigy gift type
        System.out.print("\nDo you want Fruit Basket (1) or Sweets Basket (2): ");
        int basketType = Integer.parseInt(scanner.nextLine());

        // Specify order number
        if (basketType == 1) {
            System.out.print("Create order number [FB or SB for type of gift and three integers]: ");
            String orderNumber = scanner.nextLine();
            if (!validateOrderNumber(orderNumber)) {
                System.out.println("Please re-enter order number: ");
                return;
            }
            // Specify size on user input
            System.out.print("What size do you want: S, M, or L: ");
            char size = scanner.nextLine().toUpperCase().charAt(0);
            // Specify Citrus Option
            System.out.print("Do you want citrus fruits included? 1 for yes/2 for no: ");
            int citrusChoice = Integer.parseInt(scanner.nextLine());
            boolean hasCitrus = citrusChoice == 1;

            FruitBasket fruitBasket = new FruitBasket(orderNumber, getNumFruits(size), size, hasCitrus);
            double price = calculatePrice(fruitBasket);
            fruitBasket.setPrice(price);

            giftOrders.add(fruitBasket);
            System.out.println("Gift Basket ordered successfully.");
        } else if (basketType == 2) {
            System.out.print("Create order number [FB or SB for type of gift and three integers]: ");
            String orderNumber = scanner.nextLine();
            if (!validateOrderNumber(orderNumber)) {
                System.out.println("Invalid order number. Please try again.");
                return;
            }

            System.out.print("What size do you want: S, M, or L: ");
            char size = scanner.nextLine().toUpperCase().charAt(0);
            // Specify Nuts Option
            System.out.print("Do you want nuts included? 1 for yes/2 for no: ");
            int nutsChoice = Integer.parseInt(scanner.nextLine());
            boolean hasNuts = nutsChoice == 1;

            SweetsBasket sweetsBasket = new SweetsBasket(orderNumber, getNumFruits(size), size, hasNuts);
            double price = calculatePrice(sweetsBasket);
            sweetsBasket.setPrice(price);

            giftOrders.add(sweetsBasket);
            System.out.println("Gift Basket ordered successfully.");
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    // Method to return number of fruits based on size
    private static int getNumFruits(char size) {
        switch (size) {
            case 'S':
                return 6;
            case 'M':
                return 9;
            case 'L':
                return 15;
            default:
                return 0;
        }
    }

    // Overloading Method to calculate price of each gift order
    private static double calculatePrice(Gift gift) {
        double basePrice;
        switch (gift.getSize()) {
            case 'S':
                basePrice = 19.99;
                break;
            case 'M':
                basePrice = 29.99;
                break;
            case 'L':
                basePrice = 39.99;
                break;
            default:
                basePrice = 0;
                break;
        }

        // If FruitBasket or SweetsBasket is a part of gift
        if (gift instanceof FruitBasket) {
            FruitBasket fruitBasket = (FruitBasket) gift;
            if (fruitBasket.hasCitrus()) {
                basePrice += 5.99;
            }
        } else if (gift instanceof SweetsBasket) {
            SweetsBasket sweetsBasket = (SweetsBasket) gift;
            if (sweetsBasket.hasNuts()) {
                basePrice += 4.49;
            }
        }

        return basePrice; // $0.00
    }

    // Method to Change the attributes of a certain gift order
    private static void changeGiftBasket() {
        System.out.print("\nWhich gift do you want to change? ");
        String orderNumber = scanner.nextLine();

        Gift gift = findGiftBasket(orderNumber);
        if (gift != null) {
            System.out.println("\nSelected Gift Basket:");
            gift.displayDetails();

            System.out.print("\nWhat size do you want: S, M, or L: ");
            char newSize = scanner.nextLine().toUpperCase().charAt(0);
            gift.setSize(newSize);

            if (gift instanceof FruitBasket) {
                FruitBasket fruitBasket = (FruitBasket) gift;
                System.out.print("Do you want to change the citrus fruits? 1 for yes/2 for no: ");
                int citrusChoice = Integer.parseInt(scanner.nextLine());
                fruitBasket.setHasCitrus(citrusChoice == 1);
            } else if (gift instanceof SweetsBasket) {
                SweetsBasket sweetsBasket = (SweetsBasket) gift;
                System.out.print("Do you want to change the nuts? 1 for yes/2 for no: ");
                int nutsChoice = Integer.parseInt(scanner.nextLine());
                sweetsBasket.setHasNuts(nutsChoice == 1);
            }

            double newPrice = calculatePrice(gift);
            gift.setPrice(newPrice);

            System.out.println("Gift Basket changed successfully.");
        } else {
            System.out.println("Gift Basket not found.");
        }
    }

    // Method to search for gift order to assist with change and display
    private static Gift findGiftBasket(String orderNumber) {
        for (Gift gift : giftOrders) {
            if (gift.getOrderNumber().equals(orderNumber)) {
                return gift;
            }
        }
        return null;
    }

    // Method to display and validate a single gift order in ArrayList
    private static void displaySingleGiftBasket() {
    	if (giftOrders.isEmpty()) {
        	System.out.println("No Gift has been ordered yet");
        } else {        	
        	System.out.print("\nEnter the order number of the gift basket you want to display: ");
        	String orderNumber = scanner.nextLine();
        	
        	Gift gift = findGiftBasket(orderNumber);
        	if (gift != null) {
        		System.out.println("\nOrder Number: " + orderNumber);
        		gift.displayDetails();
        	} else {
        		System.out.println("Gift Basket not found.");
        	}
        }
        
        
    }

    // Method to display and validate a all gift orders in ArrayList
    private static void displayAllGiftBaskets() {
    	if (giftOrders.isEmpty()) {
        	System.out.println("No Gift has been ordered yet");
        } else {        	
        	System.out.println("\nAll Gift Orders:");
        	for (Gift gift : giftOrders) {
        		gift.displayDetails();
        	}
        }
    }

    // Method to validate order number based on user input
    private static boolean validateOrderNumber(String orderNumber) {
        if (orderNumber.length() != 5) {
            return false;
        }

        String prefix = orderNumber.substring(0, 2);
        String suffix = orderNumber.substring(2);

        if ((!prefix.equals("FB") && !prefix.equals("SB")) || !suffix.matches("\\d{3}")) {
            return false;
        }

        return true;
    }
}