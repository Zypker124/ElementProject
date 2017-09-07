package other_programs;
//Imports needed;
//ArrayList is used for the elements ArrayList, which is an array of ElementEntrys.
//Scanner is needed to obtain user imput.
import java.util.ArrayList;
import java.util.Scanner;
// Requires the Prompt class
// OpenFile class is used
/**
 * ElementSorter.java
 * Obtains user input, then sorts all the elements by a certain component through a certain type of selection.
 * Each component will use a different type of sorting to order the elements.
 * This program is repeated until the user wishes to leave the program.
 *
 * @author Angus Jyu
 * @version 11-22-16
 * */
public class ElementSorter{
    // fields    
    private ArrayList<ElementEntry> elements;        // Array of elements
    private static ArrayList<ElementEntry> tempForMerge; //ArrayList that will later be used for merge sorting
    
    /**
     * Constructor of ElementSorter.java
     * Initializes the ArrayList elements
     * */
    public ElementSorter(){
        elements=new ArrayList<ElementEntry>();
    }
    
    /**
     * Main method of ElementSorter.java
     * Calls the run method
     * @param args    a String array that can be passed through the executer
     * */
    public static void main(String [] args){
        ElementSorter ER = new ElementSorter();
        ER.run();
    }
    
    /**
     * The run method of ElementSorter.java
     * The "main hub" where everything is combined together,
     * calling different methods and passing in methods when necessary
     * */
    public void run(){
        fillElementsContents();
        printIntroduction();
        int input = displayPrompt();
        runProgram(input);
        System.out.println();
        System.out.println("Thank you for using ElementSorter on the Periodic Table of Elements.");
        System.exit(35);
        
    }
    
    /**
     * The fillElementsContents method
     * Scans each line (each line of elements.txt has an element and its properties),
     * and compiles each line into an ElementEntry, adding it to the elements ArrayList
     * */
    private void fillElementsContents(){
        Scanner scan = OpenFile.openToRead("elements.txt");
        while(scan.hasNext()){
            String line = scan.nextLine();
            int elementNum = Integer.parseInt(line.substring(0,3).trim());
            String symbol = line.substring(11,14).trim();
            String name = line.substring(21,36).trim();
            double atomicMass = Double.parseDouble(line.substring(40,line.length()).trim());
            elements.add(new ElementEntry(name, elementNum, symbol, atomicMass));
        }
    }
    
    /**
     * The runProgram method
     * Runs the "simulation" part of the game; the interactive part where it calls on other methods to
     * sort an element by a certain property using a certain sorting process. It also
     * obtains user input to decide which condition is called for and if the user
     * wishes to leave the program.
     * @param input   the input from the user regarding what they would like to do
     * */
    public void runProgram(int input){
        while(input!=5){
            if(input==1){
                bubbleSort();
                printElements();
            }else if(input==2){
                selectionSort();
                printElements();
            }else if(input==3){
                insertionSort();
                printElements();
            }else if(input==4){
                int arrLength = elements.size();
                tempForMerge = new ArrayList<ElementEntry>(arrLength);
                recursiveSort(elements, 0, arrLength-1);
                //printElements();
                findElement();
            }
            input = displayPrompt();
        }
    }
    
    
    /**
     *    Swaps two ElementEntry objects in array arr
     *    @param arr        array of ElementEntry objects
     *    @param x        index of first object to swap
     *    @param y        index of second object to swap
     */
    private void swap(ArrayList<ElementEntry> arr, int x, int y) {
        ElementEntry temp = arr.get(x);
        arr.set(x,arr.get(y));
        arr.set(y, temp);
    }
    
    /**
     * Sorts the elements by name using bubble sorting.
     * */
    private void bubbleSort(){
        for(int end = elements.size() - 1; end > 0; end--){
            for(int a = 0; a < end; a++){
                int result = elements.get(a).compareToName(elements.get(a+1).getName());
                if(result > 0){
                    swap(elements, a, a+1);
                }
            }
        }
    }
    
    /**
     * Sorts the elements by element number using selection sorting.
     * */
    private void selectionSort(){
        for(int end = elements.size(); end > 1; end--){
            int maximum = 0;
            for(int ind = 1; ind < end; ind++){
                if(elements.get(ind).compareToElementNum(elements.get(maximum).getElementNum())>0){
                    maximum=ind;
                }
            }
            
            swap(elements,maximum,end-1);
        }
    }
    
    /**
     * Sorts the elements by atomic mass using insertion sorting.
     * */
    private void insertionSort(){
        for(int index = 1; index < elements.size(); index++){
            ElementEntry arrTemp = new ElementEntry(elements.get(index).getName(), elements.get(index).getElementNum(),
                    elements.get(index).getSymbol(), elements.get(index).getAtomicMass());
            
            int i = index;
            while(i > 0 && arrTemp.compareToAtomicMass(elements.get(i-1).getAtomicMass())<0.0 ){
                elements.set(i, elements.get(i-1));
                i--;
            }
            
            elements.set(i, arrTemp);
        }
    }
    
