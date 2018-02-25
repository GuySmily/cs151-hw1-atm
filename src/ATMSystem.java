/*
 * Driver for ATM network system
 */
import java.time.LocalDate;
import java.util.Scanner;


/**
 * @author Enrique Pedrosa
 * StudentID 004004430
 * Initializes some bank/ATM systems and customers and accepts inputs for ATM system
 */
public class ATMSystem {
    // Program's starting point
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        /*
         * Create objects and set up initial states for our scenario
         */
        Bank bankA = new Bank("Bank A", "420767");
        Bank bankB = new Bank("Z Bank", "416724");

        bankA.buildATM(1, 500);
        bankA.buildATM(2, 1000);
        bankB.buildATM(1, 500);
        bankB.buildATM(2, 1000);

        Customer cust1 = new Customer();  // with BankA
        Customer cust2 = new Customer();  // with BankB
        Customer cust3 = new Customer();  // with BankA and BankB

        // Each account comes with a card, which is stored in bank database (arraylist for now)
        bankA.openAccount(cust1, "passsword", 1000);
        bankB.openAccount(cust2, "passsword", 1000);
        bankA.openAccount(cust3, "passsword", 500);
        bankB.openAccount(cust3, "passsword", 500);

        // ****************************************************************
        // REQUIRED OUTPUTS
        // ****************************************************************
        // Show a list of cards associated with an account at a bank with their expiration dates and passwords
        bankA.printAccounts();
        System.out.println();
        bankB.printAccounts();
        System.out.println();

        // Show a list of ATMs for each bank along with how much a card can withdraw from each ATMSystem
        bankA.printATMs();
        bankB.printATMs();
        System.out.println();

        boolean quit = false;

        while (!quit) {
            // Prompt user for their desired ATM (and bank)
            //sc.useDelimiter(""); // Only take one character at a time
            Bank selectedBank = null;
            while (selectedBank == null) {
                System.out.print("Go to " + bankA.getName() + " (y/n)? ");
                if (String.valueOf(sc.next().charAt(0)).toLowerCase().equals("y")) {
                    //System.out.println();
                    selectedBank = bankA;
                } else {
                    System.out.print("Go to " + bankB.getName() + " (y/n)? ");
                    if (String.valueOf(sc.next().charAt(0)).toLowerCase().equals("y")) {
                        //System.out.println();
                        selectedBank = bankB;
                    }
                }
            }

            int selectedATM = 0;
            // Note: This assumes that all banks have 2 ATMs
            while (selectedATM < 1 || selectedATM > 2) {
                System.out.print("ATM 1 or 2? ");
                selectedATM = sc.nextInt(); sc.nextLine();  // Eat the newline
                System.out.println();
            }

            selectedBank.useATM(selectedATM);

            // Note: The remaining prompts are handled by the ATM.

            // Prompt the user for more withdrawals or quit
            System.out.print("Go to another bank (y/n)? ");
            quit = String.valueOf(sc.next().charAt(0)).toLowerCase().equals("n");
        }
    }

}