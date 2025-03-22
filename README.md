# AlmostAICreatureSimulator
Version 1 of my AI creature simulator

Here's a list of everything implemented and known about the project, as well as some bugs the program might have.

The creatures in the simulation are represented by dots. 
The goal of a creature is to reproduce, and pass on its stats and "genes". 
Each creature has a reproduction timer and a death timer. 
When the reproduction timer reaches the reproduction limit, the creature will create a near copy offspring some distance away. 
When the death timer reaches zero, the creature is deleted. 
There is also a random chance that a creature will die in a random event, or from overcrowding in the simulation. 

The colors of a creature represent one of three stats that they have: 
- Red symbolizes how much they eat from the ground
- Green symbolizes a creature's size, allowing it to eat smaller creatures
- Blue decreases the chance a creature will die from random causes or from overcrowding

Each of these stats has a con as well:
- Eating from the ground (personally referred to as "Groundeat") will poison your creature in "poisoned zones"
- Size increases the rate at which you lose energy
- The blue stat's main effect is that it increases the reproduction limit

The ground is a source of food in this game. It naturally replenishes over time. 
When a creature eats from the ground, it gets closer to reproduction, and the ground loses some of its energy. 
When the ground has less than zero energy, it is considered "poisoned". 
Poisoned ground will cause creatues to lose energy if they land on it. 
A creature with a negative Groundeat stat will replenish the ground instead of depleting it. They will also gain energy from eating poisoned ground and lose energy from healthy ground. 

When reproducing, the creatures stats and genes are changed very slightly, depending on how much energy the parent has left. 
If the parent has lots of energy, less mutations will happen. 

---

Everything past here is no longer relevant to understand the simulation, and is pure mechanics. 

---

Brain: 
The creature's brain is a very rudimentary system that sees only the squares adjacent to itself. 
It records the ground health, as well as the sizes of the creatures (if there are any) on those squares. 
From there, it uses its genes (a list of 5 numbers) to give a score to each direction. The creature will then move in the direction with the highest score. 

Metagame notes: 

Common creatures: 
- Red creatures are hyper-adapted to swarm the ground and eat everything, then reproducing several times before dying.
- Green and cyan creatures exist in a similar niche, whose sole intention is to eat each other, as well as other creatures.
- Blue creatures don't do much, but they also do not die as a cause of overcrowding. Thus, they will always exist in simulations unless one species takes over (normally by eating everyone else)
- Magenta and purple creatures are the same as blue creatures, only they also compete with red creatures for food. Purple is more common in the current version of the simulation than magenta

Less common:
- Yellow and orange creatures are uncommon mutations of red creatures that grow to a medium size. They rarely exist for long, but almost always appear shortly. They will eat red, blue, and purple creatures but will get eaten by green and cyan creatures.
- White creatures share a similar niche to cyan creatures. Their existence means that different stats aren't being punished enough. 

Rare:
- Gray creatures are so rare, I've never seen them. Their whole deal is that they're the red creatures of dead zones.


Due to the evolutionary minmaxing that will eventually send all creatures down one of these paths, these are the only creatures. 
Other distinctions are only in their brains. 

This is the latest version (as of March 2025) of my simulation. I had other simulations (i'll call them versions -1 and 0) that won't be on Github.
Instead of a groundeat stat, i had a health stat that allowed creatures to survive after being eaten.
In these versions, magenta and white were the dominant creatures.
These simulations take way to long to run compared to this (This program runs in O(n) time whereas versions -1 and 0 used O(n^2) time (n is the creature count)).

Possible bugs: 

Movement algorithm might be incorrect: creatures tend to move diagonally apparently, which shouldn't be allowed

This is my first real github project :)
