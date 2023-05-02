#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdbool.h>
#include <sys/un.h>
#include <signal.h>
#include <stdarg.h>
#include <sys/wait.h>


// Prototypes for internal functions and utilities
void error(const char *fmt, ...);
int runClient(char *clientName, int numMessages, char **messages);
int runServer();
void serverReady(int signal);

bool serverIsReady = false;

// Prototypes for functions to be implemented by students
void server();
void client(char *clientName, int numMessages, char *messages[]);

void verror(const char *fmt, va_list argp)
{
    fprintf(stderr, "error: ");
    vfprintf(stderr, fmt, argp);
    fprintf(stderr, "\n");
}

void error(const char *fmt, ...)
{
    va_list argp;
    va_start(argp, fmt);
    verror(fmt, argp);
    va_end(argp);
    exit(1);
}

int runServer(int port) {
    int forkPID = fork();
    if (forkPID < 0)
        error("ERROR forking server");
    else if (forkPID == 0) {
        server();
        exit(0);
    } else {
        fprintf(stderr, "Main: Server(%d) launched...\n", forkPID);
    }
    return forkPID;
}

int runClient(char *clientName, int numMessages, char **messages) {
    fflush(stdout);
    fprintf(stderr, "Launching client %s...\n", clientName);
    int forkPID = fork();
    if (forkPID < 0)

        error("ERROR forking client %s", clientName);
    else if (forkPID == 0) {
        client(clientName, numMessages, messages);
        exit(0);
    }
    return forkPID;
}

void serverReady(int signal) {
    serverIsReady = true;
}

#define NUM_CLIENTS 2
#define MAX_MESSAGES 5
#define MAX_CLIENT_NAME_LENGTH 10

struct client {
    char name[MAX_CLIENT_NAME_LENGTH];
    int num_messages;
    char *messages[MAX_MESSAGES];
    int PID;
};

// Modify these to implement different scenarios
struct client clients[] = {
        {"Uno", 3, {"Mensaje 1-1", "Mensaje 1-2", "Mensaje 1-3"}},
        {"Dos", 3, {"Mensaje 2-1", "Mensaje 2-2", "Mensaje 2-3"}}
};

int main() {
    signal(SIGUSR1, serverReady);
    int serverPID = runServer(getpid());
    while(!serverIsReady) {
        sleep(1);
    }
    fprintf(stderr, "Main: Server(%d) signaled ready to receive messages\n", serverPID);

    for (int client = 0 ; client < NUM_CLIENTS ; client++) {
        clients[client].PID = runClient(clients[client].name, clients[client].num_messages,
                                        clients[client].messages);
    }

    for (int client = 0 ; client < NUM_CLIENTS ; client++) {
        int clientStatus;
        fprintf(stderr, "Main: Waiting for client %s(%d) to complete...\n", clients[client].name, clients[client].PID);
        waitpid(clients[client].PID, &clientStatus, 0);
        fprintf(stderr, "Main: Client %s(%d) terminated with status: %d\n",
                clients[client].name, clients[client].PID, clientStatus);
    }

    fprintf(stderr, "Main: Killing server (%d)\n", serverPID);
    kill(serverPID, SIGINT);
    int statusServer;
    pid_t wait_result = waitpid(serverPID, &statusServer, 0);
    fprintf(stderr, "Main: Server(%d) terminated with status: %d\n", serverPID, statusServer);
    return 0;
}



//*********************************************************************************
//**************************** EDIT FROM HERE *************************************
//#you can create the global variables and functions that you consider necessary***
//*********************************************************************************

#define PORT_NUMBER 47462

bool serverShutdown = false;

void shutdownServer(int signal) {
    //Indicate that the server has to shutdown
    //Wait for the children to finish
    //Exit
    serverShutdown = true;
    while (wait(NULL) > 0);
    exit(0);
}

void client(char *clientName, int numMessages, char *messages[])
{
    char buffer[256];
    int openskt, count;
    struct sockaddr_in saddress;
    struct hostent *server;

    server = gethostbyname("111.0.1.1");
    if (server == NULL){
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &saddress, sizeof(saddress));
    saddress.sin_family = AF_INET;
    bcopy((char *)server->h_addr,
          (char *)&saddress.sin_addr.s_addr,
          server->h_length);

    saddress.sin_port = htons(PORT_NUMBER);

    for(int i = 0; i < numMessages; i++)
    {
        openskt = socket(AF_INET, SOCK_STREAM, 0);
        if (openskt < 0){
            error("ERROR opening socket");
        }
        if (connect(openskt,(struct sockaddr *) &saddress,sizeof(saddress)) < 0){
            error("ERROR connecting");
        }
        strcpy(buffer, messages[i]);
        count = write(openskt,buffer,strlen(messages[i]));
        if (count < 0){
            error("ERROR writing to socket");
        }
        bzero(buffer,256);
        count = read(openskt,buffer,255);
        if (count < 0)
            error("ERROR reading from socket");
        printf("Client %s(%d): Return message: %s\n", clientName, getpid(), buffer);
        close(openskt);
    }




    //Open the socket
    //Connect to the server
    //For each message, write to the server and read the response
    //Accept connection and create a child proccess to read and write
    printf("%s : %d : %s\n", clientName, getpid(), buffer);
    fflush(stdout);
    sleep(1);
    //Close socket
}

void server()
{
    char buffer[256];
    int openskt, newskt, count;
    struct sockaddr_in server, client;
    socklen_t clen;
    //Handle SIGINT so the server stops when the main process kills it
    signal(SIGINT, shutdownServer);
    //Open the socket
    openskt = socket(AF_INET, SOCK_STREAM, 0);
    if (openskt  < 0) printf("error in open the socket");
    bzero((char *)&server, sizeof(server));

    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(PORT_NUMBER);
    //Bind the socket
    if (bind(openskt, (struct sockaddr *)&server, sizeof(server)) < 0){
        error("error in binding the socket");
    }
    listen(openskt, 5);
    //Signal server is ready
    kill(getppid(), SIGUSR1);
    clen = sizeof(client);
    //Accept connection and create a child proccess to read and write
    while(!serverShutdown){
        newskt = accept(openskt, (struct sockaddr *)&client,&clen);
        if (newskt < 0){
            error("Error on accept");
        }
        pid_t pid = fork();
        if (pid < 0){
            error("Error on fork");
        } else if (pid == 0){
            close(openskt);
            bzero(buffer, 256);
            count = read(newskt, buffer, 255);
            if (count < 0 ){
                error("Error reading from socket");
            }
            printf("Server child(%d): got message: %s\n", getpid(), buffer); //expected output
            count = write(newskt, buffer, strlen(buffer));
            fflush(stdout);
            if (count < 0){
                error("Error writing to socket");
            }
            exit(0);
        }else{
            //Close socket
            close(newskt);
        }
        //Close socket
        close(openskt);
    }

    //printf("Server : %d : %s\n", getpid(), buffer); //expected output
}
