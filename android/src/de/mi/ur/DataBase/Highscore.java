package de.mi.ur.DataBase;

/**
 * Created by Anna-Marie on 09.08.2016.
 *
 * This class represents a Highscore from the game
 */
public class Highscore {
    private int rank;
    private int points;
    private String name;

    public Highscore(int rank, int points, String name ){
        this.rank = rank;
        this.points = points;
        this.name = name;
    }

    public int getRank(){
        return this.rank;
    }

    public int getPoints(){
        return this.points;
    }

    public String getName(){
        return this.name;
    }

    public void lowerRankByOne(){
        rank = rank +1;
    }

}
