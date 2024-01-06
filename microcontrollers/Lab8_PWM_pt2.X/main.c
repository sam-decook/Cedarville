/* LAB 8: PWM Part 2, Music
 * Part 1 of this lab plays the song, "He's got the Whole World in His Hands"
 * Part 2 of this lab plays a note based on the key pressed on a 3x4 keypad
 */

#include <p18cxxx.h>
#include "my_xlcd.h"
#include <delays.h>
#include <stdlib.h>

#pragma config FOSC = INTIO67, PLLCFG = OFF, PRICLKEN = ON,FCMEN = ON, PWRTEN = ON
#pragma config BOREN = SBORDIS, BORV = 250, WDTEN = OFF, WDTPS = 4096, PBADEN = OFF
#pragma config HFOFST = OFF, MCLRE = EXTMCLR, STVREN = ON, LVP = OFF, DEBUG = ON

#define B  0xFC //B3
// All are 4ths. C4, D4, etc.
#define C  0xEE
#define Cs 0xE0
#define D  0xD4
#define E  0xBD
#define F  0xB2
#define Fs 0xA8
#define G  0x9E
#define A  0x8D

/*
rom unsigned char wholeWorld[39][7] = {"He's", "got", "the", "whole", "world", 
        "world", "in", "his", "hands.", "hands.", "He's", "got", "the", "whole",
        "world", "world", "in", "his", "hands.", "", "He's", "got", "the", 
        "whole", "world", "world", "in", "his", "hands.", "hands.", "He's", 
        "got", "the", "whole", "world", "in", "his", "hands.", "hands"};

unsigned char notes[39] = {G,G,E,G,E,C,E,G,G,G,G,G,G,F,F,D,F,A,A,0x00,G,
        G,E,G,E,C,E,G,G,G,G,G,E,G,G,F,D,C,C};

unsigned char dur[39] = {1,1,1,4,1,3,1,2,1,1,1,1,1,4,1,3,1,2,1,1,1,1,1,4,1,3,1,
        2,1,1,1,1,1,2,2,1,3,4,1};
*/

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

unsigned char getKeyPad() {
    PORTA|=0xFF;

	// First row
    PORTAbits.RA0=0;            //set RA0 low to scan the first row
        if(!PORTAbits.RA4) {    //  check key 1
            Delay1KTCYx(3);     //  delay for debounce
            if(!PORTAbits.RA4)
                return 0x1;		//  return key 1 code
        }

        if(!PORTAbits.RA5) {    //  check key 2
            Delay1KTCYx(3);     //  delay for debounce
            if(!PORTAbits.RA5)
                return 0x2;		//  return key2 code
        }

        if(!PORTAbits.RA6) {    //  check key 3
            Delay1KTCYx(3);     //  delay for debounce
            if(!PORTAbits.RA6)
                return 0x3; 	//  return key 3 code
        }
    PORTAbits.RA0=1;

    // Second row
    PORTAbits.RA1=0;            //set RA0 low to scan the first row
        if(!PORTAbits.RA4) {    //  check key 1
            Delay1KTCYx(3);     //  delay for debounce
            if(!PORTAbits.RA4)
                return 0x4;		//  return key 4 code
        }

        if(!PORTAbits.RA5) {    //  check key 2
            Delay1KTCYx(3);     //  delay for debounce
            if(!PORTAbits.RA5)
                return 0x5;		//  return key5 code
        }

        if(!PORTAbits.RA6) {    //  check key 3
            Delay1KTCYx(3);     //  delay for debounce
            if(!PORTAbits.RA6)
                return 0x6; 	//  return key 6 code
    }
    PORTAbits.RA1=1;

    // Second row
    PORTAbits.RA2=0;            //set RA0 low to scan the first row
        if(!PORTAbits.RA4) {    //  check key 1
            Delay1KTCYx(3);     //  delay for debounce
            if(!PORTAbits.RA4)
                return 0x7;		//  return key 7 code
        }

        if(!PORTAbits.RA5) {    //  check key 2
            Delay1KTCYx(3);     //  delay for debounce
            if(!PORTAbits.RA5)
                return 0x8;		//  return key8 code
        }

        if(!PORTAbits.RA6) {    //  check key 3
            Delay1KTCYx(3);     //  delay for debounce
            if(!PORTAbits.RA6)
                return 0x0; 	//  return key 0 code
    }
    PORTAbits.RA2=1;
}

void playNote(unsigned char note) {
    PR2 = note;
    CCPR4L = (2*(PR2+1)) >> 2;
    CCP4CON = CCP4CON | ((2*(PR2+1)) << 4);
}

void main(void) {
    //unsigned char i;
    unsigned char key=0;
	
    // Setup uC oscillator to 1MHz
    OSCCON2 = 0x00; //no 4X PLL
    OSCCON = 0x30;  //1MHZ
    
    // Setup Port D for digital output (piezo)
    TRISD = 0x00;
    ANSELD = 0x00;
    // Setup Port C for digital output (LCD)
    TRISC = 0x00;
    ANSELC = 0x00;
    // Setup Port A for digital input (keypad)
    TRISA = 0xF0;
    ANSELA = 0x00;
    
    // configure external LCD
	OpenXLCD( FOUR_BIT & LINES_5X7 );
	WriteCmdXLCD( SHIFT_DISP_LEFT ); 
	while( BusyXLCD() );
	WriteCmdXLCD(0x0C); // Turn blink off
    
    //PWM setup
    CCP4CON = 0x0C;
    T2CON = 0x05;
    
    while (1) {
        /*------------ Part 1: Music code ------------/
        SetDDRamAddr(0x00);
        putrsXLCD("He's got the Wh>");
        
        for (i=0; i<39; i++) {
            // Play note
            playNote(notes[i]);
            
            // Display text
            SetDDRamAddr(0x40);
            //lyric = wholeWorld[i];
            putrsXLCD("                "); //clear screen
            SetDDRamAddr(0x40);
            putrsXLCD(wholeWorld[i]);
            
            Delay10KTCYx(7 * dur[i]); //adjust to find correct tempo
        }
        
        Delay10KTCYx(25); //1 sec delay between repeats
        */
        
        /*------------ Part 2: Musical Keypad ------------*/
        switch (key) {
            case 0x1:
                playNote(B);
                SetDDRamAddr(0x00);
                putrsXLCD("Note: B3 ");
                break;
            case 0x2:
                playNote(C);
                SetDDRamAddr(0x00);
                putrsXLCD("Note: C4 ");
                break;
            case 0x3:
                playNote(Cs);
                SetDDRamAddr(0x00);
                putrsXLCD("Note: C#4");
                break;
            case 0x4:
                playNote(D);
                SetDDRamAddr(0x00);
                putrsXLCD("Note: D4 ");
                break;
            case 0x5:
                playNote(E);
                SetDDRamAddr(0x00);
                putrsXLCD("Note: E4");
                break;
            case 0x6:
                playNote(F);
                SetDDRamAddr(0x00);
                putrsXLCD("Note: F4 ");
                break;
            case 0x7:
                playNote(Fs);
                SetDDRamAddr(0x00);
                putrsXLCD("Note: F#4");
                break;
            case 0x8:
                playNote(G);
                SetDDRamAddr(0x00);
                putrsXLCD("Note: G4 ");
                break;
            case 0x0:
                playNote(A);
                SetDDRamAddr(0x00);
                putrsXLCD("Note: B3 ");
                break;
            default:
                playNote(0);
                Delay10KTCYx(25);
                SetDDRamAddr(0x00);
                putrsXLCD("         ");
        }
    }
}