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

Everything past here is no longer relevant to understand the simulation. 

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

Some simulation parameters will generate different unique behaviors. For instance, it is possible for a simulation to be swarmed with red or magenta creatures nine times out of 10, but green and cyan creatures in the other. 

This is the latest version (as of March 2025) of my simulation. I had other simulations (i'll call them versions -1 and 0) that won't be on Github.
Instead of a groundeat stat, i had a health stat that allowed creatures to survive after being eaten.
In these versions, magenta and white were the dominant creatures.
These simulations take way to long to run compared to this (This program runs in O(n) time whereas versions -1 and 0 used O(n^2) time (n is the creature count)).

Notes on current simulation parameters: 

- The current simulation sits in an equilibrium, where no one creature gains the upper hand (as far as i've seen)
- Blue and purple creatures dominate, while red creatures eat from the ground
- Green, and later cyan, creatures eat these creatures
- Over time, Cyan adapted into the largest creatures, while green outcompeted red, and became the new ground eaters
- After 3-4 hours, red went extinct, although orange and yellow do still appear as mutations of green and can preform very well for short amounts of time
- Green creatures can sometimes grow to large sizes, but the largest creatures are always cyan, since they can survive long enough to have even larger children
- It is possible that red creatures adapted to become magenta
- after 7-8 hours, red has rebounded, and green is considerably rarer than before
- patches of ground will heal, red creatures will eat it up, and then move onto the next patch of ground
- The red creatures that are now the most common creatures seem to be the magenta creatures I mentioned above

Notes on known behaviors: 

- Red and magenta creatures will sometimes do a sweep of the map, eating all the food as they pass through. I call this the "mold", and often occurs when there is very little food on the ground, and creatures don't gain much from eating other creatures. 
- If eating other creatures is too good, green, gyan, and white creatures will overtake the perpetual blue and purple creatures.
- In most simulations, blue and purple creatures will evenly cover the entire map. I call this "heat death".
- The "death" of a simulation occurs when there is no more biodiversity. In other words, if there are less than three species, the simulation will almost never generate interesting behavior.


Possible bugs: 

- Movement algorithm might be incorrect: creatures might tend to move diagonally apparently, which shouldn't be allowed

Possible next features: 

- Simulation pauser
- Better simulation runner that allows you to create the simulation parameters
- Method to allow you to add your own creature into the simulation
- Sampler system: Clicking on a creature copies their DNA
- Creature IDs (ways to find common ancestors)
- WorldEdit features (walls creatures can't access, dead zone makers, undepletable ground, etc.)

If i implement those features, I will be able to run competitions between different players

- Better brains (actual neural networks)

I might just save this for version 2

This is my first real github project :)
