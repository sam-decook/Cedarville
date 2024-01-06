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

void main( void ){	
    //unsigned int counter = 0;
    //unsigned char buff[16];
    unsigned int hour = 12;
    unsigned int min = 59;
    unsigned int second = 50;
    unsigned char morning = '0';
    
    //configure the Oscillator for 1Mhz internal oscillator operation
    OSCCON2 = 0x00; //no 4X PLL
    OSCCON = 0x30;  // 1MHZ

    TRISC = 0x00;
    ANSELC = 0x00;
    OpenXLCD(FOUR_BIT & LINES_5X7);
    WriteCmdXLCD(SHIFT_DISP_LEFT);
    
    /* The counter
    SetDDRamAddr(0x00);
    putrsXLCD("The counter\'s");
    SetDDRamAddr(0x40);
    putrsXLCD("value is:");
    
    while (1) {
        itoa(counter, buff);
        SetDDRamAddr(0x4A);
        putsXLCD(buff);
        Delay10KTCYx(20);
        counter++;
    }
     */
    
    SetDDRamAddr(0x40);
    putrsXLCD("Sept. 21st 2022");
    SetDDRamAddr(0x00);
    putrsXLCD("Time   :  :   PM");
    
    while (1) {        
        // Display to LCD
        //Hours
        SetDDRamAddr(0x05);
        putcXLCD(hour/10 + 48);
        SetDDRamAddr(0x06);
        putcXLCD(hour%10 + 48);
        
        //Minutes
        SetDDRamAddr(0x08);
        putcXLCD(min/10 + 48);
        SetDDRamAddr(0x09);
        putcXLCD(min%10 + 48);
        
        //Hours
        SetDDRamAddr(0x0B);
        putcXLCD(second/10 + 48);
        SetDDRamAddr(0x0C);
        putcXLCD(second%10 + 48);
        
        // Increment time
        second++;
        if (second == 60) {
            second = 0;
            min++;
        }
        if (min == 60) {
            min = 0;
            hour++;
        }
        if (hour == 12 && second == 0) {
            SetDDRamAddr(0x0E);
            if (morning == '0') {
                putrsXLCD("AM");
                morning = '1'; //true
            } else {
                putrsXLCD("PM");
                morning = '0'; //false
            }
        }
        if (hour == 13) {
            hour = 1;
        }
        
        Delay10KTCYx(24); //Delay of 1 second
    }
}
