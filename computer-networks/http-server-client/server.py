import socket

serverPort = 6789

def createResponse(filename):
    try:
        f = open(filename[1:]) # Throws IOError if file not found
        print(filename, "found")
        
        response = ("HTTP/1.1 200 OK\r\n\r\n" + f.read() + "\r\n").encode()
        print(filename, "delivered")
    
    except IOError:
        print(filename, "NOT found")

        with open("Error.html") as f:
            response = ("HTTP/1.1 404 Not found\r\n\r\n" + f.read() + "\r\n").encode()

        print("file not found message delivered")
    
    return response

# Create an IPv4 TCP socket
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Prepare the sever socket 
serverSocket.bind(('', serverPort))
serverSocket.listen(1)

# Establish the connection 
print("server started...") 
connectionSocket, addr = serverSocket.accept()

# Receive the request, then form and send the response
message = connectionSocket.recv(1024).decode()

response = createResponse(message.split()[1])
    
connectionSocket.send(response)

connectionSocket.close()
serverSocket.close()
print("server closed...") 