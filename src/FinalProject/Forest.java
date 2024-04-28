package FinalProject;

import java.io.*;
import java.util.ArrayList;

/**
 * Represents a forest consisting of multiple trees.
 * Forests can be manipulated by adding, cutting, growing, and reaping trees.
 * Forests can also be saved to and loaded from files.
 *
 */
public class Forest implements Serializable  {
    private String name; // Name of the forest
    ArrayList<Tree> trees; // List of trees in the forest

    /**
     * Constructs a forest with the given name and list of trees.
     *
     * @param forestName the name of the forest
     * @param trees      the list of trees in the forest
     */
    public Forest(String forestName, ArrayList<Tree> trees) {
        this.name = forestName;
        this.trees = trees;

    } // end on of constructor

    /**
     * Generates a string representation of the forest, including the forest's name,
     * the list of trees with their indexs, and the average height of the trees. Professor showed code during a lecture
     * once on accident, so I used his toString. (sorry)
     *
     * @return a string representation of the forest
     */
    public String toString() {

        double totalHeight = 0.0;
        int index;
        double averageHeight;

        System.out.println("\nForest name: " + name);


        for (index = 0; index < trees.size(); index++) {
            System.out.printf("    %2d %s\n", index, trees.get(index));
            Tree currentTree = trees.get(index);
            totalHeight += currentTree.getHeight();
        }

        averageHeight = totalHeight / trees.size();

        // Generates a string representation of the forest
        return String.format("There are %d trees, with an average height of %.2f", trees.size(), averageHeight);

    } // end of toString

    /**
     * Adds a randomly generated tree to the forest.
     */
    public void add(){

    trees.add(Tree.generateRandomTree());

    } // end of add method

    /**
     * Removes a tree from the forest based on its index.
     *
     * @param treeIndexNumber the index of the tree to be removed
     */
    public void cut(int treeIndexNumber)  {

        if (treeIndexNumber >= 0 && treeIndexNumber < trees.size()) {
            trees.remove(treeIndexNumber);
        } else {
            // checks to see if the index typed is within range of trees array size
            System.out.println("Tree number " + treeIndexNumber + " does not exist.");
        }

    } // end of cut method

    /**
     * Simulates one year of growth for all trees in the forest.
     */
    public void grow() {

        for (Tree tree : trees) {
            tree.growPerYear();
        }

    } // end of grow method

    /**
     * Replaces trees in the forest that exceed a specified maximum height with new randomly generated trees.
     *
     * @param maxHeight the maximum height for a tree to be replaced
     */
    public void reap(double maxHeight) {

        int indexOfTreeArrayList;
        Tree tree;
        Tree newTree;
        // Iterate through each tree in the forest
        for (indexOfTreeArrayList = 0; indexOfTreeArrayList < trees.size(); indexOfTreeArrayList++) {
             tree = trees.get(indexOfTreeArrayList);
            // Check if the tree's height exceeds the specified maximum height
            if (tree.getHeight() > maxHeight) {
                System.out.println("Reaping the tall tree " + tree);
                 newTree = Tree.generateRandomTree();
                trees.set(indexOfTreeArrayList, newTree);
                System.out.println("Replaced the new tree " + newTree);

            }
        }

    } // end of reap

