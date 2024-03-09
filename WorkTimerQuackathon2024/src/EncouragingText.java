import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class EncouragingText {
    String[] messages = {
            "Stay focused and let your determination fuel your productivity.",
            "Every step forward, no matter how small, brings you closer to success.",
            "Believe in yourself and your ability to overcome any challenge.",
            "Embrace the power of consistency; small efforts every day lead to big results.",
            "You have the strength within you to conquer any task that comes your way.",
            "Set your goals high and don't stop until you reach them.",
            "Every setback is an opportunity to learn and grow stronger.",
            "Your hard work today is the foundation for your brighter tomorrow.",
            "Success is not final, failure is not fatal: it is the courage to continue that counts.",
            "You are capable of achieving more than you think; trust in your potential.",
            "Focus on progress, not perfection. Each step forward is a victory.",
            "Challenge yourself to push beyond your limits and unlock your full potential.",
            "Stay positive, work hard, and make it happen.",
            "Visualize your success and let that vision drive your actions.",
            "Remember, every accomplishment starts with the decision to try. Keep going!"
    };

    public EncouragingText(){

    }

    public String newMessage(){
        Random random = new Random();
        String message = messages[random.nextInt(0,14)];
        System.out.println(message);
        return message;
    }


}
