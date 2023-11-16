# The "chipping" codes used to encode two messages into one transmission
C1 = [1,1,-1,1]
C2 = [-1,1,-1,-1]

# If you interpret each sequence of 5 bits as a binary number (where -1 = 0)
# you can use that number to index into the array to decode it
DECODE = ["!","?","'",".",","," ","Z","Y","X","W","V","U","T","S","R","Q",\
          "P","O","N","M","L","K","J","I","H","G","F","E","D","C","B","A"]


# Takes a list of five numbers in the set {1, -1} and interprets it as a binary
# number, where -1 = 0, and returns that number 
def toSum(bits):
    s = 0

    # Amount to shift each bit
    shift = len(bits) - 1
    for i in range(len(bits)):
        if bits[i] == 1:
            s += 1 << shift
        shift -= 1
    
    return s

# Read file in
with open("tx.txt", "r") as input:
    receivedTx = [int(bit) for bit in input.read().split(',')]

# Separate messages. For each chunk (same length as chipping code), multiply 
# the transmitted bit with the chipping bit, sum them, divide by the length of
# the chipping code. A positive number is a "1", a negative number is a "-1"
def demux(tx):
    tx1 = []
    tx2 = []
    step = len(C1)

    for i in range(0, len(tx), step):
        chunk = tx[i : i + step]
        s1 = 0
        s2 = 0
        for j in range(step):
            s1 += chunk[j] * C1[j]
            s2 += chunk[j] * C2[j]
        
        # Integer division (//) to keep it from turning into a float
        tx1.append(s1 // step)
        tx2.append(s2 // step)
    
    return tx1, tx2

# For each chunk of 5 bits, convert them to an integer to index into the DECODE
# array and return the plaintext message
def decode(tx):
    chunk = 5
    msg = ""
    for i in  range(0, len(tx), chunk):
        ind = toSum(tx[i : i + chunk])
        msg += DECODE[ind]
    
    return msg

# Print out message
tx1, tx2 = demux(receivedTx)

print(decode(tx1))
print(decode(tx2))