    /**
     * Sorts the elements by symbol (alphabetically) using merge / recursive sorting.
     * @param elements   the ArrayList with the IndexEntry's
     * @param from       the index the user wants recursiveSort to start "from"
     * @param to         the index the user wants recursiveSort to start "to"
     * */
    public static void recursiveSort(ArrayList<ElementEntry> elements, int from, int to){
        if( to-from < 2){
            if( to > from && elements.get(to).compareToSymbol(elements.get(from).getSymbol()) < 0 ){
                ElementEntry arrTemp = elements.get(to);
                elements.set(to,elements.get(from));
                elements.set(from, arrTemp);
            }
        }else{
            int middle = (from + to)/2;
            recursiveSort(elements, from, middle);
            recursiveSort(elements, middle+1, to);
            merge(elements, from, middle, to);
        }
    }
    
    /**
     * Merges the array components back together for merge sorting; combines separated
     * elements back together
     * */
    public static void merge(ArrayList<ElementEntry> elements, int from, int middle, int to){
        Integer i = new Integer(from);
        Integer j = new Integer(middle+1);
        Integer k = new Integer(from);
        
        while(i<= middle && j <= to){
            if(elements.get(i).compareToSymbol(elements.get(j).getSymbol()) < 0){
                tempForMerge.add(k, elements.get(i));
                i++;
            }else{
                tempForMerge.add(k, elements.get(j));
                j++;
            }
            k++;
        }
        
        while(i <= middle){
            tempForMerge.add(k, elements.get(i));
            i++;
            k++;
        }
        
        while(j <= to){
            tempForMerge.add(k, elements.get(j));
            j++;
            k++;
        }
        
        for(k = from; k <= to; k++){
            elements.set(k, tempForMerge.get(k));
        }
    }
    /**
     * Obtains user input for which symbol he/she would like to check.
     * Searches through the array using binary search to try and find the symbol
     * after the array has been sorted using merge sorting.
     * Keeps on repeating the above steps until the user wishes to exit.
     * */
    public void findElement(){
        BinarySearch BS = new BinarySearch();
        System.out.println();
        System.out.println("-------------------------------------------------------------------------");
        System.out.println();
        String input = Prompt.getString("Please enter an Atomic Symbol to search for (-1 to exit)");
        int index2=0;
        int finalIndex=-1;
        System.out.println();
        while(!input.equals("-1")){
            int index3 = BS.binarySearch(elements, input);
            if(index3!=-1){
                System.out.println("The binary search took " + BS.getNumOfSearches() + " compares to find this element.");
                System.out.println();
                System.out.println("The element is:");
                System.out.println(elements.get(index3));
            }else{
                System.out.println("The binary search took " + BS.getNumOfSearches() + " to determine that this element does not exist.");
                System.out.println();
            }
            System.out.println();
            System.out.println("-------------------------------------------------------------------------");
            System.out.println();
            input = Prompt.getString("Please enter an Atomic Symbol to search for (-1 to exit)");
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     *    Display the user prompt
     */
    public int displayPrompt() {
        System.out.println("\n1: Display Elements sorted by name");
        System.out.println("2: Display Elements sorted by number");
        System.out.println("3: Display Elements sorted by atomic mass");
        System.out.println("4: Search for an element by symbol");
        System.out.println("5: Exit\n");
        return Prompt.getInt("Please Enter 1 through 5, indicating your "
                                    + "choice from the menu above: ", 1, 5);
    }
    
    /**
     *    Print the element array
     */
    public void printElements() {
        System.out.println();
        System.out.println("+------------------+");
        System.out.println("| List of Elements |");
        System.out.println("+------------------+-------------------------------------+");
        System.out.println("|                                                        |");
        System.out.println("|   #          Element            Symbol    Atomic Mass  |");
        System.out.println("|                                                        |");
        int count = 1;
        for (ElementEntry e : elements) {
            System.out.println("|   " + e + "       |");
            if (count % 5 == 0)
                System.out.println("|                                                        |");
            count++;
        }
        System.out.println("+--------------------------------------------------------+");
    }
    
    /**
     *    Introduction
     */
    public void printIntroduction() {
        System.out.println();
        System.out.println("    ______ __                               __  _____               __             ");
        System.out.println("   / ____// /___   ____ ___   ___   ____   / /_/ ___/ _" +
                            "___   _____ / /_ ___   _____");
        System.out.println("  / __/  / // _ \\ / __ `__ \\ / _ \\ / __ \\ / __/\\__ " +
                            "\\ / __ \\ / ___// __// _ \\ / ___/");
        System.out.println(" / /___ / //  __// / / / / //  __// / / // /_ ___/ // /_/" +
                            " // /   / /_ /  __// /    ");
        System.out.println("/_____//_/ \\___//_/ /_/ /_/ \\___//_/ /_/ \\__//____/ " +
                            "\\____//_/    \\__/ \\___//_/     ");
        System.out.print("\nWelcome to ElementSorter, a sorting and searching routine");
        System.out.println(" for the\nperiodic table of elements.");
        System.out.println("\nLet's begin.\n");
    }
    
}