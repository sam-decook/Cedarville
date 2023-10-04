from socket import *
import sys

serverName = sys.argv[1]
serverPort = int(sys.argv[2])

clientSocket = socket(AF_INET, SOCK_STREAM)
clientSocket.connect((serverName, serverPort))

request = "GET /" + sys.argv[3] + " HTTP/1.1\r\n"
host = "Host: cedarville.edu\r\n\r\n"
clientSocket.send(request.encode())
clientSocket.send(host.encode())

response = clientSocket.recv(1024 * 8)
print("From server:" + response.decode())

clientSocket.close()