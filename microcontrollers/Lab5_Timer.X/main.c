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
    // Define variables
    unsigned int high, low;
    unsigned long result, freq;
    unsigned char ten_thous, thousand, hundred, ten, one;

    //configure the Oscillator for 1Mhz internal oscillator operation
    OSCCON2 = 0x00; //no 4X PLL
    OSCCON = 0x30;  //1MHZ

    // Set up Port A -> RA4
    TRISA = 0x10;
    ANSELA = 0x00;

    // Set up Timer control register
    /*---------------- Part 1 Code ----------------*/
//    T0CON = 0b10101000;

    /*---------------- Part 2 Code ----------------*/
    T0CON = 0b10001000;


    // Set up Port C for digital output (LCD)
    TRISC = 0x00;
    ANSELC = 0x00;

    // Set up the LCD
    OpenXLCD(FOUR_BIT & LINES_5X7);
    WriteCmdXLCD(SHIFT_DISP_LEFT);

    while (1) {
        /*---------------- Part 1 Code ----------------*/
        // Clear Timer 0
//        TMR0H = 0x00;  //set high byte first
//        TMR0L = 0x00;  //set low byte, which updates both and starts counter
//
//        Delay10KTCYx(25); // 1 second delay
//
//        // Read Timer 0
//        low = TMR0L;   //also latches in high byte to TMR0H
//        high = TMR0H;  //we will shift these to correct the value
//
//        // Combine into one variable
//        freq = (high<<8) + low;

        /*---------------- Part 2 Code ----------------*/
        // Wait for a clean rising edge
        while (PORTAbits.RA4);   //catches when it's high
        while (!PORTAbits.RA4);  //waits for the low to end

        // When it goes high (rising edge), start timer
        TMR0H = 0x00;  //set high byte first /*Could we do this before the whiles??*/
        TMR0L = 0x00;  //set low byte, which updates both and starts counter

        // Wait for the next rising edge (one cycle)
        while (PORTAbits.RA4);   //catches when it's high
        while (!PORTAbits.RA4);  //waits for the low to end

        // Read Timer 0
        low = TMR0L;   //also latches in high byte to TMR0H
        high = TMR0H;  //we will shift these to correct the value

        // Combine into one variable
        result = (high<<8) + low;

        // Compute frequency
        freq = 250000/result;


        /*---------------- Common Code ----------------*/
        // Display static text
        SetDDRamAddr(0x00);
        putrsXLCD("Frequency is");
        SetDDRamAddr(0x46); //space for 5 digits
        putrsXLCD("Hz");

        // Isolate digits and convert to ASCII codes
        ten_thous = (freq/10000)+48;
        thousand  = ((freq/1000)%10)+48;
        hundred   = (((freq/100)%100)%10)+48;
        ten       = ((((freq/10)%1000)%100)%10)+48;
        one       = ((((freq%10000)%1000)%100)%10)+48;

        SetDDRamAddr(0x40);
        putcXLCD(ten_thous);
        SetDDRamAddr(0x41);
        putcXLCD(thousand);
        SetDDRamAddr(0x42);
        putcXLCD(hundred);
        SetDDRamAddr(0x43);
        putcXLCD(ten);
        SetDDRamAddr(0x44);
        putcXLCD(one);
        
        // Wait to make update more readable (less flickering)
        Delay10KTCYx(10); // ~0.4 s
    }
}
