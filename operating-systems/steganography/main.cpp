/*
 Create a Linux-style command line utility that takes an image and encodes ASCII characters within the image using the least
 significant bit within a pixel’s component values. The application must also be able to extract the ASCII text from an image encoded by the program.

Required command line argument usage:

  steg -e <original image name> <modified image name> [input ASCII text file name]
  steg -d <modified image name> [output ASCII text file name]
 
If input or output text files are not specified (as optional command line arguments), text to be encrypted should be gathered from stdin and decoded text should be sent to stdout.

You may implement additional functionality as long as the required features are complete.

Please provide a README file with your application to describe the features of your application and to thoroughly detail how it ought to be installed/used/etc.
 */

/**
 Summary: This file includes a steganography command line function with the ability to encode
 and decode a png image (though it is only able to decode an image encoded by this function)
 
 Author: Sam DeCook
 Created: April 2022
 
 Copyright Cedarville University, it's Computer Science faculty, and the author
 All rights reserved
 */

#include "lodepng.h"
#include <iostream>
#include <fstream>
#include <string>
#include <bitset>

using namespace std;

static string PROPER_USAGE = "\tsteg -e <original image name> <modified image name> [input ASCII text file name]\n\tsteg -d <modified image name> [output ASCII text file name]";

//Modifies the LSB of a pixel to the bit
void modifyPixel(unsigned char& pixel, char bit) {
    //pixel is bitwise ANDed with 00000001
    bool odd = pixel & 1;
    
    if (odd && (bit == '0')) {
        pixel--; //changes LSB to 0
    }
    if (!odd && (bit == '1')) {
        pixel++; //changes LSB to 1
    }
    //You don't need to modify the pixel for the other cases
}

//Encodes LSB of each pixel with text
void modifyImage(vector<unsigned char>& pixels, string text) {
    size_t length = text.size() * 8; //gets length as an integer
    bitset<16> lengthBin (length); //converts length to binary
    
    //Write amount of bits to be written to first 16 bits
    for (int i = 0; i < 16; i++) {
        modifyPixel(pixels[i], lengthBin[i]);
    }
    
    //First convert text (ASCII) to binary
    string binary = "";
    for (char byte : text) { //transforms each character in 8 bits
        binary += bitset<8>(byte).to_string();
    }
        
    //Encode the text file into the LSBs
    for (int i = 0; i < length; i++) {
        modifyPixel(pixels[i + 16], binary[i]);
    }
}

//Reads LSB of each pixel and returns it as a string
string decodeImage(vector<unsigned char> pixels) {
    //First, get how many bits you will read (in first 2 bytes)
    string numBitsBin = "";
    for (int i = 0; i < 16; i++) {
        if (pixels[i] & 1) { //checks LSB
            numBitsBin += "1";
        } else {
            numBitsBin += "0";
        }
    }
    size_t numBits = stoi(numBitsBin, nullptr, 2); //convert to dec
    
    //Second, read off the encoded data
    string binary = "";
    for (int i = 16; i < numBits + 16; i++) {
        if ((pixels[i] & 1) == 1) {
            binary += "1";
        } else {
            binary += "0";
        }
    }
    
    //Convert binary to ASCII, append to string
    string text = "";
    for (int i = 0; i < numBits; i += 8) { //iterate 8 bits at a time
        bitset<8> ASCII (binary.substr(i, 8));
        text += char(ASCII.to_ulong()); //binary -> int, cast to char
    }
    
    return text;
}

int main(int argc, const char* argv[]) {
    /*------------------------- Parse Input ------------------------*/
    if (argc < 3) { //decode with user input requires 2 params
        cout << "Error: too few parameters. Proper usage:" << endl
             << PROPER_USAGE << endl;
        return 1;
    }
    
    //First check if we are encoding or decoding
    bool encode = strcmp(argv[1], "-e") == 0;
    
    //Get image name(s) and (possibly) text file
    string imgName;
    string modImgName;
    string textFile;
    string text = "";
    bool noTextFile = false;
    
    if (encode) { //steg -e <orig image> <mod image> [in text file]
        if (argc < 4) {
            cout << "Error: too few parameters for encode. "
                 << "Proper usage:" << endl << PROPER_USAGE << endl;
            return 1;
        }
        
        imgName = argv[2];
        modImgName = argv[3];
        
        //Prepare text variable
        if (argc == 5) { //get text from provided file
            textFile = argv[4];
            ifstream data(textFile);
            string line;
            while (!data.eof()) {
                getline(data, line);
                text = text + line + " ";
            }
            data.close();
        } else if (argc == 4) {
            noTextFile = true; //get user input
            cout << "Enter the text you want encoded: ";
            cin >> text;
            cout << endl;
        }
        
    } else { //steg -d <mod image> [out text file]
        if (argc < 3) {
            cout << "Error: too few parameters for decode.c"
                 << "Proper usage:" << endl << PROPER_USAGE << endl;
            return 1;
        }
        
        //I use the variable imgName so that I can call
        //  lodePNG::decode() with it for both encode and decode
        imgName = argv[2];
        
        //Check if they provided a text file
        if (argc == 4) {
            textFile = argv[3]; //it will output to the text file
        } else if (argc == 3) {
            noTextFile = true; //it will output to the terminal
        }
    }
    
    /*---------------------- Encode or Decode ----------------------*/
    //Get raw pixel data from image
    //Code from example code, see LodePNG documentation section 10.1
    vector<unsigned char> pixels;
    unsigned width, height;
    unsigned error = lodepng::decode(pixels, width, height, imgName);

    if (error) {
        cout << "image decoder error " << error << ": "
             << lodepng_error_text(error) << endl;
        return 1;
    }
    
    if (encode) {
        modifyImage(pixels, text);
        //Then encode the raw data into a new file
        lodepng::encode(modImgName, pixels, width, height);
        
    } else {
        text = decodeImage(pixels);
        
        if (noTextFile) { //send decoded text to cout
            cout << "Decoded message: " << text << endl;
        } else {          //send decoded text to specified file
            ofstream modFile(textFile);
            modFile << text;
            modFile.close();
        }
    }
    
    return 0;
}
