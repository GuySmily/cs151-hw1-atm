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
        /*
         * Create objects and set up initial states for our scenario
         */
        Bank bankA = new Bank("Bank A", 420767);
        Bank bankB = new Bank("Z Bank", 416724);

        atmA1 = bankA.buildATM(500);
        atmA2 = bankA.buildATM(1000);
        atmB1 = bankB.buildATM(500);
        atmB2 = bankB.buildATM(1000);

        Customer cust1 = new Customer();  // with BankA
        Customer cust2 = new Customer();  // with BankB
        Customer cust3 = new Customer();  // with BankA and BankB

        // Note: card variables are named by bank and customer for convenience.
        Card cardA1 = bankA.openAccount(cust1, 1000);
        Card cardB2 = bankB.openAccount(cust2, 1000);
        Card cardA3 = bankA.openAccount(cust3, 500);
        Card cardB3 = bankB.openAccount(cust3, 500);



        bankA.setCustomerBalance(custA1, 40.0);
        /*
         * Print initial state
         */

        // Input scanner
        Scanner sc = new Scanner(System.in);


        /* ****************************************************************
         * REQUIRED OUTPUTS
         * ****************************************************************
         * Show a list of cards associated with an account at a bank with their expiration dates and passwords
         * Show a list of ATMs for each bank along with how much a card can withdraw from each ATMSystem
         * Prompt user for their desired ATM
         * Prompt user for their card
         * Show the ATM response when the card is somehow invalid (expired, wrong bank)
         * Show the ATM response when the card is valid
         * Prompt user for the password
         * Show the ATM response when the password is invalid
         * Show the ATM response when the password is valid
         * Prompt user for the amount to withdraw
         * Show the ATM response when the desired withdrawal amount is above that specific ATM's daily withdrawal limit
         * Show the ATM response when the desired withdrawal amount is under or at the limit
         * Show the remaining balance of the account after a successful withdrawal
         * Prompt the user for more withdrawals or quit
         */


        /*
         * Generally Code Chef, Hacker Rank gives X number of test cases so we
         * process the input for each.
         */
        final int cases;
        try {
            cases = Integer.parseInt(br.readLine().trim());
            /*
             * Logic of the program must be separated from the meta code to
             * increase readability and help debugging easier
             * Also note that Solver object is created inside for loop to
             * avoid multiple object creation that drastically increases
             * execution time and memory usage
             */
            Solver solver = new Solver();
            for (int i = 0; i < cases; i++) {
                solver.solve(br.readLine());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Print the final output
        System.out.println(output);
    }

}
/*
 * Some basic rules while coding in Programming Contests:
 * Try to follow at least 80% of them
	Correctness
		- final declaration for required data types
		- avoid Object creation
		- Scanner slows down, use InputReader
		- avoid too many static functions
	Efficiency
		- use library functions as much as possible
		- assertEquals("RESULT", functionToCall())
	Debugging-ability
		- avoid too many global variables
		- Separate logic from meta-processing
		- variable/function pneumonics must make sense
 *
 */
class Solver {

    /*
     * Logic goes here ...
     * Add to the global variables after processing the input
     * Maybe reverse a string or parse to an integer or , etc.
     */
    public void solve(String input) {
        Main.output.concat(input);
    }

}