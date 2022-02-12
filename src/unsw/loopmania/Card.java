package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.ActionCards.*;
import unsw.loopmania.Cards.*;

public abstract class Card extends StaticEntity implements CardStrategy{

    public Card(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
        /**
     * getting a random card after killing enemies
     * @param cardEntities
     * @return
     */
    public static Card loadCard(List<Card> cardEntities){
        Card[] cards = {
            new BarracksCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0)),
            new CampfireCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0)),
            new TowerCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0)),
            new TrapCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0)),
            new VampireCastleCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0)),
            new VillageCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0)),
            new ZombiePitCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0))
        };
        
        Card[] rareCards = {new RemovalistCard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0)), 
                            new Blue_wildcard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0)),
                            new Red_wildcard(new SimpleIntegerProperty(cardEntities.size()), new SimpleIntegerProperty(0))};

        Random rand = new Random();
        int upperBound = 100;
        int ran = rand.nextInt(upperBound);
        if (ran > 10) {
            return spawningCommonCard(cards, cardEntities);
        }
        return spawningRareCard(rareCards, cardEntities);
    }

    /**
     * spawning the orignal cards
     */
    public static Card spawningCommonCard(Card[] cards, List<Card> cardEntities) {
        Random rand = new Random();
        int ran = rand.nextInt(7);
        cardEntities.add(cards[ran]);
        return cards[ran];
    }

    /**
     * spawning extension card and its a rare card
     */
    public static Card spawningRareCard(Card[] rareCards, List<Card> cardEntities) {
        Random rand = new Random();
        int ran = rand.nextInt(3);
        cardEntities.add(rareCards[ran]);
        return rareCards[ran];
    }

    /**
     * getting a list of slots that is adjecent to ordered path
     * @param orderedPath
     * @return
     */
    public List<Pair<Integer, Integer>> getAdjacentToPath(List<Pair<Integer, Integer>> orderedPath) {
        List<Pair<Integer, Integer>> adjList = new ArrayList<>();
        //if adjacent and not a path and not already in adjList
        for (Pair<Integer, Integer> pathtiles: orderedPath) {
            for (Pair<Integer,Integer> tiles : adjacentTiles(pathtiles)) {
                if (!orderedPath.contains(tiles) && !tiles.equals(pathtiles) && !adjList.contains(tiles)) {
                    adjList.add(tiles);
                }
            }
        }
        return adjList;
    }

    /**
     * getting a list of adjacent tiles from the given pair or tile
     * @param path
     * @return
     */
    private List<Pair<Integer, Integer>> adjacentTiles(Pair<Integer,Integer> path) {
        List<Pair<Integer, Integer>> adjTTiles = new ArrayList<>();

        Pair <Integer, Integer> pair1 = new Pair <Integer, Integer> (path.getValue0() + 1, path.getValue1());
        Pair <Integer, Integer> pair2 = new Pair <Integer, Integer> (path.getValue0(), path.getValue1()+ 1);
        Pair <Integer, Integer> pair3 = new Pair <Integer, Integer> (path.getValue0() -1, path.getValue1());
        Pair <Integer, Integer> pair4 = new Pair <Integer, Integer> (path.getValue0(), path.getValue1() -1);
        adjTTiles.add(pair1);
        adjTTiles.add(pair2);
        adjTTiles.add(pair3);
        adjTTiles.add(pair4);
        
        return adjTTiles;
    }
}
