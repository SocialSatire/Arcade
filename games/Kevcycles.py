try:
        import sys
        import random
        import math
        import os
        import getopt
        import pygame
        from socket import *
        from pygame.locals import *
except ImportError, err:
        print "couldn't load module. %s" % (err)
        sys.exit(2)

def player_win(color):
	print "Player %s won!" % (color)

def load_png(name):
        """ Load image and return image object"""
        fullname = os.path.join('images', name)
        try:
                image = pygame.image.load(fullname)
                if image.get_alpha is None:
                        image = image.convert()
                else:
                        image = image.convert_alpha()
        except pygame.error, message:
                print 'Cannot load image:', fullname
                raise SystemExit, message
        return image, image.get_rect()

class Game:
	def __init__(self, width=640,height=480):
        pygame.init()
        self.width = width
        self.height = height
        self.screen = pygame.display.set_mode(self.width, self.height)
	
	def play(self):
		print			# We should be able to put the game code here

class cycle(pygame.sprite.Sprite):
	def __init__(self, color):
	        pygame.sprite.Sprite.__init__(self)
	        self.image, self.rect = load_png('%s_Bike.png' % (color))
	        screen = pygame.display.get_surface()
	        self.area = screen.get_rect()
	        self.speed = 10
	        self.direction = "up"
	        self.movepos = [0,0]

	def update(self):
                newpos = self.rect.move(self.movepos)
                if self.area.contains(newpos):
                        self.rect = newpos
                else:	# Went outside of screen
                	if color == "Red":
                		player_win("Blue")
                	else:
                		player_win("Red")
                self.move()
                pygame.event.pump()
        
        
if __name__ == "__main__":
    GameWindow = Game()
    GameWindow.play()
