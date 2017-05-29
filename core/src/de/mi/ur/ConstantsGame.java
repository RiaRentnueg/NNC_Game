package de.mi.ur;

import de.mi.ur.states.PlayState;

/**
 * Created by Anna-Marie on 05.08.2016.
 */
public class ConstantsGame {


        public static final int DEFAULT_CAM_WIDTH = 240;
        public static final int DEFAULT_CAM_HEIGHT = 400;

        public static final int SCREEN_HEIGHT = 450;
        public static final int SCREEN_WIDTH = 800;

        public static final int GAME_OVER_OFFSET_X = 30;
        public static final int GAME_OVER_OFFSET_Y = 220;

        public static final int BOUNDS_OFFSET = -50;
        public static final int BACKGROUND_Y_POS = 80;
        public static final int SUN_Y_POS = 90;
        public static final int GROUND_Y_OFFSET = -30;

        //Nerd
        public static final int NERD_GRAVITY_DEFAULT = -30;
        public static final int NERD_MOVEMENT_DEFAULT = 100;
        public static final int NERD_POSITION_OFFSET = 80;
        public static final int NERD_X = 40;
        public static final int NERD_Y = 200;


        //for Obstacles
        public static final int TOTAL_NUM_OBSTACLES = 4;

        public static final int PIT_WIDTH = 52;
        public static final int PIT_BOUNDS_OFFSET = 0;

        public static final int WOMAN_Y = PlayState.ground.getHeight() + GROUND_Y_OFFSET;
        public static final int WOMAN_WIDTH = 34;



        //Score
        public static final int SCORE_OFFSET_Y =160;
        public static final int SCORE_OFFSET_X = -110;
        public static final int SCORE_HEARTS_OFFSET_X = 20;
        public static final int SCORE_HEARTS_OFFSET_Y = 140;


        //Question
        public static final int QUESTION_TOSOLVE_OFFSET = -115;
        public static final int QUESTION_OFFSET_Y = 120;
        public static final int QUESTION_POSSANS_1_OFFSET = -40;

        public static final int QUESTION_POSSANS_3_OFFSET = 40;
        public static final int QUESTION_POSSANS_4_OFFSET = 80;

        //Array-Positionen für Frage und Antwort
        public static final int QUESTION_POS = 0;
        public static final int RIGHT_ANSWER_POS = 1;
        public static final int POSS_ANSWER1_POS = 2;
        public static final int POSS_ANSWER2_POS = 3;
        public static final int POSS_ANSWER3_POS = 4;
        public static final int POSS_ANSWER4_POS = 5;


        //Weather
        public static final int WEATHER_SUNNY = 0;
        public static final int WEATHER_CLOUDY = 1;
        public static final int WEATHER_RAINY = 2;
        public static final int WEATHER_SNOWY = 3;

        // for Random-Generator
        public static final int PIT_TYPE = 0;
        public static final int WOMAN_TYPE = 1;

        //Heart-States
        public static final int HEARTSTATE_ALL_HEARTS_FULL = 4;
        public static final int HEARTSTATE_2_HEARTS = 1;
        public static final int HEARTSTATE_1_HEART = 2;
        public static final int HEARTSTATE_NO_HEART = 3;
        public static final int HEARTSTATE_OTHER = 5;

        //Phones
        public static final int PHONES_Y = 200;
        public static final int PHONE1_X = 400;
        public static final int PHONE2_X = 450;
        public static final int PHONE3_X = 500;
        public static final int PHONE4_X = 550;

        public static final int PHONE1_X_OUTSIDE_SCREEN = 0;
        public static final int PHONE2_X_OUTSIDE_SCREEN = 50;
        public static final int PHONE3_X_OUTSIDE_SCREEN = 100;
        public static final int PHONE4_X_OUTSIDE_SCREEN = 150;

        public static final int PHONE_Y_TO_ADD = 0;
        public static final int PHONE_X_TIMES_WIDTH = 4;

        //PlayState
        public static final int PHASE_DURATION = 15;

        //Difficulty
        public static final int SCORE_START = 50;
        public static final int SCORE_DIFFERENCE = 50;
        public static final int VELOCITY_ADDED = 20;

        public static final int MIN_DISTANCE = 10;
        public static final int MAX_DISTANCE_LONG = 200;
        public static final int MAX_DISTANCE_MEDIUM_LONG = 170;
        public static final int MAX_DISTANCE_MEDIUM_SHORT = 140;
        public static final int MAX_DISTANCE_SHORT = 110;


}
