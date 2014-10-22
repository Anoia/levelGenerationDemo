levelGenerationDemo
===================

This small program presents different algorithms that procedurally generate random levels for computer games. It was written during my thesis, a more detailed explanation of how the algorithms work can be found there.
Controls:


SPACE - generate new level

S - choose scatter algorithm

R - choose Rogue algorithm

B - choose BSP algorithm

C - choose cave generation with cellular automata 

V - choose alternative cave generation with drunken walk 


Algorithms:

Scatter: tries to place rooms with random size and position, rejects rooms that intersect with others and creates a new one instead; very inefficient

Rogue: generates level like the game 'Rogue'; divides the map in a grid and connects the grid cells, generates rooms or corridors in cells

BSP: binary space partitioning: divides the map vertically or horizontally in two submaps and keeps doing that until submaps are room-sized. this creates a tree-structure. rooms are created in leaves of the tree 

Cellular Automata: uses cellular automata to generate a cave; all tiles are randomly created as wall or room; cells iteratively change their state depending on the neighbours states 

Drunken Walk: very simple cave generation by simulating drunken person digging around (randomly choosing a new direction each step)
