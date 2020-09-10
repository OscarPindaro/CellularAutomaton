import socket

"""Handles the communication with the module that is sending the json for the
evoluzion"""
class Communication:


    def __init__(self, port):
        self.port = port
        try:
            self.mySocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            self.mySocket.
        except socket.error as err:
            print("Problem creating the socket. Exiting")
            exit()
        self.mySocket.connect(("localhost", port))

    def returnJson(self):
        data = ""
        while('\n' not in data):
            data = data + self.mySocket.recv(1024, flags=None).decode("utf-8")
        return data

    def sendJson(self, data):
        self.mySocket.send(data.encode("'utf-8'"))
