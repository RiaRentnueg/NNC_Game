package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import de.mi.ur.ConstantsGame;

/**
 * Created by maxiwindl on 23.08.16.
 */
public class Woman extends Obstacle {


    private Texture woman;
    private Random random;
    private Rectangle bounds;
    private Vector2 womanPos;


    public Woman(float x) {
        super(x, ConstantsGame.WOMAN_Y, new Texture("woman.png"), ConstantsGame.WOMAN_TYPE);

        woman = new Texture("woman.png");
        random = new Random();
        womanPos = new Vector2(x, ConstantsGame.WOMAN_Y);
        bounds = new Rectangle(womanPos.x, womanPos.y, woman.getWidth() + ConstantsGame.BOUNDS_OFFSET, woman.getHeight());


    }

    public Texture getWoman() {
        return woman;
    }

    public Vector2 getObstaclePos() {
        return getWomanPos();
    }

    public Texture getTexture() {
        return getWoman();
    }

    public Vector2 getWomanPos() {
        return womanPos;
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(bounds);
    }

    public void dispose() {
        woman.dispose();
    }


    public void reposition(float x) {
        womanPos.set(x, ConstantsGame.WOMAN_Y);
        bounds.setPosition(womanPos.x, womanPos.y);

    }


}
