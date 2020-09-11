import socket

"""Handles the communication with the module that is sending the json for the
evoluzion"""
class Communication:


    def __init__(self, port):
        self.port = port
        try:
            self.mySocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        except socket.error as err:
            print("Problem creating the socket. Exiting")
            exit()
        self.mySocket.connect(("localhost", port))

    def readParameters(self):
        self.mySocket.send("parameters\n".encode())
        print("inviato")
        data = ""
        while('\n' not in data):
            data = data + self.mySocket.recv(1024).decode("utf-8")
        return data

    def readPopulation(self):
        self.mySocket.send("population".encode())
        while('\n' not in data):
            data = data + self.mySocket.recv(1024).decode("utf-8")
            print(data)
        return data

    def readFitness(self):
        self.mySocket.send("fitness".encode())
        while('\n' not in data):
            data = data + self.mySocket.recv(1024).decode("utf-8")
            print(data)
        return data


    def sendPopulation(self, data):
        self.mySocket.send(data.encode("'utf-8'"))
