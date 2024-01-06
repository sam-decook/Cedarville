/* EGCP 2120 Microcontrollers Final Project
 * 
 * This is the code to implement a calculator on the PIC18F45K22
 * It uses two keypads and outputs to an LCD display
 * 
 * Authors: Sam DeCook and Alvin Solomon
 */

#include <p18cxxx.h>
#include "my_xlcd.h"
#include <delays.h>

#pragma config FOSC = INTIO67, PLLCFG = OFF, PRICLKEN = ON,FCMEN = ON, PWRTEN = ON
#pragma config BOREN = SBORDIS, BORV = 250, WDTEN = OFF, WDTPS = 4096, PBADEN = OFF
#pragma config HFOFST = OFF, MCLRE = EXTMCLR, STVREN = ON, LVP = OFF, DEBUG = ON

/* I am defining non-numeric values (the operations, =, and clear)
 * The code for the numbers will be their ASCII code (0-9 is 0x3[0-9]), so 
 * there's no reason to define them - we can just bitwise-AND with 0xF0
 * The rest will also be their ASCII code, except the +/- operator */
#define ADD     0x2B    // +
#define SUB     0x2D    // -
#define MULT    0x2A    // *
#define DIV     0x2F    // /
#define EQU     0x3D    // =
#define CLEAR   0xE0

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

//Keypad 1 - numbers
//Pin1: RD7    Pin2: RA3    Pin3: RD3    Pin4: RA6
//Pin5: RA5    Pin6: RA4    Pin7: RA2    Pin8: RA7
//Pin9: RA1    Pin10:RA0

//Keypad 2 - operation
//Pin4: RD6    Pin5: RD5    Pin6: RD4    Pin7: RD2
//Pin9: RD1    Pin10:RD0
unsigned char getKeyPad(void) {
    PORTA |= 0xFF;
    PORTD |= 0xFF;
    
    PORTAbits.RA0 = 0;
    if (!PORTAbits.RA4) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA4)
            return 0x31;
    }
    if (!PORTAbits.RA5) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA5)
            return 0x32;
    }
    if (!PORTAbits.RA6) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA6)
            return 0x33;
    }
    PORTAbits.RA0 = 1;
    
    PORTAbits.RA1 = 0;
    if (!PORTAbits.RA4) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA4)
            return 0x34;
    }
    if (!PORTAbits.RA5) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA5)
            return 0x35;
    }
    if (!PORTAbits.RA6) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA6)
            return 0x36;
    }
    PORTAbits.RA1 = 1;
    
    PORTAbits.RA2 = 0;
    if (!PORTAbits.RA4) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA4)
            return 0x37;
    }
    if (!PORTAbits.RA5) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA5)
            return 0x38;
    }
    if (!PORTAbits.RA6) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA6)
            return 0x30;       
    }
    PORTAbits.RA2 = 1;
    
    PORTAbits.RA7 = 0;
    if (!PORTAbits.RA6) {
        Delay1KTCYx(3);
        if (!PORTAbits.RA6)
            return 0x39;
    }
    PORTAbits.RA7 = 1;
    
    PORTAbits.RA3 = 0;
    if (!PORTDbits.RD3) {
        Delay1KTCYx(3);
        if (!PORTDbits.RD3)
            return 0x20;
    }
    if (!PORTDbits.RD7) {
        Delay1KTCYx(3);
        if (!PORTDbits.RD7)
            return 0x40;
    }
    PORTAbits.RA3 = 1;
    
    PORTDbits.RD0 = 0;
    if (!PORTDbits.RD4) { // plus
        Delay1KTCYx(3);
        if (!PORTDbits.RD4)
            return ADD;
    }
    if (!PORTDbits.RD5) { // minus
        Delay1KTCYx(3);
        if (!PORTDbits.RD5)
            return SUB;
    }
    if (!PORTDbits.RD6) { // equals
        Delay1KTCYx(3);
        if (!PORTDbits.RD6)
            return EQU;
    }
    PORTDbits.RD0 = 1;
    
    PORTDbits.RD1 = 0;
    if (!PORTDbits.RD4) { // multiply
        Delay1KTCYx(3);
        if (!PORTDbits.RD4)
            return MULT;
    }
    if (!PORTDbits.RD5) { // divide
        Delay1KTCYx(3);
        if (!PORTDbits.RD5)
            return DIV;
    }
    PORTDbits.RD1 = 1;
    
    PORTDbits.RD2 = 0;
    if (!PORTDbits.RD6) { //clear
        Delay1KTCYx(3);
        if (!PORTDbits.RD6)
            return CLEAR;
    }
    PORTDbits.RD2 = 1;
}

