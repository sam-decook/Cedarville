package mycompany.assembler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class assembler {
    private static final String[][] mem_instr = {{"AND", "0"}, {"ADD", "1"},
        {"LDA", "2"}, {"STA", "3"}, {"BUN", "4"}, {"BSA", "5"}, {"ISZ", "6"}};
    
    private static final String[][] instr = {{"CLA", "7800"}, {"CLE", "7400"},
        {"CMA", "7200"}, {"CME", "7100"}, {"CIR", "7080"}, {"CIL", "7040"},
        {"INC", "7020"}, {"SPA", "7010"}, {"SNA", "7008"}, {"SZA", "7004"}, 
        {"SZE", "7002"}, {"HLT", "7001"}, {"INP", "F800"}, {"OUT", "F400"}, 
        {"SKI", "F200"}, {"SKO", "F100"}, {"ION", "F080"}, {"IOF", "F040"}};
    
    public static void main(String[] args) 
            throws FileNotFoundException, IOException {
        File file = new File("testFile.asm");
        
        /*----------------- First pass, generate symbol table ----------------*/
        String[][] symbols = new String[100][2];
        Scanner sc = new Scanner(file);
        
        String line;
        String lineCtr = "0";
        int ind = 0;
        boolean end = false;
        
        while (sc.hasNextLine() && !end) {
            line = sc.nextLine();
            
            if (line.charAt(3) == ',') {
                symbols[ind][0] = line.substring(0, 3);  //label
                symbols[ind][1] = lineCtr;               //memory location
                ind++;
            }
            
            lineCtr = increment(lineCtr);
            
            if ("ORG".equals(line.substring(5, 8))) {   
                //Set lineCtr after incrementing, otherwise it'll be one ahead
                lineCtr = line.substring(9, 12);
            }
            if ("END".equals(line.substring(5, 8))) {   
                end = true;
            }
        }
        
        /*---------------- Second pass, generate machine code ----------------*/
        sc = new Scanner(file);
        String binary = "";
        end = false;
        
        while (sc.hasNextLine() && !end) {
            line = sc.nextLine();
            
            //First check for the pseudo instructions, if not, normal
            switch (line.substring(5, 8)) {
                case "ORG":             //set line counter
                    lineCtr = line.substring(9, 12);
                    break;
                    
                case "DEC":             //set binary to number in hex
                    ind = adjustEndIndex(line);
                    int x = Integer.parseInt(line.substring(9, ind));
                    binary += formBinary(lineCtr, Integer.toHexString(x));
                    break;
                    
                case "HEX":             //set binary to number
                    ind = adjustEndIndex(line);
                    binary += formBinary(lineCtr, line.substring(9, ind));
                    break;
                    
                case "END":             //end of program, stop
                    end = true;
                    break;
                   
                default:                //normal instruction
                    binary += formBinary(lineCtr, parseLine(symbols, line));
                    break;
            }
            
            //Again, don't increment if the origin was just set
            if (!"ORG".equals(line.substring(5, 8))) {
                lineCtr = increment(lineCtr);
            }
        }
        
        /*---------- Print & save the label table and machine code -----------*/
        FileWriter sym = new FileWriter("symbols.sym", true);
        FileWriter bin = new FileWriter("machine.bin", true);
        
        System.out.println("Symbol table:\n-------------");
        int i = 0;
        while (symbols[i][0] != null) {
            line = symbols[i][0] + ": " + symbols[i][1] + "\n";
            sym.write(line);
            System.out.print(line);
            i++;
        }
        sym.close();
        
        System.out.println("\nMachine code:\n-------------");
        bin.write(binary);
        bin.close();
        System.out.println(binary);
    }
    
    //Adjusts the end index to avoid out-of-bounds and parsing errors
    private static int adjustEndIndex(String line) {
        //It's possible you have HEX 8 with nothing after it
        //You can also have DEC 8 with a comment after it (can't parse "8  ")
        
        int ind = 12; //where the substring call ends (non-inclusive)
        if (line.length() < 12) { //prevents out-of-bounds errors
            ind = line.length();
            
        } else if (line.charAt(11) == ' ') { //remove trailing ' 's
            ind--;
            if (line.charAt(10) == ' ') {
                ind--;
            }
        }
        return ind;
    }
    
    //Forms the binary after formatting the instruction to match the spec
    private static String formBinary(String lineCtr, String instr) {
        //For DEC and HEX, you will also need to pad to 4 digits
        int length = instr.length();
        for (int i = 0; i < 4 - length; i++) {
            instr = "0" + instr;
        }
        //For DEC, if it is negative, it will be 8 digits long (FFFFFF6 = -10)
        if (length == 8) {
            instr = instr.substring(4);
        }
        
        return lineCtr + ":" + instr.toUpperCase() + "\n";
    }
    
    //Parses the line of assembly code and returns the machine code equivalent
    private static String parseLine(String[][] labels, String line) {
        String opcode = line.substring(5, 8);
        
        //First check if it is a memory instruction since there are only 7
        for (int i = 0; i < mem_instr.length; i++) {
            if (opcode.equals(mem_instr[i][0])) {
                String bin = mem_instr[i][1]; //just the 1-digit opcode
                
                //Check if it is indirect
                if (line.charAt(13) == 'I') {
                    //For indirect instructions, the MSB increases by 8
                    int x = Integer.parseInt(bin) + 8;
                    bin = Integer.toHexString(x).toUpperCase();
                }
                
                //Get the label's 3-digit address and append it to the binary
                bin += getAddress(labels, line.substring(9, 12));
                return bin;
            }            
        }
        
        //Then check if it is a non-memory instruction
        for (int i = 0; i < instr.length; i++) {
            if (opcode.equals(instr[i][0])) {   //assembly
                return instr[i][1];             //machine
            }
        }
        
        return "-1"; //Error
    }
    
    //Searches the labels table (from first pass) and returns the memory address
    private static String getAddress(String[][] labels, String label) {
        for (int i = 0; i < labels.length; i++) {
            if (label.equals(labels[i][0])) {   //label
                return labels[i][1];            //address
            }
        }
        return "-1";
    }
    
    //Returns the incremented hex value
    private static String increment(String hex) {
        int x = Integer.parseInt(hex, 16);
        x++;
        String val = (Integer.toHexString(x)).toUpperCase();
        
        int amt = 3 - val.length(); //amount of 0's added to the front
        for (int i = 0; i < amt; i++) {
            val = "0" + val;
        }
        
        return val;
    }
}
