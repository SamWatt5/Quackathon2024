import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;

public class WorkTimer
{
    public static void main(String[] args)
    {

        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                runStartProgram();
            }
        });

    }

    public static void runProgram(int workMin, int workSec, int breakMin, int breakSec)
    {

        WorkTimer workTimer = new WorkTimer();
        final boolean[] isWork = new boolean[1];
        isWork[0] = true;
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1300, 792);
        window.setTitle("Work Timer");
        window.getContentPane().setBackground(new Color(242, 233, 208));
        window.setIconImage(new ImageIcon(Objects.requireNonNull(WorkTimer.class.getResource("images/Icon.png"))).getImage());
        window.setLocationRelativeTo(null);


        final int[] workTime = {workTimer.setMinsToSecs(workMin, workSec) + 1};
        final int[] breakTime = {workTimer.setMinsToSecs(breakMin, breakSec) + 1};
        //GET USER INPUT FOR TIME
        CountdownClock countdown = new CountdownClock(workTime[0] -1); // Create countdown clock
         // Start countdown clock
        JLabel workState = new JLabel("Work");
        workState.setBounds(550,25,200,50); // Set parameters for timer
        workState.setFont(new Font("SansSerif",Font.BOLD,50));
        window.add(workState);

        JTextField minBox = new JTextField(countdown.getMins()); // Create Timer label
        minBox.setBounds(300,100,250,300); // Set parameters for timer
        minBox.setFont(new Font("SansSerif",Font.BOLD,200));
        minBox.setBackground(window.getBackground());
        minBox.setBorder(BorderFactory.createEmptyBorder());
        minBox.setHorizontalAlignment(SwingConstants.RIGHT);
        window.add(minBox); // Add Timer label

        JLabel colon = new JLabel(":");
        colon.setBounds(600,100,100,300); // Set parameters for timer
        colon.setFont(new Font("SansSerif",Font.BOLD,200));
        window.add(colon); // Add Timer label

        JTextField secBox = new JTextField(countdown.getSecs()); // Create Timer label
        secBox.setBounds(700,100,250,300); // Set parameters for timer
        secBox.setFont(new Font("SansSerif",Font.BOLD,200));
        secBox.setBackground(window.getBackground());
        secBox.setBorder(BorderFactory.createEmptyBorder());
        window.add(secBox); // Add Timer label


        JButton pauseplay = new JButton("Play");
        pauseplay.setBounds(525,420,200,50);
        pauseplay.setFont(new Font("SansSerif",Font.BOLD,40));

        JButton reset = new JButton("Reset");
        reset.setBounds(525,470,200,50);
        reset.setFont(new Font("SansSerif",Font.BOLD,40));

        JButton newTimer = new JButton("New");
        newTimer.setBounds(525,520,200,50);
        newTimer.setFont(new Font("SansSerif",Font.BOLD,40));

        JButton save = new JButton("Save");
        save.setBounds(425,570,200,50);
        save.setFont(new Font("SansSerif",Font.BOLD,40));

        JButton load = new JButton("Load");
        load.setBounds(625,570,200,50);
        load.setFont(new Font("SansSerif",Font.BOLD,40));

        window.add(pauseplay);
        window.add(reset);
        window.add(newTimer);
        window.add(save);
        window.add(load);

        JLabel message = new JLabel();
        message.setBounds(200,600,1100,100); // Set parameters for timer
        message.setFont(new Font("SansSerif",Font.BOLD,25));
        workTimer.createNewMessage(message);
        window.add(message); // Add Timer label

        ActionListener refreshClock = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workTimer.displayTime(window, countdown, minBox, secBox); // Create event
                if (countdown.timerLengthSecs == 0){

                    if (isWork[0]) {
                        countdown.setSecsLeft(workTime[0]);
                        isWork[0] = false;
                        workTimer.changeWorkState(workState.getText(), workState, countdown, workTime[0], breakTime[0]);
                    } else {
                        countdown.setSecsLeft(breakTime[0]);
                        isWork[0] = true;
                        workTimer.changeWorkState(workState.getText(), workState, countdown, workTime[0], breakTime[0]);
                    }

                }
            }
        };
        ActionListener createMessage = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workTimer.createNewMessage(message);
            }
        };
        Timer refreshClockTimer = new Timer(1000, refreshClock); // Run event every second
        Timer messageTimer = new Timer((60*1000), createMessage);
        messageTimer.start();
        //refreshClockTimer.start(); // Start refresh clock timer
        pauseplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdown.pauseTimer();
                if (pauseplay.getText().equals("Play")){
                    pauseplay.setText("Pause");
                    minBox.setEditable(false);
                    secBox.setEditable(false);

                    refreshClockTimer.start();

                    countdown.setMinsSecsToSecs(Integer.parseInt(minBox.getText()), Integer.parseInt(secBox.getText()));
                    countdown.startTimer();

                }else{
                    countdown.getTimer().cancel();

                    refreshClockTimer.stop();
                    pauseplay.setText("Play");
                    minBox.setEditable(true);
                    secBox.setEditable(true);
                }
                // Create event
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdown.setSecsLeft(workTime[0]);
                isWork[0] = true;
                workTimer.changeWorkState("Break", workState, countdown, workTime[0], breakTime[0]);


                //countdown.startTimer();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                workTimer.saveToFile(workTime[0], breakTime[0]);
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] array = new int[2];
                array = workTimer.loadFromFile();
                workTime[0] = array[0];
                breakTime[0] = array[1];
                System.out.println(workTime[0]);
                System.out.println(breakTime[0]);
                window.setVisible(false);
                javax.swing.SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        runProgram(Integer.parseInt(workTimer.getMins(workTime[0])), Integer.parseInt(workTimer.getSecs(workTime[0])), Integer.parseInt(workTimer.getMins(breakTime[0])), Integer.parseInt(workTimer.getSecs(breakTime[0])));
                    }
                });
                //countdown.getTimer().cancel();
                //workTimer.changeWorkState("Break",workState, countdown, workTime[0], breakTime[0]);


            }
        });

        newTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                javax.swing.SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        runStartProgram();
                    }
                });
                //countdown.getTimer().cancel();
                //workTimer.changeWorkState("Break",workState, countdown, workTime[0], breakTime[0]);


            }
        });

        window.setResizable(false);
        window.setLayout(null);
        window.setVisible(true);

    }

    public String getMins(int totalSecs){
        int timeLeft = totalSecs;
        int mins = timeLeft / 60;
        String minsToString = Integer.toString(mins);
        return minsToString;
    }
    public String getSecs(int totalSecs){
        int timeLeft = totalSecs;
        int secs = timeLeft % 60;
        String secsToString = Integer.toString(secs);
        int length = Integer.toString(secs).length();
        if (length == 1){
            secsToString = "0" + secs;
        }

        return secsToString;
    }

    /*public static void menu(JFrame window){
        JButton testButton = new JButton("CLICK ME");
        testButton.setBounds(100, 100, 100, 100);
        window.add(testButton);
    }*/

    public void displayTime(JFrame window, CountdownClock countdown, JTextField minBox, JTextField secBox){
        //System.out.println("TEST: " + countdown.getTimeInMinsSecs());
        minBox.setText(countdown.getMins());
        secBox.setText(countdown.getSecs());
        //timerLabel.setText("Hello");
    }

    public void createNewMessage(JLabel message){
        EncouragingText texts = new EncouragingText();
        message.setText(texts.newMessage());
    }

    public void changeWorkState(String workText, JLabel workStateLabel, CountdownClock countdown, int workTime, int breakTime){
        if (workText.equals("Work")){

            workStateLabel.setText("Break");
            countdown.getTimer().cancel();
            countdown.setSecsLeft(breakTime);
            countdown.startTimer();

        }else{
            workStateLabel.setText("Work");
            countdown.getTimer().cancel();
            countdown.setSecsLeft(workTime);
            countdown.startTimer();

        }
    }
    public static void runStartProgram(){
        JFrame inputWindow = new JFrame();
        inputWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputWindow.setSize(260, 200);
        inputWindow.setTitle("Work Timer");
        inputWindow.getContentPane().setBackground(new Color(242, 233, 208));
        inputWindow.setIconImage(new ImageIcon(Objects.requireNonNull(WorkTimer.class.getResource("images/Icon.png"))).getImage());
        inputWindow.setLocationRelativeTo(null);

        JLabel workText = new JLabel("<html>Work Time<br>(Mins:Secs)</html>");
        workText.setBounds(30, 20, 130, 40);
        inputWindow.add(workText);

        JTextField workMin = new JTextField();
        workMin.setBounds(20,60,40,40);
        inputWindow.add(workMin);

        JLabel colon1 = new JLabel(":");
        colon1.setBounds(70, 60, 40, 40);
        inputWindow.add(colon1);

        JTextField workSec = new JTextField();
        workSec.setBounds(80,60,40,40);
        inputWindow.add(workSec);


        JLabel breakText = new JLabel("<html>Break Time<br>(Mins:Secs)</html>");
        breakText.setBounds(160, 20, 130, 40);
        inputWindow.add(breakText);

        JTextField breakMin = new JTextField();
        breakMin.setBounds(140,60,40,40);
        inputWindow.add(breakMin);

        JLabel colon2 = new JLabel(":");
        colon2.setBounds(190, 60, 40, 40);
        inputWindow.add(colon2);

        JTextField breakSec = new JTextField();
        breakSec.setBounds(200,60,40,40);
        inputWindow.add(breakSec);

        JButton enterButton = new JButton("ENTER");
        enterButton.setBounds(60, 120, 130, 30);
        inputWindow.add(enterButton);
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputWindow.setVisible(false);
                javax.swing.SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        runProgram(Integer.parseInt(workMin.getText()), Integer.parseInt(workSec.getText()), Integer.parseInt(breakMin.getText()), Integer.parseInt(breakSec.getText()));
                    }
                });
            }
        });

        inputWindow.setResizable(false);
        inputWindow.setLayout(null);
        inputWindow.setVisible(true);

    }
    public int setMinsToSecs(int mins, int secs){
        int seconds = (mins*60) + secs;
        return seconds;
    }

    public void saveToFile(int workTime, int breakTime)
    {
        FileOutputStream outputStream = null;
        PrintWriter printWriter = null;

        try
        {
            outputStream = new FileOutputStream("SavedTimes.txt");
            printWriter = new PrintWriter(outputStream);

            printWriter.println(workTime);
            printWriter.println(breakTime);
        }
        catch (IOException e)
        {
            System.out.println("There was a problem opening or writing to the file");
        }
        finally
        {
            if (printWriter != null)
            {
                printWriter.close();
            }
        }
    }

    public int[] loadFromFile()
    {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String nextLine;
        int[] array = new int[2];

        try
        {
            fileReader = new FileReader("SavedTimes.txt");
            bufferedReader = new BufferedReader(fileReader);

            nextLine = bufferedReader.readLine();
            array[0] = Integer.parseInt(nextLine);
            nextLine = bufferedReader.readLine();
            array[1] = Integer.parseInt(nextLine);

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Sorry, you file was not found");
        }
        catch (IOException e)
        {
            System.out.println("There is a problem opening or reading from the file");
        }
        finally
        {
            if (bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                    System.out.println("File was not properly opened.");
                }
            }
        }
        return array;
    }


}