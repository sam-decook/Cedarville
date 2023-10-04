from socket import *

serverPort = 6789

# Create an IPv4 TCP socket
serverSocket = socket(AF_INET, SOCK_STREAM)

# Prepare the sever socket 
serverSocket.bind(('', serverPort))
serverSocket.listen(1)

# Establish the connection 
print("server started...") 
connectionSocket, addr = serverSocket.accept()

try: 
    message = connectionSocket.recv(1024).decode()

    filename = message.split()[1]
    f = open(filename[1:]) # Throws IOError if file not found
    print(filename, "found")
    
    # Send the HTTP header into socket
    connectionSocket.send("HTTP/1.1 200 OK\r\n\r\n".encode())

    # Send the body to the client (i.e., the contents of the file)
    connectionSocket.send((f.read() + "\r\n").encode())
    
    connectionSocket.close()
    print(filename, "delivered")

except IOError:
    print(filename, "NOT found")
    
    # Send response message for file not found
    connectionSocket.send("HTTP/1.1 404 Not found\r\n\r\n".encode())

    with open("Error.html") as f:
        connectionSocket.send((f.read() + "\r\n").encode())
             
    connectionSocket.close()
    print("file not found message delivered")

serverSocket.close()
print("server closed...") 
