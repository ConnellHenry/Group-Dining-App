import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.*;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/** Group Dining App
* For situations where 3+ members in a group go out for a meal and argue
* over the bill and what they paid. I created this program in order to 
* control and manage individual orders so that each member can keep a log 
* of what they owe towards the bill with each item they ordered
*
* In addition, a .TXT file can be saved (Called "DiningReceipt") in order
* to keep a permanent record of the bill

To compile:
    javac Dining.java
To run:
    java Dining

Developed by Connell Henry **/

public class Dining {

    public static ArrayList<Item> item = new ArrayList<Item>();
    public static int option = 6;

    public static void main (String args[]) {
        String guestName;
        double starterPrice;
        double mainPrice;
        double extrasPrice;
        double dessertPrice;

        int numOfDrinks;
        double firstDrink;
        double secondDrink;
        double thirdDrink;

        mainMenu(option); // Initial Call from program run
    }

    public static int mainMenu(int option) {
        Scanner opInput = new Scanner(System.in);

        System.out.println();
        System.out.println("~~ Group Dining App ~~");
        System.out.println("__________________________________________");
        System.out.println("Enter '1' to Add an Order to the list");
        System.out.println("Enter '2' to Remove an Order");
        System.out.println("Enter '3' to show the total Price");
        System.out.println("Enter '4' to Show Individual Member Prices");
        System.out.println("Enter '5' to Save this to a .TXT File");
        System.out.println("Enter '0' to Exit");
        System.out.println("__________________________________________");
        System.out.println();
        System.out.print("Option Number: ");
        option = opInput.nextInt();

        switch(option) {
            case 1:        // ADD
                addItem();
                break;
            case 2:        // Remove
                removeItem();
                break;
            case 3:        // Total
                totalItems();
                break;
            case 4:        // Individual
                individualItems();
                break;
            case 5:        // Save
                saveItems();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.print(" Error. Please try again");
                break;
        }

        return option;
    }

    public static class Item {
        private String name;
        private String foodItem;
        private double foodItemCost;
        private String drinkItem;
        private double drinkItemCost;

        public Item(String name, String foodItem, double foodItemCost, String drinkItem, double drinkItemCost) {

            this.name = name;
            this.foodItem = foodItem;
            this.foodItemCost = foodItemCost;
            this.drinkItem = drinkItem;
            this.drinkItemCost = drinkItemCost;

        }

        public String getName() {
            return name;
        }
        public String getFoodItem() {
            return foodItem;
        }
        public double getFoodItemCost() {
            return foodItemCost;
        }
        public String getDrinkItem() {
            return drinkItem;
        }
        public double getDrinkItemCost() {
            return drinkItemCost;
        }

        @Override
        public String toString() {
            return "\n NAME: " + this.name + ", FOOD: " + this.foodItem + ", FOOD PRICE: " + this.foodItemCost + ", DRINK: " + this.drinkItem + ", DRINK PRICE: " + this.drinkItemCost + "\n";
        }
    }

    public static void addItem() { // Opt 1
        String nameIn = "";
        String foodIn = "";
        double foodCostIn = 0.0;
        String drinkIn = "";
        double drinkCostIn = 0.0;
        int index = 0;

        Scanner dataInput = new Scanner(System.in);
        System.out.println("Adding a New Order");
        System.out.print("Please Enter a Name: ");
            nameIn = dataInput.nextLine();
        System.out.print("Please Enter the Food Order: ");
            foodIn = dataInput.nextLine();
        System.out.print("Please Enter the Food Price: ");
            foodCostIn = dataInput.nextDouble();
        System.out.print("Please Enter the Drink Order: ");
            drinkIn = dataInput.nextLine();
        System.out.print("Please Enter the Drink Price: ");
            drinkCostIn = dataInput.nextDouble();	

        item.add(new Item(nameIn, foodIn, foodCostIn, drinkIn, drinkCostIn));

        index ++;
        System.out.println("Index Number = " + index + ": " + item);
        mainMenu(option);
    }

    public static void removeItem() { // Opt 2
        Scanner nameINput = new Scanner(System.in);
        System.out.print("Please enter the name of the guest that you would like to remove: ");
        String search = nameINput.nextLine();
        Iterator<Item> it = item.iterator();
            while(it.hasNext()) {
                Item itemS = it.next();
                    if(itemS.getName().equals(search)) {
                        it.remove();
                    }
            }
        mainMenu(option);
    }

    public static void totalItems() { // Opt 3
        double cost;
        double total = 0;
            for(int i = 0; i < item.size(); i ++) {
                Item price = item.get(i);
                cost = price.getFoodItemCost() + price.getDrinkItemCost();
                total += cost;
                System.out.println();
                System.out.println("For " + price.getName(/*i*/));
                System.out.println("TOTAL COST IS: " + cost);
                System.out.println();
            }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The total for this meal is " + total);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        mainMenu(option);
    }

    public static void individualItems() { // Opt 4
        double iCost;
        double tCost = 0;
        Scanner nameInput = new Scanner(System.in);
        System.out.print("Please enter the name of the guest for their bill: ");
        String nameSearch = nameInput.nextLine();
            for(int iT = 0; iT < item.size(); iT++) {
                Item nM = item.get(iT);
                    if(nM.getName().equals(nameSearch)) {
                        iCost = nM.getFoodItemCost() + nM.getDrinkItemCost();
                        tCost += iCost;
                    }
            }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(nameSearch + " Needs to pay " + tCost);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        mainMenu(option);
    }

    public static void saveItems() { // Opt 5
        System.out.println("Saving...");
            try {
                File f = new File("DiningReceipt.txt");
                    if(!f.exists()) {
                        f.createNewFile();
                    }
                FileWriter fw = new FileWriter(f.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                    for(Object s : item) {
                        bw.write(s + System.getProperty("line.separator"));
                    }
                bw.close();
            } catch(IOException ioe) {
                System.out.println(ioe);
            }
        System.out.println("Saved");
        mainMenu(option);
    }

}