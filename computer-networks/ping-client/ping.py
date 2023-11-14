import socket
import os
import struct
import time
import select

""" IPv4 header (relevant parts only)
     0       4       8      12      16      20      24      28    31
    | - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
  0 |       |       |           |   |                               |
  4 |                               |     |                         |
  8 |      TTL      |    Protocol   |                               |
 12 |                             Source                            |
 16 |                          Destination                          |
    | - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
"""
"""ICMP header (with our specific content; bytes 4-11)
     0       4       8      12      16      20      24      28    31
    | - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
  0 |      Type     |      Code     |            Checksum           |
  4 |               ID              |            Sequence           |
  8 |                           Time Sent                           |
    | - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
"""

ICMP_ECHO_REQUEST =  8   
IP_ICMP_PROTOCOL  =  1
ICMP_ECHO_REPLY   =  0
ICMP_ECHO_CODE    =  0

def checksum(byteString):
    csum = 0
    count = 0
    countTo = (len(byteString) // 2) * 2 # floor division
    while count < countTo:
        thisVal = byteString[count+1] * 256 + byteString[count]
        csum += thisVal
        csum &= 0xffffffff 
        count += 2
        
    if countTo < len(byteString): # catches the last byte for odd length str
        csum += byteString[len(byteString) - 1]
        csum &= 0xffffffff

    csum = (csum >> 16) + (csum & 0xffff)
    csum += csum >> 16
    answer = ~csum
    answer &= 0xffff
    answer = answer >> 8 | (answer << 8 & 0xff00)
    return answer

def receiveOnePing(mySocket, ID, timeout, seqNum):
    # Wait for socket to be ready and record the amount of time that took
    whatReady = select.select([mySocket], [], [], timeout)
    if whatReady[0] == []: # Timeout
        return "Request timed out."

    timeReceived = time.time()
    recPacket, _ = mySocket.recvfrom(1024)

    print("[*] Received packet of length:", len(recPacket))

    #Fetch the IP header data you need
    ipHeader = recPacket[:20]
    _, _, _, _, _, ttl, protocol, _, s1, s2, s3, s4, \
        d1, d2, d3, d4 = struct.unpack("BBH HH BBH BBBB BBBB", ipHeader)
    
    isICMP = "True" if protocol == IP_ICMP_PROTOCOL else "False"
    src = str(s1) + "." + str(s2) + "." + str(s3) + "." + str(s4)
    dst = str(d1) + "." + str(d2) + "." + str(d3) + "." + str(d4)

    #Print IP data
    print("IP: " + src + " -> " + dst)
    print("TTL: " + str(ttl))
    print("Upper Layer ICMP? " + isICMP)

    #Fetch the ICMP header data you need
    icmpHeader = recPacket[20:]
    icmpType, icmpCode, cs, icmpId, icmpSeq, timeSent = struct.unpack("BBHHHd", icmpHeader)

    #Zero the checksum field before validating the checksum
    zeroedHeader = struct.pack("BBH HHd", icmpType, icmpCode, 0, icmpId, icmpSeq, timeSent)

    checksumValid = "True" if socket.htons(cs) == checksum(zeroedHeader) else "False"
    isEchoReply = "True" if icmpType == ICMP_ECHO_REPLY else "False"
    expectedIdSeq = "True" if icmpId == ID and icmpSeq == seqNum else "False"

    #Print ICMP data
    print("ICMP Data: " + str(timeSent))
    print("ICMP Checksum valid? " + checksumValid)
    print("ICMP Echo Reply? " + isEchoReply)
    print("ICMP IDs & Seq Nums as Expected? " + expectedIdSeq)

    #Return total time elapsed in ms
    return (timeReceived - timeSent) * 1000

def sendOnePing(mySocket, destAddr, ID, seqNum):
    #Calculate the checksum on a packet with a zeroed checksum field
    header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, ICMP_ECHO_CODE, 0, ID, seqNum)
    data = struct.pack("d", time.time())
    myChecksum = checksum(header + data)

    #Change byte order from host to network order
    myChecksum = socket.htons(myChecksum)
    
    #Insert checksum and send packet
    header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, ICMP_ECHO_CODE, myChecksum, ID, seqNum)
    packet = header + data
    mySocket.sendto(packet, (destAddr, 1))

#Send and receive one ping, return the delay in milliseconds
def doOnePing(destAddr, timeout, seqNum):
    icmp = socket.getprotobyname("icmp")

    mySocket = socket.socket(socket.AF_INET, socket.SOCK_RAW, icmp)

    #Use process ID to set ID
    myID = os.getpid() & 0xFFFF

    sendOnePing(mySocket, destAddr, myID, seqNum)
    delay = receiveOnePing(mySocket, myID, timeout, seqNum)
    mySocket.close()

    return delay

#Timeout is in seconds, default 1 second
def ping(host, timeout=1):
    dest = socket.gethostbyname(host)
    print("Pinging " + dest + " using Python")
    print("")

    #Send ping requests to a server separated by approximately one second
    for i in range(1,5) :
        print("[*] Sending ping", i, "...")
        delay = doOnePing(dest, timeout, i)
        print("[*] Delay:", delay, "ms")
        print()
        time.sleep(1)
    
    print("Done Pinging " + dest)
 
    
ping("www.google.com")