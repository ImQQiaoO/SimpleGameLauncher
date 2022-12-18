package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    static Process process;
    static long totalPlayTime = 0;
    static ArrayList<String> playedGameList = new ArrayList<>();
//    static int chooseCounter = 0; //第二个页面choose按键（调用文件选择器）的计数器
    static String finChoice = "";

    public static void main(String[] args) throws InterruptedException {

        final String[] path = {""};
        JFrame jFrame = new JFrame("VillvCotton's Pirate Games Launcher");
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);
        jFrame.setSize(508, 252);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        JLabel textLabel = new JLabel("Please Select Your Game:");
        textLabel.setBounds(175, 50, 150, 50);
        JButton jButton = new JButton("Select");
        JButton jButtonGame = new JButton("Start");
        jButton.setBounds(94, 125, 100, 50);
        jButtonGame.setBounds(300, 125, 100, 50);
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        textPanel.add(textLabel);
        buttonPanel.add(jButton);
        buttonPanel.add(jButtonGame);
        jFrame.add(textLabel);
        jFrame.add(jButton);
        jFrame.add(jButtonGame);
        JLabel gameName = new JLabel("You Have Selected: ...");
        gameName.setBounds(175, 70, 300, 50);
        textPanel.add(gameName);
        jFrame.add(gameName);
        jButton.addActionListener(new ActionListener() { //selected button
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrameSelecting = new JFrame("Please Select Your Game");
                jFrameSelecting.setLocationRelativeTo(null);
                jFrameSelecting.setLayout(null);
                jFrameSelecting.setSize(380, 252);
                jFrameSelecting.setVisible(true);
                jFrameSelecting.setResizable(false);
                JButton chooseButton = new JButton("Choose");
                JButton confirmButton = new JButton("Confirm");
                JButton deleteButton = new JButton("Delete");
                JLabel choosingTips = new JLabel("If you have ever opened a game, please select it below.");
                JComboBox<String> gameList = new JComboBox<>();
                choosingTips.setBounds(30, 30, 400, 50);
                chooseButton.setBounds(75, 125, 85, 50);
                confirmButton.setBounds(207, 125, 85, 50);
                gameList.setBounds(80, 80, 130, 25);
                deleteButton.setBounds(210, 80, 75, 25);
                JPanel choosingTextPanel = new JPanel();
                JPanel choosingButtonPanel = new JPanel();
                JPanel choosingListPanel = new JPanel();
                choosingTextPanel.add(choosingTips);
                choosingButtonPanel.add(chooseButton);
                choosingButtonPanel.add(confirmButton);
                choosingButtonPanel.add(deleteButton);
                choosingListPanel.add(gameList);
                jFrameSelecting.add(choosingTips);
                jFrameSelecting.add(chooseButton);
                jFrameSelecting.add(confirmButton);
                jFrameSelecting.add(deleteButton);
                jFrameSelecting.add(gameList);
                /*TODO: Add Here! */
                String basePath = "StatisticsOfPiratedGameTime";
                String[] list = new File(basePath).list();
                int cnt = 0;
                int cntListFinder = 0;
                for (int i = 0; i < Objects.requireNonNull(list).length; i++) {
                    //创建_playedGameList_.txt文件存储已经玩过的游戏列表
                    if (list[i].equals("_playedGameList_.txt")) {
                        cntListFinder++;
                    }
                }
                if (cntListFinder == 0) {
                    String fileName = "StatisticsOfPiratedGameTime\\_playedGameList_.txt";
                    Path path = Paths.get(fileName);
                    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                        bufferedWriter.write("");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                ArrayList<String> playedGameList0 = new ArrayList<>();
                FileInputStream FIS = null;
                try {
                    FIS = new FileInputStream("StatisticsOfPiratedGameTime\\_playedGameList_.txt");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                InputStreamReader ISR = new InputStreamReader(FIS);
                BufferedReader BR = new BufferedReader(ISR);
                String readGameListLine;
                while (true) {
                    try {
                        if ((readGameListLine = BR.readLine()) != null) {
                            playedGameList0.add(readGameListLine.substring(readGameListLine.lastIndexOf("\\") + 1));
                        }else {
                            break;
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                for (String s : playedGameList0) {
                    System.out.println(s);
                    gameList.addItem(s);
                }

                chooseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//                        chooseCounter ++;
                        JFileChooser fileChooser = new JFileChooser();
                        int dialog = fileChooser.showOpenDialog(null);
                        if (dialog == JFileChooser.APPROVE_OPTION) {
                            // Get the path of the selected file
                            path[0] = fileChooser.getSelectedFile().getAbsolutePath();
                            System.out.println(path[0]);
                            String selectedGame = path[0].substring(path[0].lastIndexOf("\\") + 1);
                            System.out.println(selectedGame);
                            //列出当前项目文件夹中的所有文件，与读取到的游戏名进行对比，如果发现项目文件夹不存在以此游戏命名的txt文件，
                            //则创建此游戏的文件夹，并将游戏时长置为0
                            String basePath = "StatisticsOfPiratedGameTime";
                            String[] list = new File(basePath).list();
                            int cnt = 0;
                            int cntListFinder = 0;
                            for (int i = 0; i < Objects.requireNonNull(list).length; i++) {
                                if (list[i].equals(selectedGame + ".txt")) {
                                    cnt++;
                                }

                            }
                            if (cnt == 0) {
                                String fileName = "StatisticsOfPiratedGameTime\\" + selectedGame + ".txt";
                                Path path = Paths.get(fileName);
                                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                                    bufferedWriter.write("0");
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
//TODO:写好列表文件！
                            FileInputStream fileInputStream = null;
                            try {
                                fileInputStream = new FileInputStream("StatisticsOfPiratedGameTime\\_playedGameList_.txt");
                            } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            String strLine = "";//应为文件读取的每一行的内容
                            int lineCnt = 0;
                            int cntNotSame = 0;
                            while (true) {
                                try {
                                    strLine = bufferedReader.readLine();
                                    lineCnt ++;
//                                    System.out.println("read " + strLine + " 0");
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if ((strLine) == null) {
                                    System.out.println("列表内容为空");
                                    String fileName = "StatisticsOfPiratedGameTime\\_playedGameList_.txt";
                                    Path path = Paths.get(fileName);
                                    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                                        bufferedWriter.write(fileChooser.getSelectedFile().getAbsolutePath() + "\n");
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    break;//
                                    //todo
                                }
                                if (strLine.equals(fileChooser.getSelectedFile().getAbsolutePath())) {//
                                    System.out.println("列表存在相同内容");
                                    break;
                                }

                            }
                            FileInputStream FIS = null;
                            try {
                                FIS = new FileInputStream("StatisticsOfPiratedGameTime\\_playedGameList_.txt");
                            } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                            InputStreamReader ISR = new InputStreamReader(FIS);
                            BufferedReader BR = new BufferedReader(ISR);
                            String readGameListLine;
                            gameList.removeAllItems();
                            playedGameList.clear();
                            while (true) {
                                try {
                                    if ((readGameListLine = BR.readLine()) != null) {
                                        playedGameList.add(readGameListLine.substring(readGameListLine.lastIndexOf("\\") + 1));
                                    } else {
                                        break;
                                    }
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                            for (String s : playedGameList) {
                                System.out.println(s);
                                gameList.addItem(s);//todo
                            }
                        }
                }
            });


               confirmButton.addActionListener(new ActionListener() {

                   @Override
                   public void actionPerformed(ActionEvent e) {
                       jFrameSelecting.dispose();
                       ArrayList <String> finPlayedGameList = new ArrayList<>();
                       System.out.println(gameList.getSelectedIndex());
                       FileInputStream FIS = null;
                       try {
                           FIS = new FileInputStream("StatisticsOfPiratedGameTime\\_playedGameList_.txt");
                       } catch (FileNotFoundException ex) {
                           throw new RuntimeException(ex);
                       }
                       InputStreamReader ISR = new InputStreamReader(FIS);
                       BufferedReader BR = new BufferedReader(ISR);
                       String readGameListLine;
                       while (true) {
                           try {
                               if ((readGameListLine = BR.readLine()) != null) {
                                   finPlayedGameList.add(readGameListLine);
                               } else {
                                   break;
                               }
                           } catch (IOException ex) {
                               throw new RuntimeException(ex);
                           }
                       }
                       finChoice = finPlayedGameList.get(gameList.getSelectedIndex());
                       System.out.println(finChoice);
                       gameName.setText("You Have Selected: " + finChoice);

                   }
               });

               ArrayList<String> deletedList = new ArrayList<>();
               deleteButton.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       FileInputStream FIS = null;
                       try {
                           FIS = new FileInputStream("StatisticsOfPiratedGameTime\\_playedGameList_.txt");
                       } catch (FileNotFoundException ex) {
                           throw new RuntimeException(ex);
                       }
                       InputStreamReader ISR = new InputStreamReader(FIS);
                       BufferedReader BR = new BufferedReader(ISR);
                       String readGameListLine;
                       deletedList.clear();
                       while (true) {
                           try {
                               if ((readGameListLine = BR.readLine()) != null) {
                                   deletedList.add(readGameListLine);
                               } else {
                                   break;
                               }
                           } catch (IOException ex) {
                               throw new RuntimeException(ex);
                           }
                       }
                       deletedList.remove(gameList.getSelectedIndex());
                       gameList.removeItem(gameList.getSelectedIndex());
                       gameList.removeItemAt(gameList.getSelectedIndex());
                       String fileName = "StatisticsOfPiratedGameTime\\_playedGameList_.txt";
                       Path path = Paths.get(fileName);
                       try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                           bufferedWriter.write("");
                       } catch (IOException ex) {
                           throw new RuntimeException(ex);
                       }
                       System.out.println(deletedList.size());
                       for (int i = 0; i < deletedList.size(); i++) {
                            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path,StandardOpenOption.APPEND)) {
                                bufferedWriter.write(deletedList.get(i) + "\n");
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                       }
                       System.out.println(deletedList.size());
                   }
               });
        }
    });

        jButtonGame.addActionListener(new ActionListener() {//start button
        @Override
        public void actionPerformed (ActionEvent e){
            jFrame.dispose();
            try {

//                process = Runtime.getRuntime().exec("cmd /c "+ finChoice.charAt(0) + ": && cd " +
//                        finChoice.substring(0, finChoice.lastIndexOf("\\")) + " && start " + finChoice);
//                Process p = Runtime.getRuntime().exec(finChoice);
                File f = new File(finChoice.substring(0, finChoice.lastIndexOf("\\")));
                process = Runtime.getRuntime().exec(finChoice,null,f);

//                System.out.println(process.info());
//                System.out.println(p.info());
                long startTime = System.currentTimeMillis();
                long endTime = startTime;
                //调用应用进程
                boolean shut = process.isAlive();
                while (shut) {
                    shut = process.isAlive();
                    Thread.sleep(10000);
                    if (!shut) {
                        endTime = System.currentTimeMillis();
                        long playTimeMs = endTime - startTime;
                        String playTime = String.valueOf(playTimeMs);
                        float playTimeMin = Float.parseFloat(playTime) / 60000;

                        int m = finChoice.lastIndexOf("\\");
                        String nameOfGame = finChoice.substring(m + 1);
                        FileInputStream fileInputStream = new FileInputStream("StatisticsOfPiratedGameTime\\" + nameOfGame + ".txt");
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String strLine;
                        long lastTime = 0;
                        while ((strLine = bufferedReader.readLine()) != null) {
                            lastTime = Long.parseLong(strLine);
                        }

                        String fileName = "StatisticsOfPiratedGameTime\\" + nameOfGame + ".txt";
                        Path path = Paths.get(fileName);

                        totalPlayTime = lastTime + playTimeMs;
                        float totalPlayTimeMin = Float.parseFloat(String.valueOf(totalPlayTime)) / 60000;

                        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                            bufferedWriter.write(String.valueOf(totalPlayTime));
                        }

                        JOptionPane.showMessageDialog(null,
                                "You played for " + playTimeMin + " Minutes This Time.\n" + "You played for " + totalPlayTimeMin +
                                        " Minutes In Total.\n",
                                "Play Happily!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                System.out.println(endTime - startTime);
                System.out.println(totalPlayTime);
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        }
    });
}
}