    /**
     * Reads a forest from a CSV file and returns the forest object. Classic example from FileTextEdit.java
     *
     *
     * @param filename the name of the CSV file containing the forest data
     * @return the forest object read from the CSV file
     */
    public static Forest readForestFromCSV(String filename) {

        // ArrayList to store Tree objects that are broken down from the CSV file
        ArrayList<Tree> trees = new ArrayList<>();
        // Variables to store attributes of each tree
        int yearOfPlanting;
        double height;
        double growthRate;
        String speciesName; // The name of the tree species as read from the CSV file as a String
        Tree.Species enumSpeciesName; // Represents the tree species as an enum constant defined in the Tree class
        String[] treeAttributes;  // Array to store attributes of a single tree read from a line in the CSV file
        // Variables for file reading
        Tree tree;
        String oneLine;
        BufferedReader fromBufferedReader = null;

        try {
            // Create a BufferedReader to read the CSV file
            fromBufferedReader = new BufferedReader(new FileReader(filename + ".csv"));

            // Read each line of the CSV file until the end is reached
            while ((oneLine = fromBufferedReader.readLine()) != null) {

                treeAttributes = oneLine.split(",");
                // Extract tree attributes from the array
                speciesName = treeAttributes[0]; // treeAttributes is a String array containing data from the parsed CSV row
                enumSpeciesName = Tree.Species.valueOf(speciesName); // Parse the species name from the String to a Species enum value
                yearOfPlanting = Integer.parseInt(treeAttributes[1]);// String to an Integer object (wrapped int)
                height = Double.parseDouble(treeAttributes[2]); // height from a String to a Double object (wrapped double)
                growthRate = Double.parseDouble(treeAttributes[3]); // growth rate from a String to a Double object (wrapped double)

                // Create a Tree object with the extracted attributes
                tree = new Tree(enumSpeciesName, yearOfPlanting, height, growthRate);
                // Add the created Tree object to the ArrayList
                trees.add(tree);
            }
            // Handle file IO exceptions instead of throwing it on the top
        } catch (IOException e) {
            System.out.println("Error reading/opening file: " + filename + ".csv");
            return null;
            // Finally part to make sure the BufferedReader closes
        } finally {
            if (fromBufferedReader != null) {
                try {
                    fromBufferedReader.close();
                    // Finally part to make sure the BufferedReader closes, but catch it just in case it goes wrong
                } catch (IOException e) {
                    System.out.println("Error closing BufferedReader: " + e.getMessage());
                }
            }
        }
        // Create a new Forest object using the ArrayList of trees and return it, after the lines above are done
        return new Forest(filename, trees);

    } // end of method

    /**
     * Saves the forest object to a binary file (serialization) with the specified filename.
     * Classic example of FileAllCarDealer.java
     *
     * @param forest   the forest object to be saved
     * @param filename the name of the file to which the forest will be saved
     */
    public static void save(Forest forest, String filename) {

        ObjectOutputStream toStream = null;

        try {
            // Create an ObjectOutputStream to write the forest object to file db
            toStream = new ObjectOutputStream(new FileOutputStream(filename + ".db"));
            // Write the forest object to the file to save it there
            toStream.writeObject(forest);

            System.out.println("Forest saved successfully");

        } catch (IOException e) {
            // Print an error message if there is an IOException during the saving process, general message outputted
            System.out.println("Error saving forest: " + e.getMessage());

        } finally {
            // Used finally to make sure it closes right
            if (toStream != null) {
                try {
                    toStream.close();
                    // catch just in case it closes wrong
                } catch (IOException e) {
                    System.out.println("Error closing ObjectOutputStream: " + e.getMessage());
                }
            }
        }

    } // end of save method

    /**
     * Loads a forest object from a binary file (serialized) with the specified filename.
     * Classic example of FileAllCarDealer.java
     *
     * @param filename the name of the file from which the forest will be loaded
     * @return the forest object loaded from the binary file
     */
    public static Forest load(String filename) {

        File dbFile;// File object representing the database file, did it just for load method.
        Forest forest;// Variable to hold the loaded Forest object
        ObjectInputStream fromStream = null;// ObjectInputStream for reading the serialized object

        // Create a File object for the database file
        dbFile = new File(filename + ".db");

        // Makes sure the database file exists, if not an error returns
        if (!dbFile.exists()) {
            System.out.println("Error opening/reading " + filename + ".db");
            System.out.println("Old forest retained");
            return null;
        }

        try {
            // Create an ObjectInputStream to read the serialized object from the file
            fromStream = new ObjectInputStream(new FileInputStream(dbFile));
            // Read the serialized object and cast it to a Forest object
            forest = (Forest) fromStream.readObject();
            System.out.println("Forest loaded successfully");
            return forest; // Return the loaded Forest object

            // Print an error message if there is an IOException or ClassNotFoundException during loading
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading forest: " + e.getMessage());
            return null;
            // Finally block to make sure it closes right
        } finally {
            if (fromStream != null) {
                try {
                    fromStream.close();
                    // Catch block to make sure nothing goes wrong while closing
                } catch (IOException e) {
                    System.out.println("Error closing forest file: " + e.getMessage());
                }
            }
        }

    } // end of load method


} // end of Forest class
