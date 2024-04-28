package FinalProject;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Forestry Simulation
 *
 * @author Joshua Yepes
 * @version 1.0
 *
 * This class simulates a forestry management system. It allows users to
 * load forests from CSV files, perform operations like adding,
 * cutting, growing, reaping trees, and manage multiple forests.
 */
public class Forestry {

    // scanner object
    private static final Scanner keyboard = new Scanner(System.in);

    /**
     * The main method initializes the forestry simulation and processes command-line arguments.
     *
     * @param args Command-line arguments containing filenames for forest data.
     */

    public static void main(String[] args) {

        Forest myForest;
        int argIndex;
        boolean doNext;
        String currentFilename;

        System.out.println("Welcome to the Forestry Simulation");
        System.out.println("-----------------------------------");
        doNext = true;
        // Loop through command-line arguments
        for (argIndex = 0; doNext && argIndex < args.length; argIndex++) {
            // make whatever name in the command line equal to variable currentFilename
            currentFilename = args[argIndex];
            System.out.println("Initializing from " + currentFilename);

            try {
                // Attempt to read forest data from CSV file
                myForest = Forest.readForestFromCSV(currentFilename);
            } catch (NullPointerException e) {
                System.out.println("Error opening/reading " + currentFilename + ".csv");
                return;
            }

            if (myForest != null) {
                // If the forest object is not null, proceed to display the menu
                doNext = menu(myForest, currentFilename);
            }
        }

        System.out.println();
        System.out.println("Exiting the Forestry Simulation");

    } // end of main


    /**
     * Displays a menu and processes user input to perform various forest management operations.
     *
     * @param forest The Forest object representing the current forest.
     * @param currentFilename The filename of the current forest data.
     * @return true if the program should proceed to the next forest, false otherwise.
     */
    private static boolean menu(Forest forest, String currentFilename) {

        char userChoice;
        double maxHeight;
        int treeIndexNumber;
        String loadFilename;

        do {
            System.out.println();
            // Display menu options
            System.out.print("(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it : ");
            userChoice = keyboard.next().charAt(0);

            switch (Character.toUpperCase(userChoice)) {
                // Print forest details
                case 'P':
                    System.out.println(forest);
                    break;

                case 'A':
                    // Add forest from add method,If the forest object is not null
                    if (forest != null) {
                        forest.add();
                    }
                    break;

                case 'C':
                    // loop the cut option to make it handle errors , so make a boolean to keep it looping
                    boolean isCutValid = false;
                    do {
                        try {
                            System.out.print("Tree number to cut down: ");
                            treeIndexNumber= keyboard.nextInt();
                            if (forest != null) {
                                // cut tree depending on the tree index typed,If the forest object is not null
                                forest.cut(treeIndexNumber);
                            }
                            isCutValid = true;
                        } catch (InputMismatchException e) {
                            // handle if letters are typed
                            System.out.println("That is not an integer. Try again.");
                            keyboard.next();
                        }
                    } while (!isCutValid);
                    break;

                case 'G':
                    // Grows height of forest trees,If the forest object is not null
                    if (forest != null) {
                        forest.grow();
                    }
                    break;

                case 'R':
                    // loop the reap option to handle errors, therefore boolean variable is made
                    boolean isReapValid = false;
                    do {
                        try {
                            System.out.print("Height to reap from: ");
                            maxHeight = keyboard.nextDouble();
                            if (forest != null) {
                                // reap forest trees,If the forest object is not null
                                forest.reap(maxHeight);
                            }
                            isReapValid = true;
                        } catch (InputMismatchException e) {
                            // handle if letters are typed
                            System.out.println("That is not an integer Try again.");
                            keyboard.next();
                        }
                    } while (!isReapValid);
                    break;

                case 'S':
                    // saves forest, by taking object of forest and the currentFilename
                    Forest.save(forest, currentFilename);
                    break;

                case 'L':
                    // load forest
                    System.out.print("Enter filename to load: ");
                    // put in parameter loadFilename to load it
                    loadFilename = keyboard.next();
                    // forest object = load the loadFileName
                    forest = Forest.load(loadFilename);
                    break;

                case 'N':
                    // Moving to the next forest
                    System.out.println("Moving to the next forest");
                    // Signal to the next iteration in the main method
                    return true;

                case 'X':
                    // Signal to the main method to terminate the program
                    return false;

                default:
                    System.out.println("Invalid menu option, try again");
            }
        } while (Character.toUpperCase(userChoice) != 'X');

        // Signal to the main method to terminate the program
        return false;

    } // end of menu


} // end of forestry class
