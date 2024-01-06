#include <p18cxxx.h>
#include <delays.h>
#include <stdlib.h>

#pragma config FOSC = INTIO67, PLLCFG = OFF, PRICLKEN = ON,FCMEN = ON, PWRTEN = OFF
#pragma config BOREN = SBORDIS, BORV = 250, WDTEN = OFF, WDTPS = 4096, PBADEN = OFF
#pragma config HFOFST = OFF, MCLRE = EXTMCLR, STVREN = ON, LVP = OFF, DEBUG = ON


void main(void) {
    unsigned char counter;

    //make PIC18 run at 1MHz , Tcy=1MHz/4=250KHz.
    OSCCON2 = 0x00;
    OSCCON = 0x30;

    //port A is just used for debugging purpose.
    TRISA = 0x00;
    ANSELA = 0x00;

    //setup Tim0 as 8 bits timer, 1:16 prescalar
    T0CON = 0b11000011;

    counter = 0;
    INTCONbits.TMR0IE = 1;
    while (1) {
        //delay for 10 ms
        TMR0L = 0x64;   //100d, 10 ms takes 156 counts. 2^8 - 156 = 100
        INTCONbits.TMR0IF = 0;
        while (!(INTCONbits.TMR0IF));

        //increase counter for debugging purposes
        counter++;
        PORTA = counter;
    }
}
