from socket import *
import sys
import time

# Command format:
# client.py address port file

serverName = sys.argv[1]
serverPort = int(sys.argv[2])

clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((serverName, serverPort))

clientSocket.send(("GET /" + sys.argv[3] + " HTTP/1.1\r\n").encode())
clientSocket.send(("Host: cedarville.edu\r\n\r\n").encode())

response = clientSocket.recv(1024 * 8)
print("From server:\n" + response.decode())

clientSocket.close()