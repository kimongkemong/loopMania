package unsw.loopmania;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * represents the main character in the backend of the game world
 */
public class Character extends MovingEntity {

    private static final Integer STARTING_HEALTH = 100;
    private static final Integer STARTING_DMG = 10;
    private Integer exp = 0;
    private Integer bossesKilled = 0;
    private Integer gold = 0;

    private Helmet helmet;
    private Armour armour;
    private Weapon weapon;
    private Boolean confused;
    private Shield shield;
    private TheOneRing ring;
    private Weapon confusedWeapon;
    private Shield confusedShield;
    private TheOneRing confusedRing;
    private Random rand = new Random(System.currentTimeMillis());
    private StringProperty goldBind;

    /**
     * Makes a character and intialises them to have no equipped armour. The character
     * can equip armour and weapons found by killing enemies or buying from the shop.
     * @param position
     */
    public Character(PathPosition position){
        super(position);
        helmet = null;
        armour = null;
        weapon = null;
        shield = null;
        confusedShield = null;
        confusedWeapon = null;
        confusedRing = null;
        confused = false;
        ring = null;
        health = STARTING_HEALTH;
        this.setDamage(STARTING_DMG);
        this.setAttack(new CharacterBasicAttack());
        goldBind = new SimpleStringProperty(Integer.toString(gold));
    }

    public void setRand(int seed) {
        this.rand = new Random(seed);
    }




    public Helmet getHelmet() {
        return helmet;
    } 

    public Boolean getConfused() {
        return confused;
    }

    public void setConfused(Boolean confused) {
        this.confused = confused;
    }

    /**
     * Equips a helmet and unequips the currently equipped helmet.
     * @param helmet
     * @param inventory
     */
    public void equipHelmet(Helmet helmet, ArrayList<Item> inventory) {
        // unequips current helmet before putting new one on
        if (getHelmet() != null) {
            inventory.add(getHelmet());
        }
        this.helmet = helmet;
        inventory.remove(helmet);
    }

    /**
     * Equips a helmet and returns the currently equipped helmet.
     * @param helmet
     * @return
     */
    public Item equipHelmet(Helmet helmet)  {
        Item unequippedItem = getHelmet();
        this.helmet = helmet;
        return unequippedItem;

    }


    public void unequipHelmet(ArrayList<Item> inventory) {
        inventory.add(helmet);
        helmet = null;
    }

    /**
     * Removes helmet if it has 0 durability.
     */
    public void remove_broken_helmet() {
        if (helmet != null) {
            if (helmet.getDurability() == 0) {
                helmet.destroy();
                helmet = null;
            }
        }
    }

    /**
     * Removes armour if it has 0 durability.
     */
    public void remove_broken_armour() {
        if (armour != null) {
            if (armour.getDurability() == 0) {
                armour.destroy();
                armour = null;
            }
        }
    }

    /**
     * Removes a shield if 0 durability.
     */
    public void remove_broken_shield() {
        if (shield != null) {
            if (shield.getDurability() == 0) {
                shield.destroy();
                shield = null;
            }
        }
    }

    /**
     * Removes a weapon if 0 durability.
     */
    public void remove_broken_weapon() {
        if (weapon != null) {
            if (weapon.getDurability() == 0) {
                weapon.destroy();
                weapon = null;
            }
        }
    }

    public Weapon getConfusedWeapon() {
        return confusedWeapon;
    }

    public void setConfusedWeapon(Weapon confusedWeapon) {
        this.confusedWeapon = confusedWeapon;
    }

    public Shield getConfusedShield() {
        return confusedShield;
    }

    public void setConfusedShield(Shield confusedShield) {
        this.confusedShield = confusedShield;
    }

    public TheOneRing getConfusedRing() {
        return confusedRing;
    }

    public void setConfusedRing(TheOneRing confusedRing) {
        this.confusedRing = confusedRing;
    }

    public TheOneRing getRing() {
        return ring;
    }

    /**
     * Equips a ring and if the game mode is confusing, then it will either equip a
     * confusedShield or confusedWeapon.
     * @param ring
     */
    public void equipRing(TheOneRing ring) {
        if (confused && ring instanceof TheOneRing) {
            if (rand.nextInt(2) == 0) {
                confusedShield = new TreeStump(null, null);
            } else {
                confusedWeapon = new Anduril(null, null);
            }
        }
        this.ring = ring;
    }
    
    public Integer getBossesKilled() {
        return bossesKilled;
    }

    public void setBossesKilled(Integer bossesKilled) {
        this.bossesKilled = bossesKilled;
    }

