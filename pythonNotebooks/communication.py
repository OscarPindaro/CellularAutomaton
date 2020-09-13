import socket

"""Handles the communication with the module that is sending the json for the
evoluzion"""
class Communication:


    def __init__(self, port, print=False):
        self.port = port
        try:
            self.mySocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        except socket.error as err:
            print("Problem creating the socket. Exiting")
            exit()
        self.mySocket.connect(("localhost", port))
        self.print = print

    def readData(self, terminator):
        data = ""
        while(terminator not in data):
            data = data + self.mySocket.recv(1024).decode("utf-8")
        return data

    def readParameters(self):
        self.mySocket.send("parameters\n".encode())
        self.logPrint("READING PARAMETERS")
        return self.readData("\n")

    def readPopulation(self):
        self.logPrint("READING POPULATION")
        self.mySocket.send("population\n".encode())
        data = self.readData("\n")
        return data

    def readFitness(self):
        self.logPrint("READING FITNESS")
        self.mySocket.send("fitness\n".encode())
        data= self.readData("\n")
        return data

    def sendPopulation(self, data):
        self.logPrint("SENDING POPULATION")
        data = data + "\n"
        self.mySocket.send(data.encode("'utf-8'"))

    def logPrint(self, string):
        if self.print:
            print(string)
