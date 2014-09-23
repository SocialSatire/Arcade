import pygame

# We need some more code here somebody get on that.
#Thanks for the good start, Mike. I would never have figured out how to import the libraries.

class Game:
	def __init__(self, width=640,height=480):
        pygame.init()
        self.width = width
        self.height = height
        self.screen = pygame.display.set_mode(self.width, self.height)
	
	def play(self):
		print			# We should be able to put the game code here

if __name__ == "__main__":
    GameWindow = Game()
    GameWindow.play()