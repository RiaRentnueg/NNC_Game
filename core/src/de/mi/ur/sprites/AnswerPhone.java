package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import de.mi.ur.ConstantsGame;

/**
 * Created by maxiwindl on 05.09.16.
 */
public class AnswerPhone {


    private Vector2 position;


    private Rectangle bounds;
    private static boolean counted;


    private Animation phoneAnimation;
    private Animation phoneAnimation2;


    public boolean colliding;


    public AnswerPhone(int x, int y, Texture texture) {
        position = new Vector2(x, y);

        counted = true;
        phoneAnimation = new Animation(new TextureRegion(texture), 3, 0.8f);

        bounds = new Rectangle(x, y, texture.getWidth() / 3 + ConstantsGame.BOUNDS_OFFSET, texture.getHeight());
        colliding = false;
    }

    public boolean isCounted() {
        return counted;
    }

    public void setCounted() {
        counted = true;
    }

    public static void resetCounted() {
        counted = false;
    }

    public boolean collides(Rectangle player) {
        // return super.collides(player);

        return player.overlaps(bounds);
    }

    public void update(float dt) {
        phoneAnimation.update(dt);
        bounds.setPosition(position.x, position.y);
    }


    public Vector2 getPosition() {
        return position;
    }


    public float getX() {
        return position.x;
    }


    public float getY() {
        return position.y;
    }


    public TextureRegion getTexture() {
        return phoneAnimation.getFrame();
    }


}
