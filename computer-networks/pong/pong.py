#based on code from https://github.com/Vinay609/Two-Player-Pong,
#which is not available (tried Nov 2023)
import os, pygame, sys, random, socket, struct
    
def ball_animation():
    global ball_speed_x, ball_speed_y, player_score, opponent_score, score_time
    
    ball.x += ball_speed_x
    ball.y += ball_speed_y

    # Bounce
    if ball.top <= 0 or ball.bottom >= height:
        ball_speed_y *= -1
        
    if ball.left <= 0:
        player_score += 1
        score_time = pygame.time.get_ticks()
        
    if ball.right >= width:
        opponent_score += 1
        score_time = pygame.time.get_ticks()

    # Hits player's paddle
    if ball.colliderect(player) and ball_speed_x > 0:
        if abs(ball.right - player.left) < 10:
            ball_speed_x *= -1
        elif abs(ball.bottom - player.top) < 10 and ball_speed_y > 0:
            ball_speed_y *= -1
        elif abs(ball.top- player.bottom) < 10 and ball_speed_y < 0:
            ball_speed_y *= -1
        
    # Hits opponent's paddle
    if ball.colliderect(opponent) and ball_speed_x < 0:
        if abs(ball.left - opponent.right) < 10:
            ball_speed_x *= -1
        elif abs(ball.bottom - opponent.top) < 10 and ball_speed_y > 0:
            ball_speed_y *= -1
        elif abs(ball.top - opponent.bottom) < 10 and ball_speed_y < 0:
            ball_speed_y *= -1

def player_animation():
    player.y += player_speed
    
    # Prevent paddle from moving off screen
    if player.top <= 0:
        player.top = 0
    if player.bottom >= height:
        player.bottom = height

def opponent_animation():
    opponent.y += opponent_speed
    
    # Prevent paddle from moving off screen
    if opponent.top <= 0:
        opponent.top = 0
    if opponent.bottom >= height:
        opponent.bottom = height

