/* Lab 10: I2C Communication
 * This code provides a demonstration of the I2C read and write protocols
 */

#include <p18cxxx.h>
#include <delays.h>

#pragma config FOSC = INTIO67, PLLCFG = OFF, PRICLKEN = ON,FCMEN = ON, PWRTEN = ON
#pragma config BOREN = SBORDIS, BORV = 250, WDTEN = OFF, WDTPS = 4096, PBADEN = OFF
#pragma config HFOFST = OFF, MCLRE = EXTMCLR, STVREN = ON, LVP = OFF, DEBUG = ON

unsigned char SLAVE_ADDR = 0x40;

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

void I2C_write(unsigned char reg_addr, unsigned char data) {
    SSP2CON2bits.SEN = 1;		//start
    while (SSP2CON2bits.SEN);
    SSP2BUF = SLAVE_ADDR + 0;	//device address
    while (SSP2STATbits.R_NOT_W);
    SSP2BUF = reg_addr;		//register address
    while (SSP2STATbits.R_NOT_W);
    SSP2BUF = data;			//send data
    while (SSP2STATbits.R_NOT_W);
    SSP2CON2bits.PEN = 1;		//stop
    while (SSP2CON2bits.PEN);
}

unsigned char I2C_read(unsigned char reg_addr) {
    SSP2CON2bits.SEN = 1;		//start
    while (SSP2CON2bits.SEN);
    SSP2BUF = SLAVE_ADDR + 0;	//device address
    while (SSP2STATbits.R_NOT_W);
    SSP2BUF = reg_addr;		//register address
    while (SSP2STATbits.R_NOT_W);
    SSP2CON2bits.RSEN = 1;		//repeated start
    while (SSP2CON2bits.RSEN);
    SSP2BUF = SLAVE_ADDR + 1;	//device address, with read set
    while (SSP2STATbits.R_NOT_W);
    SSP2CON2bits.RCEN = 1;		//put master in receive mode
    while (SSP2CON2bits.RCEN);
    SSP2CON2bits.PEN = 1;		//stop
    while (SSP2CON2bits.PEN);
    return SSP2BUF;
}

void main() {
    unsigned char counter = 0, value = 0;
    
    //Configure uC for 32 MHz operation
    OSCCON = 0x62;
    OSCTUNEbits.PLLEN = 1;
    
    // Port D - digital input
    ANSELD = 0x00;
    TRISD = 0xFF;
    
    // I2C Master Mode
    SSP2ADD = 0x02;
    SSP2CON1 = 0x28;
    
    I2C_write(0x00, 0x00);              //Configure GPIOA as output
	while (1) {
		counter++;	
		if (counter>159) counter=0;
		I2C_write(0x12, counter);       //Write GPIOA with count
		Delay10KTCYx(2);
		value = I2C_read(0x13);         //Read GPIOB B as input
		Delay10KTCYx(1);
	}
}