    public Armour getArmour() {
        return armour;
    }
    /**
     * Equips armour and unequips the currently equipped armour.
     * @param armour
     * @param inventory
     */
    public void equipArmour(Armour armour, ArrayList<Item> inventory) {
        // unequips current armour before putting new one on
        if (getArmour() != null) {
            inventory.add(getArmour());
        }
        this.armour = armour;
        inventory.remove(armour);
    }

    /**
     * Equips armour and returns the currently equipped armour.
     * @param armour
     * returns unequipped item else null
     */
    public Item equipArmour(Armour armour) {
       Item unequippedItem = getArmour();
       this.armour = armour;
       return unequippedItem;
    }

    public void unequipArmour(ArrayList<Item> inventory) {
        inventory.add(armour);
        armour = null;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * Equips a weapon and if the game mode is confusing gamemode and the weapon equipped is Anduril
     * it will also equip either a confusedring or confusedshield.
     * @param weapon
     * @param inventory
     */
    public void equipWeapon(Weapon weapon, ArrayList<Item> inventory) {
        if (confused && weapon instanceof Anduril) {
            if (rand.nextInt(2) == 0) {
                confusedRing = new TheOneRing(null, null);
            } else {
                confusedShield = new TreeStump(null, null);
            }
        }
        // unequips current weapon before putting new one on
        if (getWeapon() != null) {
            inventory.add(getWeapon());
        }
        this.weapon = weapon;
        inventory.remove(weapon);
    }

    public Item equipWeapon(Weapon weapon)  {
        Item unequippedItem = getWeapon();
        this.weapon = weapon;
        return unequippedItem;

    }

    public void unequipWeapon(ArrayList<Item> inventory) {
        inventory.add(weapon);
        weapon = null;
    }

    public Shield getShield() {
        return shield;
    }

    /**
     * Equips a shield and if the game mode is confusing gamemode and the shield equiped
     * is TreeStump then it will also equip either a confusedring or confusedweapon
     * @param shield
     * @param inventory
     */
    public void equipShield(Shield shield, ArrayList<Item> inventory) {
        if (confused && shield instanceof TreeStump) {
            if (rand.nextInt(2) == 0) {
                confusedRing = new TheOneRing(null, null);
            } else {
                confusedWeapon = new Anduril(null, null);
            }
        }
        // unequips current shield before putting new one on
        if (getShield() != null) {
            inventory.add(getShield());
        }
        this.shield = shield;
        inventory.remove(shield);
    }
    /**
     * Equips a shield and returns the unequipped shield.
     * 
     */
    public Item equipShield(Shield shield)  {
        Item unequippedItem = getShield();
        this.shield = shield;
        return unequippedItem;
    }

    public void unequipShield(ArrayList<Item> inventory) {
        inventory.add(shield);
        shield = null;
    }

    public Integer getExp() {
        return exp;
    }

    public void addExp(Integer exp) {
        this.exp += exp;
    }

    /**
     * Returns the damage the character will deal with their weapons against the given enemy.
     * This takes into accounts special items and attack types.
     * @param enemy
     * @return
     */
    public int getDamage(BasicEnemy enemy) {
        if (confused && confusedWeapon != null) {
            return this.getDamage() + confusedWeapon.returnDamage(enemy);
        }
        if (weapon == null) {
            return this.getDamage();
        }
        return this.getDamage() + weapon.returnDamage(enemy);
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
        this.goldBind.set(Integer.toString(gold));
    }


    /**
     * Attacks the enemy based upon which weapon is being used as well as probability. Utilises an attack strategy.
     * @param e - given MovingEntity which is attacked by the character.
     */
    @Override
    public void attack(MovingEntity e) {
        if (this.isStunned()) {
            return;
        }
        if (getWeapon() instanceof Staff && rand.nextInt(5) == 0) {
            this.setAttack(new CharacterConvertAttack());
        } else {
            this.setAttack(new CharacterBasicAttack());
        }
         getAttack().attack(this, e);
    }

    /**
     * Changes the health of the character. If the health is decreased, the decrease will be affected
     * by the items that are equipped at the time. If health is increased, then it will setHealth as 
     * normal.
     * @param health - the new health of the character
     */
    public void setHealth(int health, BasicEnemy enemy) {
        int damage = getHealth() - health;
        if (damage > 0) {
            if (getArmour() != null) {
                damage = getArmour().reduceDamage(damage);
            }
            if (getHelmet() != null) {
                damage = getHelmet().reduceDamage(damage);
            }
            if (getShield() != null || getConfusedShield() != null) {
                if (getConfusedShield() != null) {
                    damage = getConfusedShield().reduceDamage(damage, enemy);
                } else {
                    damage = getShield().reduceDamage(damage, enemy);
                }
            }
            this.health = this.health - damage;
        } else {
            this.health = health;
        }
    }

    public StringProperty getGoldProperty() {
        return goldBind;
    }
}
