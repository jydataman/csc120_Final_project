package FinalProject;
import java.io.Serializable;
import java.util.Random;

/**
 * Represents a tree with attributes such as species, year of planting, height, and growth rate.
 * Trees can grow annually and be generated with random attributes.
 *
 */

public class Tree implements Serializable {

// Constants for defining the range of attributes for a tree

    /** The maximum year for planting a tree. */
    public final static int MAX_YEAR = 2024;

    /** The minimum year for planting a tree. */
    public final static int MIN_YEAR = 2000;

    /** The minimum height of a tree. */
    public final static double MIN_HEIGHT = 10.0;

    /** The maximum height of a tree. */
    public final static double MAX_HEIGHT = 20.0;

    /** The minimum growth rate of a tree per year. */
    public final static double MIN_GROWTH_RATE = 10.0;

    /** The maximum growth rate of a tree per year. */
    public final static double MAX_GROWTH_RATE = 20.0;

    // Enum for tree species
    public enum Species {Birch, Maple, Fir}

    // Tree attributes
    private Species species;
    private int yearOfPlanting;
    private double heightOfTree;
    private double growthRatePerYear;

    /**
     * Constructs a Tree object with the specified attributes.
     *
     * @param species         the species of the tree
     * @param yearOfPlanting  the year when the tree was planted
     * @param height          the height of the tree
     * @param growthRate      the growth rate of the tree per year
     */
    public Tree(Species species, int yearOfPlanting, double height, double growthRate) {

        this.species = species;
        this.yearOfPlanting = yearOfPlanting;
        this.heightOfTree = height;
        this.growthRatePerYear = growthRate;

    } // end of Tree Constructor

    /**
     * Returns the height of the tree.
     *
     * @return the height of the tree
     */

    // Getter method for getting the height of a tree
    public double getHeight() {

        return heightOfTree;

    } // end of getHeight


    /**
     * Simulates annual growth of the tree based on its growth rate.
     */
    public void growPerYear() {

        double growth = heightOfTree * (growthRatePerYear / 100.0);

        heightOfTree += growth;

    } // end of growPerYear

    /**
     * Generates a random Tree object with random attributes.
     *
     * @return a new Tree object with random attributes
     */
    public static Tree generateRandomTree() {

        // Variable to hold the length of the Species enum
         int enumLength;
         int randomIndex;
         Random randomTree = new Random();
        // Array to hold all values of the Species enum
         Species[] speciesValues;
        // Variable to hold the random species made from the enum Species
         Species species;
         int yearOfPlanting;
         double height;
         double growthRate;

        // Get the length of the Species enum
         enumLength = Species.values().length;
        // Generate a random index within the range of the Species enum length
         randomIndex = randomTree.nextInt(enumLength);
        // Get all values of the Species enum
         speciesValues = Species.values();
        // Pick a random species from the Species enum using the random index
         species = speciesValues[randomIndex];

         yearOfPlanting = randomTree.nextInt(MAX_YEAR - MIN_YEAR + 1) + MIN_YEAR;

         height = randomTree.nextDouble() * (MAX_HEIGHT - MIN_HEIGHT) + MIN_HEIGHT;

         growthRate = randomTree.nextDouble() * (MAX_GROWTH_RATE - MIN_GROWTH_RATE) + MIN_GROWTH_RATE;

        // Create and return a new Tree object with the generated random properties
        return new Tree(species, yearOfPlanting, height, growthRate);

    } // end generateRandomTree


    /**
     * Returns a string representation of the Tree object.
     *
     * @return a string containing the tree's species, year of planting, height, and growth rate
     */

    public String toString() {

        return String.format("%-10s %-10d %-10.2f%-10s", species, yearOfPlanting, heightOfTree, String.format("%.1f%%",
                growthRatePerYear));

    } // end of toString

} // end of Tree class
