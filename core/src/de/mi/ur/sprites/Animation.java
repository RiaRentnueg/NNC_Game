package de.mi.ur.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by maxiwindl on 31.07.16.
 */
public class Animation {

    /**
     * This class makes it possible that the nerd is "moving" instead of just sliding across the screen in a
     * steady position.
     */
    private Array<TextureRegion> frames;
    //this value tells how long a frame has to stay in view, until its switched to the next one
    private float maxFrameTime;
    //the time: how long has the animation been in the current frame
    private float currentFrameTime;
    //number of frames in the animation
    private int frameCount;
    //current frame we're in
    private int frame;

    /**
     * @param region:     all of the different frames combined in one texture
     * @param frameCount: of how many frames does the picture consists. How many states are there?
     * @param cycleTime:  how long does it take to go through the whole textureRegion
     */
    public Animation (TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth()/frameCount;
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(region, i*frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime/frameCount;
        frame = 0;

    }

    public void update (float dt) {
        currentFrameTime += dt;
        /**
         *  if the current time of the frame is greater than "allowed", it is switched to the
         *  next frame and the currentFrameTime is reset to 0.
         */

        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        /**
         * if we are through all the frames we start again at the beginning.
         */
        if (frame >= frameCount) {
            frame = 0;

        }
    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }
}
