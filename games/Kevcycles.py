# So this should work when I run it but it does not

# Somebody fix that


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

class cycle(pygame.sprite.Sprite):
	def __init__(self, color):			# If we ever do color selection all the 'color's will need to be changed
		pygame.sprite.Sprite.__init__(self)
		self.image, self.rect = load_png('%s_Bike.png' % (color))
		screen = pygame.display.get_surface()
		self.area = screen.get_rect()
		self.speed = 10
		self.direction = "up"	# This will probably end up changing
		self.movepos = [0,0]
		self.color = color

	def update(self):
		self.move()
		newpos = self.rect.move(self.movepos)
		if self.area.contains(newpos):
				self.rect = newpos
		else:	# Went outside of screen
			if self.color == "Red":
				player_win("Blue")
			else:
				player_win("Red")
		pygame.event.pump()
    
	def move(self):
		if direction == "up"
			self.movepos[1] = self.movepos[1] - (self.speed)
		elif direction == "left"
			self.movepos[0] = self.movepos[0] - (self.speed)
        elif direction == "right"
			self.movepos[0] = self.movepos[0] + (self.speed)
		else direction == "down"
			self.movepos[1] = self.movepos[1] + (self.speed)
	
	def set_direction(self, dir):
		self.direction = dir
	
def main():
	pygame.init()
	screen = pygame.display.set_mode((640, 480))
	pygame.display.set_caption('Kevcycles')		# We'll come up with a better name later

	# VVV This section will need to be changed to make a tiled background VVV
	
	background = pygame.Surface(screen.get_size())
	background = background.convert()
	background.fill((0, 0, 0))

	
	global player1
	global player2
	player1 = cycle("Red")
	player2 = cycle("Blue")
	playersprites = pygame.sprite.RenderPlain((player1, player2))
    screen.blit(background, (0, 0))
    pygame.display.flip()
	clock = pygame.time.Clock()
    while 1:
		clock.tick(60)
		for event in pygame.event.get():
			if event.type == QUIT:
				return
			elif event.type == KEYDOWN:
				if event.key == K_UP:
					player1.set_direction("up")
				if event.key == K_DOWN:
					player1.set_direction("down")
				if event.key == K_LEFT:
					player1.set_direction("left")
				if event.key == K_RIGHT:
					player1.set_direction("right")
				if event.key == K_w:
					player2.set_direction("up")
				if event.key == K_s:
					player2.set_direction("down")
				if event.key == K_a:
					player2.set_direction("left")
				if event.key == K_d:
					player2.set_direction("right")
			elif event.type == KEYUP:
				print "" # Pretty sure we don't need anything here but I don't want to
						 # delete the elif statement just in case
		screen.blit(background, player1.rect, player1.rect)
		screen.blit(background, player2.rect, player2.rect)
		playersprites.update()
		playersprites.draw(screen)
        pygame.display.flip()
		
if __name__ == "__main__":
    main()
