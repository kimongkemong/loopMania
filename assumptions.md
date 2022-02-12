Assumptions:

Attack:
Enemies attack from weakest to strongest, i.e slugs before vampires 
 
For every step a character takes an event is triggered for every tile on the map.

Map is a 2D graph

Assume an enemy can’t get stronger as the game goes by (Enemy will deal a flat amount of damage (no increases))

Assume a health potion can be used whenever by clicking the item in the inventory and it will show a button to consume it. A potion that has been consumed will take effect in the next round.

Assume when the user wants to change an equipped item, add the wanted item slot and it will automatically remove the previous item in that slot. The removed items will go back to the inventory. The equipment change will occur in the next round.

Assume the building that produces item, will produce item with their own rate (Vampire castle: random between 1-2 vampire each 5 cycles
 Zombie castle: random between 1-3 zombie each cycle
Barracks : random between 1-2 allied soldier when character passes through)

Assume buildings can only be moved once (when moving from card pile) and permanently stay in that position on the map until the end of the game

Assume that the character only uses the equipped weapon at the time, and will be changed if it changed in equipped inventory.

Assume that the character can use all defensive items at once, but the amount must be 1 for each type of defensive item. 

Assume the order of battle is to add the allied soldier , then battle all at once with the order of weakest to stronger enemy.

Assume in-game currency is called rupees. 

Assume Battles are turn based, i.e 1 enemy fights the character until killed, this happens weakest to strongest. 

Assume item droprates are all at 10% except "The One Ring" at 0.01%. -> Modify for balance. 

Assume character does not become stronger after gaining more experience.

Assume when cards are unable to be converted into buildings because there's another building or it is not in the correct position, the card return to the card deck as positioned before.

Assume when health is 0, game is over. When game is over, human player has to choose between restarting the game or quit

Assume character does not become stronger after gaining more experience.

Assume that when an enemy is under a trance and wins the fight, the character will not get gold or
exp for the enemy that is still under that trance.

Assume towers only attack enemies when a battle is occuring within their battle radius.

Assume that fights happen 1v1 from weakest to strongest.

Assume that DoggieCoin is a normal item in that its worth could drop if not sold, therefore the human player has to decide whether to gamble with a lower price or wait for the chance that the shop opens up again.

When an Inventory item is returned to Inventory (A new instance of the same object is created with equivalent durability - note assumes can
only initialise an item in one way)

Assume when a certain number of bosses is killed, the bosses goal is satisfied.

Assume that the onering will revive a character mid fight and reset the characters health to 100. Also the onering will only be able to used
once per drop.

Assume that the Doggie's stun attack just lasts for 1 turn (the character doesn't get to attack for 1 turn).

In confusing mode, rare item effects dont stack if they're the same effect.

Assume that if an ally survives a battle then it will be able to fight in the next battle and its health will be reset as well.

