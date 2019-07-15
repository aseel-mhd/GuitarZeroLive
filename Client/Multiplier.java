import java.lang.Math.*;
/*
 * PlayMode.
 *
 * @version 1.00, February 2019.
 */
public class Multiplier {
        public static void main(String[] args){

            int streakCount = 1;
            int score = 1;
            boolean noteHit;
            int Multiplier=1;
            int x = 2;

            for (streakCount=1; streakCount<101; streakCount++){
                if (streakCount % 10 == 0){
                    Multiplier =Multiplier *2;
                    System.out.println(Multiplier + " Multiplier");
                }
                System.out.println(streakCount + " Streak");
            }


        }

    // all buttons enabled


}
