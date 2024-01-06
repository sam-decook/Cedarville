#include <p18cxxx.h>
#include "xlcd.h"
#include <stdlib.h>
#include <delays.h>

#pragma config FOSC=INTIO67, PLLCFG=OFF, PRICLKEN=ON,FCMEN=ON, PWRTEN=OFF
#pragma config BOREN=SBORDIS, BORV=250, WDTEN=OFF, WDTPS=4096, PBADEN=OFF
#pragma config HFOFST=OFF, MCLRE=EXTMCLR, STVREN=ON, LVP=OFF, DEBUG= ON

unsigned rom char sinwave[32]={
	0, 1, 2, 6, 10, 15, 22, 29, 37, 47, 57, 68, 79, 91, 103, 115, 128,
	141, 153, 165, 177, 188, 199, 209, 219, 227, 234, 241, 246, 250, 254, 255};

void DAC_A(unsigned char data) {
    PORTDbits.RD2 = 0;              //activate chip
    SSP2BUF = 0x10 | (data >> 4);   //  send first (high) byte
    while (!SSP2STATbits.BF);       //  wait for it to be written
    SSP2BUF = data << 4;            //  send second (low) byte
    while (!SSP2STATbits.BF);       //  wait for it to be written
    PORTDbits.RD2 = 1;              //deactivate chip
}

void main() {
    unsigned char i = 0;
    
    // Configure uC for 32 MHz
    OSCCON = 0x62;          //8 MHz
    OSCTUNEbits.PLLEN = 1;  //with PLL by 4
    
    // Port A - digital output
    ANSELA = 0x00;
    TRISA = 0x00;
    // Port D - digital output
    ANSELD = 0x00;
    TRISD = 0x00;
    
    // Register setup
    SSP2STAT = 0xC0;
    SSP2CON1 = 0x20;
    VREFCON0 = 0x00;
    VREFCON1 = 0xE0;
    
    while (1) {
        /*-------- Code for saw-tooth wave --------*/
        //DAC_A(i++);
        
        /*---------- Code for sine wave -----------*/
        //while (i < 32) DAC_A(sinwave[i++]);
        //while (i > 0)  DAC_A(sinwave[--i]);
        
        /*--------- Code for triangle wave --------*/
        while (i < 32) VREFCON2 = i++;
        while (i > 0)  VREFCON2 = --i;
    }
}