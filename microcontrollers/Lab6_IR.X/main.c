/* Lab 6: IR Receiver
 * 
 * This is the code for lab 6, demonstrating the use of polling and interrupts
 * to decode an IR signal from a TV remote
 */

#include <p18cxxx.h>
#include "my_xlcd.h"
#include <stdlib.h>
#include <delays.h>

#pragma config FOSC=INTIO67, PLLCFG=OFF, PRICLKEN=ON,FCMEN=ON, PWRTEN=OFF
#pragma config BOREN=SBORDIS, BORV=250, WDTEN=OFF, WDTPS=4096, PBADEN=OFF
#pragma config HFOFST=OFF, MCLRE=EXTMCLR, STVREN=ON, LVP=OFF, DEBUG= ON

// Variable declarations
unsigned char device=0, command=0, done=0, value=0, counter=0, i;
unsigned int A=0, B=0, C=0, D=0, E=0, result, temp;
unsigned char buffer[16], text[8];


/*-------- Pre-code for interrupt part --------*/
void high_ISR(void);

#pragma code high_vector = 0x08
void high_interrupt(void) {
    _asm
    goto high_ISR
    _endasm
}

#pragma code
#pragma interrupt high_ISR
void high_ISR(void) {
    if (PORTBbits.RB4 == 0) {		//falling edge, clear and start timer
        TMR0L = 0x00;
    } else {				//rising edge, read value
        value = TMR0L;
        if (value > 140) {		//start bit: reset buffer index
            i = 0;
            done = 0;
        } else {				//info bit: record in buffer
            buffer[i] = value;
            i++;
        }
    }

    if (i > 11) done = 1;		//done if read all 12 bits
    INTCONbits.RBIF = 0;
}
/**/

void DelayFor18TCY(void) {
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

void DelayPORXLCD(void) {
    Delay1KTCYx(60); // Delay of 15ms
    // Cycles = (TimeDelay * Fosc) / 4
    // Cycles = (15ms * 16MHz) / 4
    // Cycles = 60,000
    return;
}

void DelayXLCD(void) {
    Delay1KTCYx(20); // Delay of 5ms
    // Cycles = (TimeDelay * Fosc) / 4
    // Cycles = (5ms * 16MHz) / 4
    // Cycles = 20,000
    return;
}

void main(void) {
	//configure the Oscillator for 1Mhz internal oscillator operation
	OSCCON2 = 0x00; //no 4X PLL
	OSCCON = 0x30;  // 1MHZ
	
	//PORT A - digital input
	TRISA = 0xFF; 
	ANSELA = 0x00;
	
	//PORT B - digital input
	TRISB = 0xFF; 
	ANSELB = 0x00;
	
	//PORT D
	TRISD = 0x00;
	ANSELD = 0x00;
	
	//PORT C - digital outputs
	TRISC = 0x00;
	ANSELC = 0x00;

	// Set up registers for interrupts
	RCONbits.IPEN = 1;      //interrupt enable
	INTCONbits.GIEH = 1;	//enable high priority
	INTCONbits.GIEL = 0;	//disable low priority
	INTCONbits.RBIE = 1;	//enables on port B
	INTCON2bits.RBIP = 1;
	IOCB = 0x10;            //use RB4 pin for interrupt on change

	//set up the Timer 0 for 8 bit operation with Input from internal clock divided by 4
	T0CON = 0xD1;
	
	// configure external LCD
	OpenXLCD( FOUR_BIT & LINES_5X7 );
	WriteCmdXLCD( SHIFT_DISP_LEFT); 
	while( BusyXLCD() );
	WriteCmdXLCD(0x0C); // Turn blink off

	SetDDRamAddr(0x00);
	putrsXLCD("Counter =");
	SetDDRamAddr(0x40);
	putrsXLCD("Dev  Command ");
	
	for (i=0;i<16;i++){
		buffer[i]= 100;//initialize buffer to see if real data is written to the buffer
	}


    while(1) {
        /*---------- Code for interrupt method  ----------*/
        if (done == 1) {	//Translate buffer into binary
            result = 0;
            for (i = 0; i < 12; i++) {
                result = result >> 1;
                if (buffer[i] > 70)
                    result = result | 0x0800;
            }

            // Separate result into device (upper 5) and command (lower 7)
            device = result >> 7;
            command = result & 0x7F;
            done = 0;

            SetDDRamAddr(0x43);
            putcXLCD(device + 48);

            A =    (command / 100) + 48;
            temp = command % 100;
            B =    (temp / 10) + 48;
            C =    (temp % 10) + 48;

            SetDDRamAddr(0x4D);
            putcXLCD(A);
            putcXLCD(B);
            putcXLCD(C);
            
            // Use remote to change LEDs. Volume shifts the light
            switch (command) {
                case 16:            //channel up
                    PORTD = 0xFF;
                    break;
                case 17:            //channel down
                    PORTD = 0x00;
                    break;
                case 19:            //< volume
                    PORTD = (PORTD >> 1) + 0x80;
                    break;
                case 18:            //volume >
                    PORTD = PORTD << 1;
                    break;
                case 96:            //menu 
                    PORTD = 0xAA;
                    break;
                default:
                    PORTD = 0x99;
            }
            
            Delay10KTCYx(5);
        }

        // Display counter
        A =    (counter / 100) + 48;
        temp = counter % 100;
        B =    (temp / 10) + 48;
        C =    (temp % 10) + 48;

        SetDDRamAddr(0x0A);
        putcXLCD(A);
        putcXLCD(B);
        putcXLCD(C);
        counter++;
        Delay10KTCYx(5);
        /**/

        /*---------- Code for polling method ----------/        
        // Wait for start bit to be transmitted
        while (value < 150) {
            // Start timer on falling edge, record value on rising
            while (PORTAbits.RA4);
            TMR0L = 0x00;
            while (!PORTAbits.RA4);
            value = TMR0L;

            if (value > 150) {		//look for long start pulse
                i=0;
                result=0;
                buffer[0]=value;
            }	
        }

        // Read 7 bit command and 5 bit address
        for (i=1; i<=12; i++) {
            result = result >> 1;	//shift each bit down, we receive LSB -> MSB

            // Start timer on falling edge, record value on rising
            while (PORTAbits.RA4);	
            TMR0L = 0x00;
            while (!PORTAbits.RA4);
            value = TMR0L;

            buffer[i]=value; 
            if (value > 70)		//this is a '1', so OR a '1' into the result
                    result = result |0x0800;
        }

        // Separate result into device (upper 5) and command (lower 7)
        device = result >> 7;
        command = (result & 0x7f);

        //Since our remote is Device "1" - only handle a single digit
        SetDDRamAddr(0x43);
        putcXLCD(48+(device));  

        // Prepare each digit
        A = (command/100);
        temp = (command % 100);
        B = (temp/10);
        C = (temp % 10);

        // Display command
        SetDDRamAddr(0x4D);
        putcXLCD(48+(A));
        putcXLCD(48+(B));
        putcXLCD(48+(C));

        Delay10KTCYx(5);
        /*/
    }
}
