/*
 * Driver for ATM network system
 */
import java.time.LocalDate;
import java.util.Scanner;


/**
 * @author Enrique Pedrosa
 * StudentID 004004430
 *
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

        // Note: card variables are named by bank and customer for convenience.
        // Ideally these should go in a Customer's Wallet or something.
        Card cardA1 = bankA.openAccount(cust1, "passsword", 1000);
        Card cardB2 = bankB.openAccount(cust2, "passsword", 1000);
        Card cardA3 = bankA.openAccount(cust3, "passsword", 500);
        Card cardB3 = bankB.openAccount(cust3, "passsword", 500);


        /*
         * Print initial state
         */


        // ****************************************************************
        // REQUIRED OUTPUTS
        // ****************************************************************
        // Show a list of cards associated with an account at a bank with their expiration dates and passwords
        bankA.printAccounts();
        bankB.printAccounts();

        // Show a list of ATMs for each bank along with how much a card can withdraw from each ATMSystem
        bankA.printATMs();
        bankB.printATMs();

        // Prompt user for their desired ATM (and bank)
        sc.useDelimiter(""); // Only take one character at a time
        Bank selectedBank = null;
        while (selectedBank == null)
        {
            System.out.print("Go to Bank A (y/n)? ");
            if (sc.next().toLowerCase() == "y"){
                System.out.println();
                selectedBank = bankA;
            }
            else {
                System.out.print("Go to Bank B (y/n)? ");
                if (sc.next().toLowerCase() == "y") {
                    System.out.println();
                    selectedBank = bankB;
                }
            }
        }

        int selectedATM = 0;
        // Note: This assumes that all banks have 2 ATMs
        while (selectedATM < 1 || selectedATM > 2) {
            System.out.print("ATM 1 or 2? ");
            selectedATM = sc.nextInt();
            System.out.println();
        }

        selectedBank.useATM(selectedATM);

        // Note: The remaining prompts are handled by the ATM.

        // Prompt user for their card
        // Show the ATM response when the card is somehow invalid (expired, wrong bank)
        // Show the ATM response when the card is valid
        // Prompt user for the password
        // Show the ATM response when the password is invalid
        // Show the ATM response when the password is valid
        // Prompt user for the amount to withdraw
        // Show the ATM response when the desired withdrawal amount is above that specific ATM's daily withdrawal limit
        // Show the ATM response when the desired withdrawal amount is under or at the limit
        // Show the remaining balance of the account after a successful withdrawal
        // Prompt the user for more withdrawals or quit
    }

}