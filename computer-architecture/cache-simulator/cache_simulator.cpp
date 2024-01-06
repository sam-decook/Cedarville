/*
Your project should fulfill the following functions:
a. Allow user to input any requested memory address
b. The cache simulator will use the input address and try to find it in cache.
c. If the data requested is in the cache, output “Cache Hit” and output the 
   requested data along with it; if the data requested is not in the cache, the
   project should output “Cache Miss”, and also bring in the data from memory
   to the corresponding cache location.
d. No matter you will have GUI or not, the small cache should be displayed and
   updated as each time the user input memory address to request data.  The
   display of the small memory is optional.
e. Please submit your project through moodle link and provide with detailed 
   instructions on how to test your project. (Readme.txt file).
 */

/*
  0  000000  92    16  010000  FB    32  100000  0A    48  110000  85
  1  000001  70    17  010001  44    33  100001  F1    49  110001  13
  2  000010  8C    18  010010  DD    34  100010  4C    50  110010  60
  3  000011  FD    19  010011  F6    35  100011  45    51  110011  C5
  4  000100  B9    20  010100  A6    36  100100  63    52  110100  56
  5  000101  E2    21  010101  43    37  100101  2C    53  110101  F2
  6  000110  40    22  010110  11    38  100110  40    54  110110  89
  7  000111  C2    23  010111  17    39  100111  98    55  110111  9E
  8  001000  0D    24  011000  98    40  101000  91    56  111000  06
  9  001001  9A    25  011001  88    41  101001  65    57  111001  E2
 10  001010  D1    26  011010  08    42  101010  0E    58  111010  0B
 11  001011  F8    27  011011  6A    43  101011  76    59  111011  A2
 12  001100  43    28  011100  6D    44  101100  EE    60  111100  B2
 13  001101  7E    29  011101  B8    45  101101  5D    61  111101  41
 14  001110  B7    30  011110  BC    46  101110  18    62  111110  B1
 15  001111  75    31  011111  12    47  101111  29    63  111111  7B
 */

#include <iostream>
#include <string>

using namespace std;

//Contains {address, data} pairs
static string MEMORY[64][2] = {
    {"0", "92"},  {"1", "70"},  {"2", "8C"},  {"3", "FD"},
    {"4", "B9"},  {"5", "E2"},  {"6", "40"},  {"7", "C2"},
    {"8", "0D"},  {"9", "9A"},  {"10", "D1"}, {"11", "F8"},
    {"12", "43"}, {"13", "7E"}, {"14", "B7"}, {"15", "75"},
    {"16", "FB"}, {"17", "44"}, {"18", "DD"}, {"19", "F6"},
    {"20", "A6"}, {"21", "43"}, {"22", "11"}, {"23", "17"},
    {"24", "98"}, {"25", "88"}, {"26", "08"}, {"27", "6A"},
    {"28", "6D"}, {"29", "B8"}, {"30", "BC"}, {"31", "12"},
    {"32", "0A"}, {"33", "F1"}, {"34", "4C"}, {"35", "45"},
    {"36", "63"}, {"37", "2C"}, {"38", "40"}, {"39", "98"},
    {"40", "91"}, {"41", "65"}, {"42", "0E"}, {"43", "76"},
    {"44", "EE"}, {"45", "5D"}, {"46", "18"}, {"47", "29"},
    {"48", "85"}, {"49", "13"}, {"50", "60"}, {"51", "C5"},
    {"52", "56"}, {"53", "F2"}, {"54", "89"}, {"55", "9E"},
    {"56", "06"}, {"57", "E2"}, {"58", "0B"}, {"59", "A2"},
    {"60", "B2"}, {"61", "41"}, {"62", "B1"}, {"63", "7B"}};

//Set-associative cache with eight lines
//- Each pair of lines is a set
//- Each line is made of {tag, data} pairs
string CACHE[8][2];

//The line to be replaced next in each set
int nextLineOut[4] = {0, 0, 0, 0};

//Pads the string for displaying it
string pad(string s, int width) {
    string padding = "";
    int amt = width - (int) s.size();
    for (int i = 0; i < amt; i++) {
        padding += " ";
    }
    return padding + s;
}

//Displays each set of the cache on a line
void displayCache() {
    cout << " Set | Tag  Data | Tag  Data" << endl;
    cout << "-----------------------------" << endl;
    for (int i = 0; i < 4; i++) {
        cout << "|  " << i << " | "
             << pad(CACHE[2 * i][0], 3) << "  "
             << pad(CACHE[2 * i][1], 4) << " | "
             << pad(CACHE[2 * i + 1][0], 3) << "  "
             << pad(CACHE[2 * i + 1][1], 4) << "|" << endl;
    }
}

//Updates the cache with the tag and data of the address given
void updateCache(int address) {
    int set = address % 4;
    int line = set * 2 + nextLineOut[set];
    
    //Updating the cache
    CACHE[line][0] = to_string(address / 4); //the tag
    CACHE[line][1] = MEMORY[address][1];
    
    //Switches the line to be replaced. This implements FIFO replacement
    if (nextLineOut[set] == 0) {
        nextLineOut[set]++;
    } else {
        nextLineOut[set]--;
    }
}

int main(int argc, const char* argv[]) {
    cout << "Welcome to Sam DeCook's cache simulator for Computer Architecture" << endl;
    cout << "Enter memory addresses to get data from cache. Enter 'q' to quit" << endl;
    cout << "Use decimal numbers for memory addresses" << endl << endl;
    cout << "-----------------------------" << endl << endl;
    
    //Get user input (memory address) (in decimal)
    string address = "";
    cout << "Request memory address: ";
    cin >> address;
    cout << endl;
    
    while (address.compare("q") != 0) {
        //Look in cache for data
        //- Find the set then check tag of both lines
        bool hit = false;
        int addr = stoi(address);
        int set = addr % 4;
        int line = set * 2;
        string tag = to_string(addr / 4);
        
        if (CACHE[line][0].compare(tag) == 0) {
            hit = true;
        } else if (CACHE[line + 1][0].compare(tag) == 0) {
            hit = true;
            line++;
        }
        
        if (hit) { //output requested data
            cout << "Cache Hit: " << MEMORY[addr][1] << endl << endl;
        } else {   //update the cache to have the data
            cout << "Cache Miss\tUpdating cache..." << endl << endl;
            updateCache(addr);
            displayCache();
        }
        
        cout << "-----------------------------" << endl << endl;
                
        //Get user input (memory address) (in decimal)
        cout << "Request memory address: ";
        cin >> address;
        cout << endl;
    }
    
    cout << "End of program" << endl;
    
    return 0;
}
