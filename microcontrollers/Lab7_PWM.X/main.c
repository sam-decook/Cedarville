/* Lab 7: Pulse Width Modulation
 * This code demonstrates multiple uses of PWM, changing the hue of a tri-color
 * LED, the position of a servo, and the speed of a fan
 */

#include <p18f45k22.h>
#include <delays.h>

#pragma config FOSC = INTIO67, PLLCFG = OFF, PRICLKEN = ON, FCMEN = ON, PWRTEN = OFF
#pragma config BOREN = SBORDIS, BORV = 250, WDTEN = OFF, WDTPS = 4096, PBADEN = OFF
#pragma config HFOFST = OFF, MCLRE = EXTMCLR, STVREN = ON, LVP = OFF, DEBUG = ON
#pragma config CCP3MX = PORTE0

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

/* These three functions set each individual LED color
 * We only need an 8 bit resolution, so we use uchars */
void RED_LED  (unsigned char value) { CCPR1L = value; }
void GREEN_LED(unsigned char value) { CCPR2L = value; }
void BLUE_LED (unsigned char value) { CCPR3L = value; }

/* This function changes the duty cycle to move the servo
 * to the desired position */
void Servo_4(unsigned char value) {
    CCPR4L = value >> 2;
    CCP4CON = CCP4CON | (value << 4);
}

/* This function changes the duty cycle to change the
 * speed of the fan */
void FAN(unsigned char value) { CCPR5L = value; }

void main() {
    unsigned char i, j , k, counter = 32, value;
    
    // Set uC to 1 MHz
    OSCCON = 0x30;
    OSCTUNEbits.PLLEN = 0;
    
    // Setup port C, D, E for digital output
    ANSELC = 0x00;
    TRISC = 0x00;
    ANSELD = 0x00;
    TRISD = 0x00;
    ANSELE = 0x00;
    TRISE = 0x00;
    
    /* LED register setup
    CCPTMRS0 = 0x00;
    PR2 = 0xF9;
    T2CON = 0x04;
    CCP1CON = 0x0C;
    CCP2CON = 0x0C;
    CCP3CON = 0x0C; */
    
    // Servo and fan register setup
    CCPTMRS1 = 0x00;
    PR2 = 0xFF;
    T2CON = 0x06;
    CCP4CON = 0x0C;
    CCP5CON = 0x0C;
    
    while (1) {
        /* LED code
        for (i=1; i<127;i=i<<1){
            for (j=1; j<127;j=j<<1){
                for (k=1; k<127;k=k<<1){
                    Delay1KTCYx(20); 
                    RED_LED(k);
                    GREEN_LED(j);
                    BLUE_LED(i);
                }
            }
        } 
        */
        
        /* Servo code
        counter = counter+1;
        if (counter > 156) counter = 32; //keep it within min and max duty cycle
        Servo_4(counter);
        Delay10KTCYx(5);
        */
        
        // Fan code
        //value = 26;       //attempted 10% duty cycle
        //value = 51;       //attempted 20% duty cycle
        //value = 102;      //attempted 40% duty cycle
        //value = 153;      //attempted 60% duty cycle
        //value = 204;      //attempted 80% duty cycle
        value = 255;      //attempted 100% duty cycle
        FAN(value);
    }    
}