char* reverse(char *buff, int i, int j) {
    char t;
    while (i < j) {
        t = buff[i];
        buff[i++] = buff[j];
        buff[j--] = t;
    }
    return buff;
}

char* itoa(long value, char* buff) {
    long n = value < 0 ? -value : value;
    int i = 0;
    while(n) {
        buff[i++] = (n % 10) + 48;
        n /= 10;
    }
    if (i == 0)    buff[i++] = '0';
    if (value < 0) buff[i++] = '-';
    buff[i] = '\0';
    return reverse(buff, 0, i - 1);
}

void display(long numbers[2], unsigned char op) {
    char c_num[8];
    
    SetDDRamAddr(0x00);
    putsXLCD(itoa(numbers[0], c_num));
    
    if (op != 0) {
        putcXLCD(op);
    }
    if (numbers[1] != 0) {
        putsXLCD(itoa(numbers[1], c_num));
    }
}

long compute(long numbers[2], int op) {
    switch (op){
        case ADD:
            return numbers[0] + numbers[1];
        case SUB:
            return numbers[0] - numbers[1];
        case MULT:
            return numbers[0] * numbers[1];
        case DIV:
            return numbers[0] / numbers[1];
        default:
            return -1;
    }
}

void clearLCD(void) {
    SetDDRamAddr(0x00);
    putrsXLCD("                ");
    SetDDRamAddr(0x40);
    putrsXLCD("                ");
}

void main() {
    unsigned char key, op = 0, currNum = 0;
    long numbers[2] = {0, 0};
    long result = 0;
    char c_result[15] = "";
    
    // Set up uC for 1 MHz operation
    OSCCON = 0x30;
    OSCCON2 = 0x00;
    
    // Port C - digital output (LCD)
    TRISC = 0x00;
    ANSELC = 0x00;
    
    // Port A - digital (keypad)
    // 0-3,7: out   4-6: in
    TRISA = 0x70;
    ANSELA = 0x00;
    
    // Port D - digital (keypad)
    // 0-2: out     3-7: in
    TRISD = 0xF8;
    ANSELD = 0x00;
    
    // configure external LCD
	OpenXLCD( FOUR_BIT & LINES_5X7 );
	WriteCmdXLCD( SHIFT_DISP_LEFT ); 
	while( BusyXLCD() );
	WriteCmdXLCD(0x0C); // Turn blink off
    
    SetDDRamAddr(0x00);
    putrsXLCD("Calculator Final");
    SetDDRamAddr(0x40);
    putrsXLCD("-DeCook, Solomon");
    Delay10KTCYx(50);
    clearLCD();
    
    while (1) {
        key = getKeyPad();
        
        if ((key >= 0x30) && (key <= 0x39)) {   //numbers
            if (result != 0) {
                result = 0;
                clearLCD();
            }
            numbers[currNum] *= 10;
            if (numbers[currNum] >= 0) {
                numbers[currNum] += (key & 0x0F);   //mask off top byte
            } else {
                numbers[currNum] -= (key & 0x0F);   //mask off top byte
            }
            display(numbers, op);
        } else if (key == ADD) {
            if (result != 0) {
                numbers[0] = result;
                result = 0;
                clearLCD();
            }
            op = ADD;
            currNum = 1;
            display(numbers, op);
        } else if (key == SUB) {
            if (result != 0) {
                numbers[0] = result;
                result = 0;
                clearLCD();
            }
            op = SUB;
            currNum = 1;
            display(numbers, op);
        } else if (key == MULT) {
            if (result != 0) {
                numbers[0] = result;
                result = 0;
                clearLCD();
            }
            op = MULT;
            currNum = 1;
            display(numbers, op);
        } else if (key == DIV) {
            if (result != 0) {
                numbers[0] = result;
                result = 0;
                clearLCD();
            }
            op = DIV;
            currNum = 1;
            display(numbers, op);
        } else if ((key == EQU) && (currNum == 1)) {
            result = compute(numbers, op);
            SetDDRamAddr(0x40);
            putcXLCD('=');
            putsXLCD(itoa(result, c_result));
            numbers[0] = 0;
            numbers[1] = 0;
            currNum = 0;
            op = 0;
        } else if (key == CLEAR) {
            clearLCD();
            numbers[0] = 0;
            numbers[1] = 0;
            result = 0;
            currNum = 0;
            op = 0;
        } else if (key == 0x20) {   // +/- operator
            numbers[currNum] = -numbers[currNum];
            display(numbers, op);
        }
        
        Delay10KTCYx(5);
    }
}