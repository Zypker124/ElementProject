package other_programs;
/**
 * ElementEntry.java
 * 
 * Creates an element object and its components, which will eventually be stored
 * in an arrayList called elements in ElementSOrter.java.
 * Contains accessor methods, comparison methods, and a toString() method for various components
 * of the elements.
 * 
 * @author Angus Jyu
 * @version 11-23-16
 * */
public class ElementEntry {
	//FIeld variables
	private String name;
	private int elementNum;
	private String symbol;
	private double atomicMass;
	/**
	 * Constructor of the class; fields are initialized
	 * */
	public ElementEntry(String n, int eN, String s, double aM){
		name=n;
		elementNum=eN;
		symbol=s;
		atomicMass=aM;
	}
	/**
	 * Accessor methods
	 * */
	public String getName(){return name;}
	public int getElementNum(){return elementNum;}
	public String getSymbol(){return symbol;}
	public double getAtomicMass(){return atomicMass;}
	/**
	 * Comparison methods
	 * */
	public int compareToName(String name2){
		return name.compareTo(name2);
	}
	public int compareToElementNum(int elementNum2){
		return elementNum-elementNum2;
	}
	public int compareToSymbol(String symbol2){
		return symbol.toLowerCase().compareTo(symbol2.toLowerCase());
	}
	public double compareToAtomicMass(double atomicMass2){
		return atomicMass-atomicMass2;
	}
	/**
	 * toString() method that prints out the element and its contents
	 * in a neat, formatted manner
	 * */
	public String toString(){
		String printedLine = "";
		printedLine += ""+elementNum;
		if(elementNum>=1&&elementNum<=9){
			printedLine+="          ";
		}else if(elementNum>=10&&elementNum<=99){
			printedLine+="         ";
		}else{
			printedLine+="        ";
		}
		
		printedLine+=name;
		for(int ind = 0; ind < 19-name.length(); ind++){
			printedLine+=" ";
		}
		
		printedLine+=symbol;
		for(int ind = 0; ind < 10-symbol.length(); ind++){
			printedLine+=" ";
		}
		
		String aMString = atomicMass+"";
		if(aMString.indexOf(".")<aMString.length()-2){
			aMString=aMString.substring(0, aMString.indexOf(".")+3);
		}else{
			if(aMString.indexOf(".")==aMString.length()-2){
				aMString+="0";
			}else{
				aMString+=".00";
			}
		}
		for(int ind = 0; ind<6-aMString.length();ind++){
			printedLine+=" ";
		}
		printedLine+=aMString;
		
		if(printedLine.equals("")){
			printedLine+="            ";
		}
		
		return printedLine;
	}
}
