#include <p18cxxx.h>
#include "my_xlcd.h"
#include <delays.h>
#include <stdlib.h>

#pragma config FOSC = INTIO67, PLLCFG = OFF, PRICLKEN = ON,FCMEN = ON, PWRTEN = ON
#pragma config BOREN = SBORDIS, BORV = 250, WDTEN = OFF, WDTPS = 4096, PBADEN = OFF
#pragma config HFOFST = OFF, MCLRE = EXTMCLR, STVREN = ON, LVP = OFF, DEBUG = ON

void DelayFor18TCY( void ){
    Nop();
    Nop();
    Nop();
    Nop();
    Nop();
    Nop();
    Nop();
    Nop();
    Nop();
    Nop();
    Nop();
    Nop();
}

void DelayPORXLCD (void){
    Delay1KTCYx(60); // Delay of 15ms
    // Cycles = (TimeDelay * Fosc) / 4
    // Cycles = (15ms * 16MHz) / 4
    // Cycles = 60,000
    return;
}

void DelayXLCD (void){
    Delay1KTCYx(20); // Delay of 5ms
    // Cycles = (TimeDelay * Fosc) / 4
    // Cycles = (5ms * 16MHz) / 4
    // Cycles = 20,000
    return;
}

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
    //ADCON1 = 0b00000000; // For VDD (pre-lab)
    ADCON1 = 0b00001000; // For Vref
    ADCON2 = 0b10110110;
    VREFCON0 = 0b11110000; // Internal 4.096 V

    // Set up Port C for digital output
    TRISC = 0x00;
    ANSELC = 0x00;

    // Set up the LCD
    OpenXLCD(FOUR_BIT & LINES_5X7);
    WriteCmdXLCD(SHIFT_DISP_LEFT);

    while (1) {
        // Display static text
        SetDDRamAddr(0x00);
        putrsXLCD("Voltage is");
        SetDDRamAddr(0x40);
        putrsXLCD(" .     volts");
        
        ADCON0bits.GO = 1; //start ADC
        while (ADCON0bits.GO); //let it finish

        // Read result
        /* Original, using VDD (pre-lab)
        result = ADRES; // 5 V / 1024 = 0.00488281
        result = result * 488281;
        
        // Convert to characters
        // Must do smaller divisions, data type isn't that large
        ones =        (result/100000/1000)+48;
        tenths =      ((result/10000/1000)%10)+48;
        hundredths =  (((result/1000/1000)%100)%10)+48;
        thousandths = ((((result/1000/100)%1000)%100)%10)+48;
        */

        // New, using Vref = 4.096 V
        result = ADRES; // 4.096 V / 1024 = 0.004
        result = result * 4;
        
        // Convert to characters
        ones =        (result/1000)+48;
        tenths =      ((result/100)%10)+48;
        hundredths =  (((result/10)%100)%10)+48;
        thousandths = (((result%1000)%100)%10)+48;
        //
        
        // Display to screen
        SetDDRamAddr(0x40);
        putcXLCD(ones);
        SetDDRamAddr(0x42); //skipping decimal point
        putcXLCD(tenths);
        SetDDRamAddr(0x43);
        putcXLCD(hundredths);
        SetDDRamAddr(0x44);
        putcXLCD(thousandths);
        
        // Wait to make update more readable (less flickering)
        Delay10KTCYx(10); // ~0.4 s
    }
}
