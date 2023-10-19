# At no point does this client validate the server's responses
# It just trusts Google's clients will always send the expected response
# And that's fine, this assignment is intended to familiarize us with the SMTP protocol

import socket
import ssl
import base64 

NL = '\r\n'
OK_CODE = b"250"
SMTP_SERVER = "smtp.gmail.com"
SSL_PORT = 465
USERNAME = base64.b64encode(b"sjd.networks.test@gmail.com")
# This app password has been removed, so this program will no longer work #
PASSWORD = base64.b64encode(b"vidn qdcb tvet ruam")

# create a TCP/IP socket and wrap it with SSL
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
ctx = ssl.create_default_context()
ssock = ctx.wrap_socket(sock, server_hostname=SMTP_SERVER)
ssock.connect((SMTP_SERVER, SSL_PORT))

print(ssock.cipher())

def send(s) :
    ssock.send(s.encode())
    print("SENT:" + NL + s)
    
def recv(s="") :
    s += ssock.recv(1024).decode()
    print("RCVD:" + NL + s)

# start interaction
send("EHLO localhost" + NL)

# look for 250 - OK message, then read the rest of the message
s = ssock.recv(1)
while OK_CODE not in s:
    s += ssock.recv(1)

recv(s.decode())

# begin authentication interaction
send("AUTH LOGIN" + NL)
recv()
send(USERNAME.decode() + NL)
recv()
send(PASSWORD.decode() + NL)
recv()

# normal SMTP dialogue
send("MAIL FROM: <sjd.networks.test@gmail.com>" + NL)
recv()
send("RCPT TO: <samdecook@cedarville.edu>" + NL)
recv()
send("DATA" + NL)
recv()
send(
    "From: My client" + NL +
    "To: samdecook@cedarville.edu" + NL +
    "Reply-To: sjd.networks.test@gmail.com" + NL +
    "Subject: SMPT mail" + NL
)
send("Hello from my python client!" + NL + "." + NL)
recv()

# send quit command
send("QUIT" + NL)

# close connection
ssock.close()
