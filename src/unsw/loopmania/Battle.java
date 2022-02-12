package unsw.loopmania;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import unsw.loopmania.Buildings.Tower;
import unsw.loopmania.Enemies.Zombie;

public class Battle {
    
    private List<BasicEnemy> enemies;
    private List<FightingAlly> allies = new ArrayList<FightingAlly>();
    private Character character;
    private List<Building> buildings;
    private Boolean battleWon;

    /**
     * Makes a battle with the given allies, enemies, buildings and character. The fights occur in a 
     * 1 v 1 fashion with the weakest enemies fighting first. Gold, exp and items are awarded when a 
     * enemy is killed.
     * @param enemies
     * @param allies
     * @param character
     * @param buildings
     */
    public Battle(List<BasicEnemy> enemies, List<Ally> allies, Character character, List<Building> buildings) {
        sortEnemies(enemies);
        this.enemies = enemies;
        sortAllies(allies);
        for (Ally ally : allies) {
            FightingAlly newAlly = new FightingAlly(character.getPosition(), ally.getHealthPoints(), ally.getDamage());
            this.allies.add(newAlly);
        }
        this.character = character;
        this.buildings = buildings;
        battleWon = false;
    }

    /**
     * Returns if a battle is won.
     * @return battleWon
     */
    public Boolean battleWon() {
        return battleWon;
    }

    /**
     * Begins the battle between all the participants.
     */
    public int beginBattle() {
        int numAllies = allies.size() + 1;
        int numEnemies = enemies.size();
        BasicEnemy trancedEnemy = null;
        while (numAllies > 0 && numEnemies > 0) {
            // if there are still allies available
            if (numAllies > 1) {
                FightingAlly currAlly = allies.get(0);
                currAlly.setRoundsInTrance(currAlly.getRoundsInTrance() - 1);
                BasicEnemy currEnemy = enemies.get(0);
                currEnemy.attack(currAlly);
                currAlly.attack(currEnemy);
                // buildings attack current enemy
                for (Building building : buildings) {
                    Tower tower = (Tower) building;
                    tower.towerDamage(currEnemy);
                }
                // unstuns a character
                currAlly.setStunned(false);
                // ally will either die, be converted or fight in the next round
                if (currAlly.isConverted()) {
                    Zombie zomb = new Zombie(character.getPosition());
                    enemies.add(1, zomb);
                    numEnemies += 1;
                    allies.remove(0);
                    numAllies += -1;
                } else if (currAlly.getHealth() <= 0) {
                    allies.remove(0);
                    numAllies += -1;
                } else if (currAlly.getRoundsInTrance() == 0) {
                    allies.remove(0);
                    numAllies += -1;
                    enemies.add(0, trancedEnemy);
                    numEnemies += 1;
                }
                // if the enemy runs out of health they die and give exp and gold
                if (currEnemy.getHealth() <= 0) {
                    character.setGold(character.getGold() + currEnemy.getGoldDrop());
                    character.addExp(currEnemy.getXPdrop());
                    enemies.remove(0);
                    numEnemies += -1;
                }
                // character fights if no allies left
            } else if (numAllies == 1) {
                BasicEnemy currEnemy = enemies.get(0);
                currEnemy.attack(character);
                character.attack(currEnemy);
                // buildings attack current enemy
                for (Building building : buildings) {
                    Tower tower = (Tower) building;
                    tower.towerDamage(currEnemy);
                }
                character.setStunned(false);
                // enemy is either converted, killed or fights in the next round
                if (currEnemy.isConverted() == true) {
                    FightingAlly ally = new FightingAlly(character.getPosition());
                    ally.setRoundsInTrance(3);
                    allies.add(ally);
                    numAllies += 1;
                    trancedEnemy = enemies.remove(0);
                    numEnemies += -1;
                } else if (currEnemy.getHealth() <= 0) {
                    character.setGold(character.getGold() + currEnemy.getGoldDrop());
                    character.addExp(currEnemy.getXPdrop());
                    enemies.remove(0);
                    numEnemies += -1;
                }
                if (character.getHealth() <= 0) {
                    if (character.getRing() != null) {
                        character.setHealth(100);
                        TheOneRing ring = character.getRing();
                        character.equipRing(null);
                        ring.destroy();
                    } else if (character.getConfusedRing() != null) {
                        character.setHealth(100);
                        TheOneRing ring = character.getConfusedRing();
                        character.setConfusedRing(null);
                        ring.destroy();
                    } else {
                        numAllies += -1;
                    }
                }

            }
        }



        reduce_durability();
        // if character has health, he won the fight
        if (character.getHealth() > 0) {
            battleWon = true;
            return numAllies - 1;
        }
        return 0;
    }


    /**
     * Reduces the durability of all worn items after a battle.
     */
    public void reduce_durability() {
        if (character.getHelmet() != null) {
            character.getHelmet().setDurability(character.getHelmet().getDurability() - 1);
        }
        if (character.getWeapon() != null) {
            character.getWeapon().setDurability(character.getWeapon().getDurability() - 1);
        }
        if (character.getShield() != null) {
            character.getShield().setDurability(character.getShield().getDurability() - 1);
        }
        if (character.getArmour() != null) {
            character.getArmour().setDurability(character.getArmour().getDurability() - 1);
        }
        destroy_broken_items();
    }

    /**
     * Destroys all the items with 0 durability.
     */
    public void destroy_broken_items() {
        character.remove_broken_armour();
        character.remove_broken_helmet();
        character.remove_broken_shield();
        character.remove_broken_weapon();
    }

    // sort enemies by weakest to strongest
    public void sortEnemies(List<BasicEnemy> enemies) {
        Comparator<BasicEnemy> compareByHealth = (BasicEnemy e1, BasicEnemy e2) -> e1.getHealth().compareTo(e2.getHealth());
        Collections.sort(enemies, compareByHealth);
    }
    
    // sort allies weakest to strongest
    public void sortAllies(List<Ally> allies) {
        Comparator<Ally> compareByHealth = (Ally a1, Ally a2) -> a1.getHealthPoints().compareTo(a2.getHealthPoints());
        Collections.sort(allies, compareByHealth);
    }


}
