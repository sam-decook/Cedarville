#include <p18cxxx.h>
#include "my_xlcd.h"
#include <delays.h>
#include <stdlib.h>

#pragma config FOSC = INTIO67, PLLCFG = OFF, PRICLKEN = ON,FCMEN = ON, PWRTEN = ON
#pragma config BOREN = SBORDIS, BORV = 250, WDTEN = OFF, WDTPS = 4096, PBADEN = OFF
#pragma config HFOFST = OFF, MCLRE = EXTMCLR, STVREN = ON, LVP = OFF, DEBUG = ON

void main(void) {	
    unsigned long result;
    unsigned char ones, tenths, hundredths, thousandths;

    //configure the Oscillator for 1Mhz internal oscillator operation
    OSCCON2 = 0x00; //no 4X PLL
    OSCCON = 0x30;  // 1MHZ

    // Set up Port A for analog input
    TRISA = 0x01;
    ANSELA = 0x01;

    // Set up ADC control registers
    ADCON0 = 0b00000001;
    ADCON1 = 0b00000000;
    ADCON2 = 0b10110110;

    // Set up Port C for digital output
    TRISC = 0x00;
    ANSELC = 0x00;

    // Set up the LCD
    OpenXLCD(FOUR_BIT & LINES_5X7);
    WriteCmdXLCD(SHIFT_DISP_LEFT);

    // Display static text
    SetDDRamAddr(0x00);
    putrsXLCD("Voltage is");
    SetDDRamAddr(0x40);
    putrsXLCD(" .     volts");

    while (1) {
        ADCON0bits.GO = 1; //start ADC
        while (ADCON0bits.GO); //let it finish

        // Read result
        result = ADRES; // 5 V / 1024 = 0.00488281
        result = result * 4882810 + 5; //extra zero for correction

        // Convert to characters
        ones =        (result/100000000)+48;
        tenths =      ((result/10000000)%10)+48;
        hundredths =   (((result/1000000)%100)%10)+48;
        thousandths = ((((result/100000)%1000)%100)%10)+48;

        // Display to screen
        SetDDRamAddr(0x40);
        putcXLCD(ones);
        SetDDRamAddr(0x42); //skipping decimal point
        putcXLCD(tenths);
        SetDDRamAddr(0x43);
        putcXLCD(hundredths);
        SetDDRamAddr(0x44);
        putcXLCD(thousandths);
    }
}