def ball_start():
    global ball_speed_y, ball_speed_x, score_time

    ball.center = (width//2, height//2)

    # Set countdown timer
    since_score = pygame.time.get_ticks() - score_time
    if since_score < 1000:
        number_three = game_font.render("3", False, light_grey)
        screen.blit(number_three, (width//2 - 5, height//2 + 30))
    elif since_score < 2000:
        number_two = game_font.render("2", False, light_grey)
        screen.blit(number_two, (width//2 - 5, height//2 + 30))
    elif since_score < 3000:
        number_one = game_font.render("1", False, light_grey)
        screen.blit(number_one, (width//2 - 5, height//2 + 30))

    # Hold ball in center or send it in a random direction
    if since_score < 3000:
        ball_speed_x, ball_speed_y = 0,0
    else:
        ball_speed_y = 4 * random.choice((-1,1))
        ball_speed_x = 4 * random.choice((-1,1))
        score_time = None



global ball_speed_x, ball_speed_y, player_score, opponent_score, score_time
os. environ["PYGAME_HIDE_SUPPORT_PROMPT"] = "hide"
print(sys.argv[0])

if len(sys.argv) < 2: 
    print('starting server...')
    #NOTE: giving a 0 port, allows the socket library to use a randomly selected AND open port
    serverPort = 0
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    serverSocket.bind((socket.gethostbyname(socket.gethostname()), serverPort))

    sockname = serverSocket.getsockname()
    sockIp = str(sockname[0])
    sockPort = str(sockname[1])
    print('connect client by starting: ')
    print("python", sys.argv[0], sockIp + ":" + sockPort)

    WINDOW_TITLE = "Pong SERVER " + sockIp + ":" + sockPort
    SERVER = True
    CLIENT_CONNECTED = False
    
elif len(sys.argv) < 3:
    print('starting server...')    
    ip_port = sys.argv[1].split(':')
    serverIp = ip_port[0]
    serverPort = int(ip_port[1])

    clientSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    clientSocket.connect((serverIp, serverPort))

    sockname = clientSocket.getsockname()
    clientIp = str(sockname[0])
    clientPort = str(sockname[1])

    #The client will quit abruptly if it doesn't receive a datagram from the socket in 1s
    clientSocket.settimeout(1)

    WINDOW_TITLE = "Pong CLIENT"
    SERVER = False
    SERVER_CONNECTED = False

pygame.init()

clock = pygame.time.Clock()

width = 640
height = 480
screen = pygame.display.set_mode((width, height))
pygame.display.set_caption(WINDOW_TITLE)

ball = pygame.Rect(width//2 - 7, height//2 - 7, 15, 15)
player = pygame.Rect(width - 10, height//2 - 35, 5, 70)
opponent = pygame.Rect(5, height//2 - 35, 5, 70)

bg_color = pygame.Color('grey12')
light_grey = (200,200,200)

ball_speed_x = 6 * random.choice((-1,1))
ball_speed_y = 6 * random.choice((-1,1))
player_speed = 0
opponent_speed = 0

player_score = 0
opponent_score = 0
game_font = pygame.font.Font(None,25)

score_time = True

while True:
    #Get Keyboard Input
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            break
                            
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_DOWN:
                player_speed += 5
            if event.key == pygame.K_UP:
                player_speed -= 5
                
        if event.type == pygame.KEYUP:            
            if event.key == pygame.K_DOWN:
                player_speed -= 5
            if event.key == pygame.K_UP:
                player_speed += 5

    # Both the client and server send a message to each other
    # Then each knows the others is connected, and their messages are syncronized
    # The server's state is what the game is based on
    # The client just sends its paddle speed
    if SERVER:
        if not CLIENT_CONNECTED: #only display waiting message first time we get here
            screen.fill(bg_color) # clear screen
            player_text = game_font.render("waiting for client to connect", False, light_grey)
            screen.blit(player_text,(345,235))
            pygame.display.flip()

            _, client = serverSocket.recvfrom(128)
            clientIp = client[0]
            clientPort = int(client[1])
            serverSocket.sendto(b"received connected", (clientIp, clientPort))
            CLIENT_CONNECTED = True
        
        # Send variables
        # To send score_time over, map all types other than int to 0
        score = score_time if type(score_time) is int else 0
        msg = struct.pack("iiiiii", player_speed, ball_speed_x, ball_speed_y, player_score, opponent_score, score)
        serverSocket.sendto(msg, (clientIp, clientPort))

        # Receive updates from client
        clientMsg = serverSocket.recv(512)
        opponent_speed = struct.unpack("i", clientMsg)[0]
        
    else:
        if not SERVER_CONNECTED:
            clientSocket.sendto("connected".encode(), (serverIp, serverPort))
            if clientSocket.recv(512) == b"received connected":
                SERVER_CONNECTED = True
        
        # Send the paddle speed
        msg = struct.pack("i", player_speed)
        clientSocket.sendto(msg, (serverIp, serverPort))

        # Update game state, "remap" the score to score_time, and mirror ball speed
        serverMsg = clientSocket.recv(512)
        opponent_speed, ball_speed_x, ball_speed_y, opponent_score, player_score, score = struct.unpack("iiiiii", serverMsg)
        score_time = score if score != 0 else None
        ball_speed_x *= -1

    #Update game object states
    ball_animation()
    player_animation()
    opponent_animation()

    #draw
    screen.fill(bg_color) # clear screen
    pygame.draw.rect(screen, light_grey, player) #draw player
    pygame.draw.rect(screen, light_grey, opponent) #draw opponent
    pygame.draw.ellipse(screen, light_grey, ball) #draw ball
    pygame.draw.aaline(screen, light_grey, (width//2, 0), (width//2, height)) #draw dividing line

    # True when score_time is set to a time, None => False
    if score_time:
        ball_start() #Initialize ball for another set

    #draw player score    
    player_text = game_font.render(f'{player_score}', False, light_grey)
    screen.blit(player_text,(345,235))

    #draw opponent score
    opponent_text = game_font.render(f'{opponent_score}', False, light_grey)
    screen.blit(opponent_text,(285,235))

    #buffer swap: takes what was drawn to the back buffer
    # and swaps it with the front buffer (e.g. the one we "see")
    pygame.display.flip()

    #locks frame update rate at 60Hz
    clock.tick(60) 

