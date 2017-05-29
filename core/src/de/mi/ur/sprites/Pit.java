package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.mi.ur.ConstantsGame;
import de.mi.ur.states.PlayState;

/**
 * Created by maxiwindl on 01.08.16.
 */
public class Pit extends Obstacle {



    private Texture pit;
    private Rectangle bounds;
    private Vector2 pitPos;


    public Pit (float x) {
        super(x, 0, new Texture("pit_old.png"), ConstantsGame.PIT_TYPE);
        if (PlayState.sunny) {
            pit = new Texture("pit_old.png");
        } else {
            pit = new Texture("pit_bad_weather.png");
        }
        pitPos = new Vector2(x, 0);
        bounds = new Rectangle(pitPos.x, pitPos.y, pit.getWidth() + ConstantsGame.BOUNDS_OFFSET, pit.getHeight());


    }

    public Texture getPit () {
        return pit;
        //return super.getObstacle();
    }

    public Vector2 getPitPos() {

        //return super.getObstaclePos();
        return pitPos;
    }

    public Vector2 getObstaclePos() {
        return getPitPos();
    }

    public Texture getTexture() {
        return pit;
    }

    public boolean collides(Rectangle player){
        // return super.collides(player);
        return player.overlaps(bounds);
    }

    public void dispose() {
        // super.dispose();
        pit.dispose();
    }


    public void reposition(float x){
        //super.reposition(x);
        pitPos.set(x, 0);
        bounds.setPosition(pitPos.x, pitPos.y);

    }












}
