# The "chipping" codes used to encode two messages into one transmission
C1 = [1,1,-1,1]
C2 = [-1,1,-1,-1]

# Small messages for testing
M1 = "HELLO"
M2 = "OYEAH"

ENCODING_MAP = {"A" : [1,1,1,1,1],     \
                "B" : [1,1,1,1,-1],    \
                "C" : [1,1,1,-1,1],    \
                "D" : [1,1,1,-1,-1],   \
                "E" : [1,1,-1,1,1],    \
                "F" : [1,1,-1,1,-1],   \
                "G" : [1,1,-1,-1,1],   \
                "H" : [1,1,-1,-1,-1],  \
                "I" : [1,-1,1,1,1],    \
                "J" : [1,-1,1,1,-1],   \
                "K" : [1,-1,1,-1,1],   \
                "L" : [1,-1,1,-1,-1],  \
                "M" : [1,-1,-1,1,1],   \
                "N" : [1,-1,-1,1,-1],  \
                "O" : [1,-1,-1,-1,1],  \
                "P" : [1,-1,-1,-1,-1], \
                "Q" : [-1,1,1,1,1],    \
                "R" : [-1,1,1,1,-1],   \
                "S" : [-1,1,1,-1,1],   \
                "T" : [-1,1,1,-1,-1],  \
                "U" : [-1,1,-1,1,1],   \
                "V" : [-1,1,-1,1,-1],  \
                "W" : [-1,1,-1,-1,1],  \
                "X" : [-1,1,-1,-1,-1], \
                "Y" : [-1,-1,1,1,1],   \
                "Z" : [-1,-1,1,1,-1],  \
                " " : [-1,-1,1,-1,1],  \
                "," : [-1,-1,1,-1,-1], \
                "." : [-1,-1,-1,1,1],  \
                "'" : [-1,-1,-1,1,-1], \
                "?" : [-1,-1,-1,-1,1], \
                "!" : [-1,-1,-1,-1,-1] }

# Each letter is encoded using the map above (1 letter -> 5 bits). Then, we use
# each bit and multiply it by the CDMA (chipping) code (4 bits). So 1 letter 
# turns into 20 bits once it has been fully encoded
def encodeStringToCDMA(stringInput, cdmaCode):
    stringEncodedCDMA = []

    for letter in stringInput:
        for letterBit in ENCODING_MAP[letter]:
            for cdmaBit in cdmaCode:
                stringEncodedCDMA.append(letterBit * cdmaBit)

    return stringEncodedCDMA

# Simply adds each bit of the two transmissions; bits are in the set {2, 0, -2}
def addTransmissions(tx1, tx2):
    # this simple method only works for same length strings
    if(len(tx1)!=len(tx2)) : return 0

    sumOfTransmissions = [tx1[i] + tx2[i] for i in range(len(tx1))]

    return sumOfTransmissions

# Takes a transmission (list of integers {2, 0, -2}) and turns them into a
# comma separated string which is written to a file
def outputRawTransmission(tx):
    output = ""
    for bit in tx:
        output += str(bit) + ","
    
    # remove the trailing comma
    output = output[:-1]
    print(output)
     
    with open('tx.txt', 'w') as outfile:
        outfile.write(output)

# Encode the messages, combine, and output transmission
trans1 = encodeStringToCDMA(M1,C1)
trans2 = encodeStringToCDMA(M2,C2)

combinedTrans = addTransmissions(trans1, trans2)

outputRawTransmission(combinedTrans)