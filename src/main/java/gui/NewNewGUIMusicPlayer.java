package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javazoom.jl.player.Player;
import load_playlist.Playlist;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;


public class NewNewGUIMusicPlayer extends javax.swing.JFrame implements ActionListener {

    //Creating FileChooser for choosing the music mp3 file
    JFileChooser fileChooser;
    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;
    File[] myFiles = {};
    int songCounter = 0;
    JList<String> listOfSongs = new JList<>();
    DefaultListModel<String> songListModel = new DefaultListModel<>();
    Player player;
    Thread playThread, resumeThread, restartThread;

    private Playlist loadedPlaylist;

    private javax.swing.JButton StopButton;
    private javax.swing.JButton PlayPauseButton;
    private javax.swing.JButton PickSongButton;
    private javax.swing.JButton startOfThatSongButton;
    private javax.swing.JButton goToPlaylistHubButton;
    private javax.swing.JButton previousSongButton;
    private javax.swing.JButton nextSongButton;
    private javax.swing.JLabel TitleOfTheSongLabel;
    // End of variables declaration


    volatile boolean songStarted = false;
    volatile boolean songPlaying = false;
    volatile long totalLength, pauseLength;

    public NewNewGUIMusicPlayer() {
        this.loadedPlaylist = new Playlist();
        initComponents();
        this.setResizable(true);

        addActionEvents();
    }

    private void initComponents() {

        JLabel radioPlayerLabel = new JLabel();
        JLabel artistLabel = new JLabel();
        JLabel titleLabel = new JLabel();
        JPanel allButtonsPanel = new JPanel();
        JLabel remainingTimeLabel = new JLabel();
        JLabel artistNameLabel = new JLabel();
        JLabel currentPlaylistLabel = new JLabel();
        JLabel cogitoErgoScrumLabel = new JLabel();
        JLabel smileyFaceLabel = new JLabel();
        previousSongButton = new JButton();
        nextSongButton = new JButton();
        PlayPauseButton = new JButton();
        StopButton = new JButton();
        startOfThatSongButton = new JButton();
        TitleOfTheSongLabel = new JLabel();
        PickSongButton = new JButton();
        goToPlaylistHubButton = new JButton();
        JProgressBar jProgressBar1 = new JProgressBar();
        JScrollPane jScrollPane1 = new JScrollPane();



        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        radioPlayerLabel.setFont(new Font("Times New Roman", Font.BOLD, 22)); // NOI18N
        radioPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        radioPlayerLabel.setText("Radio Player");
        radioPlayerLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        radioPlayerLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        artistLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16)); // NOI18N
        artistLabel.setText("Artist");

        titleLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16)); // NOI18N
        titleLabel.setText("Title");

        allButtonsPanel.setBorder(BorderFactory.createTitledBorder(""));

        previousSongButton.setText("<<");

        startOfThatSongButton.setText("<");

        PlayPauseButton.setText("▶");

        StopButton.setText("▢");

        jProgressBar1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        jProgressBar1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jProgressBar1.setDoubleBuffered(true);

        remainingTimeLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18)); // NOI18N
        remainingTimeLabel.setText("Remaining time");
        remainingTimeLabel.setHorizontalTextPosition(SwingConstants.CENTER);

        nextSongButton.setText(">>");

        GroupLayout AllButtonsPanelLayout = new GroupLayout(allButtonsPanel);
        allButtonsPanel.setLayout(AllButtonsPanelLayout);
        AllButtonsPanelLayout.setHorizontalGroup(AllButtonsPanelLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(AllButtonsPanelLayout.createSequentialGroup().addContainerGap(13, Short.MAX_VALUE)
                        .addGroup(
                        AllButtonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                                GroupLayout.Alignment.TRAILING,
                                AllButtonsPanelLayout.createSequentialGroup().addGroup(AllButtonsPanelLayout
                                        .createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jProgressBar1, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(GroupLayout.Alignment.TRAILING, AllButtonsPanelLayout
                                                .createSequentialGroup().addComponent(previousSongButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(startOfThatSongButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(PlayPauseButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(StopButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(nextSongButton)))
                                        .addGap(13, 13, 13))
                                .addGroup(GroupLayout.Alignment.TRAILING,
                                        AllButtonsPanelLayout.createSequentialGroup().addComponent(remainingTimeLabel)
                                                .addGap(80, 80, 80)))));
        AllButtonsPanelLayout.setVerticalGroup(AllButtonsPanelLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(AllButtonsPanelLayout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(AllButtonsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(previousSongButton).addComponent(startOfThatSongButton)
                                .addComponent(PlayPauseButton).addComponent(StopButton).addComponent(nextSongButton))
                        .addGap(23, 23, 23)
                        .addComponent(jProgressBar1, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remainingTimeLabel)));


        artistNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // NOI18N
        artistNameLabel.setText("ArtistName1");

        TitleOfTheSongLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // NOI18N
        TitleOfTheSongLabel.setText("No song selected.");


        goToPlaylistHubButton.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // NOI18N
        goToPlaylistHubButton.setText("Go to Playlist hub");

        listOfSongs.setModel(songListModel);
        jScrollPane1.setViewportView(listOfSongs);

        currentPlaylistLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // NOI18N
        currentPlaylistLabel.setText("Current Playlist");

        cogitoErgoScrumLabel.setFont(new Font("Times New Roman", Font.BOLD, 24)); // NOI18N
        cogitoErgoScrumLabel.setText("Cogito Ergo Scrum");

        smileyFaceLabel.setFont(new Font("Times New Roman", Font.PLAIN, 48)); // NOI18N
        smileyFaceLabel.setText("☺");

        PickSongButton.setFont(new Font("Times New Roman", Font.PLAIN, 14)); // NOI18N
        PickSongButton.setText("Pick a song");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(artistLabel).addComponent(titleLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(TitleOfTheSongLabel, 50, 200,
                                                250)
                                        .addComponent(artistNameLabel, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        ))
                        .addGroup(layout.createSequentialGroup().addComponent(goToPlaylistHubButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PickSongButton))
                        .addComponent(cogitoErgoScrumLabel).addComponent(radioPlayerLabel)
                        .addGroup(layout.createSequentialGroup().addGap(39, 39, 39)
                                .addComponent(smileyFaceLabel)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(allButtonsPanel, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1))
                                .addGap(28, 28, 28))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(currentPlaylistLabel).addGap(128, 128, 128)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(cogitoErgoScrumLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioPlayerLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(smileyFaceLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(goToPlaylistHubButton).addComponent(PickSongButton))
                                .addGap(18, 18, 18))
                        .addGroup(layout.createSequentialGroup().addGap(32, 32, 32)
                                .addComponent(currentPlaylistLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 99,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(artistLabel).addComponent(artistNameLabel))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(titleLabel).addComponent(TitleOfTheSongLabel))
                                        .addGap(32, 32, 32)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                ))
                                .addComponent(allButtonsPanel, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap()));

        pack();
    }


    public void addActionEvents() {
        //registering action listener to buttons
        PickSongButton.addActionListener(this);
        PlayPauseButton.addActionListener(this);
        StopButton.addActionListener(this);
        startOfThatSongButton.addActionListener(this);
        goToPlaylistHubButton.addActionListener(this);
        nextSongButton.addActionListener(this);
        previousSongButton.addActionListener(this);
    }

    public void stop() {
        //code for stop button
            if (player != null) {
                player.close();
                TitleOfTheSongLabel.setText("");
                PlayPauseButton.setText("▶");
                songStarted = false;
                songPlaying = false;
                songCounter = -1;
                songListModel.removeAllElements();
            }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(PickSongButton)) {
            fileChooser = new JFileChooser();
            File currentDir = new File("./src/main/resources");
            fileChooser.setCurrentDirectory(currentDir);
            fileChooser.setDialogTitle("Select Mp3");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
            if (fileChooser.showOpenDialog(PickSongButton) == JFileChooser.APPROVE_OPTION) {

                myFiles = fileChooser.getSelectedFiles();


                if (myFiles[0].isDirectory()) {
                    fileChooser.setCurrentDirectory(new File(myFiles[0].getPath()));
                    System.out.println(fileChooser.getCurrentDirectory().getAbsolutePath());
                    myFiles = fileChooser.getCurrentDirectory().listFiles();
                }

                songListModel.removeAllElements();
                for (File file : myFiles) {
                    System.out.println(file.getName());
                    songListModel.addElement(file.getName());
                }
                
                songCounter = 0;

                TitleOfTheSongLabel.setText(myFiles[songCounter].getName());
            }
        }
        if (e.getSource().equals(PlayPauseButton)) {

            if (!songStarted){
                try {
                    //starting play thread
                    if (myFiles[songCounter].exists()) {
                        playThread = new Thread(runnablePlay);
                        playThread.start();
                        TitleOfTheSongLabel.setText(myFiles[songCounter].getName());
                        PlayPauseButton.setText("⏸");
                        songStarted=true;
                        songPlaying=true;

                    } else {
                        TitleOfTheSongLabel.setText("No File was selected!");
                        System.out.println(loadedPlaylist.getSongs().get(0));
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    TitleOfTheSongLabel.setText("No File was selected!");
                }
            }
            else {
                if (songPlaying){
                    if (player != null && myFiles[songCounter].exists()) {
                        try {
                            pauseLength = fileInputStream.available();
                            player.close();
                            songPlaying = false;
                            PlayPauseButton.setText("▶");
                            songCounter--;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                else{
                    //starting resume thread
                    if (myFiles[songCounter].exists()) {
                        resumeThread = new Thread(runnableResume);
                        resumeThread.start();
                        songPlaying=true;
                        PlayPauseButton.setText("⏸");

                    } else {
                        TitleOfTheSongLabel.setText("No File was selected!");
                    }
                }
            }

            System.out.print("SongStarted: ");
            System.out.println(songStarted);
            System.out.print("SongPlaying: ");
            System.out.println(songPlaying);
        }
        
        if (e.getSource().equals(StopButton)) {
            stop();
        }

        if (e.getSource().equals(startOfThatSongButton)) {
            if (!myFiles[songCounter].getName().equals(" ")) {
                songCounter--;
                player.close();
                songPlaying = false;
                restartThread = new Thread(runnableRestart);
                restartThread.start();
                songPlaying=true;

            } else {
                TitleOfTheSongLabel.setText("No File was selected!");
            }
        }

        // Code for "Go To PLaylist Hub Button"
        if (e.getSource().equals(goToPlaylistHubButton)){
            PlaylistGUI playlistGui = new PlaylistGUI(this);
            playlistGui.display();
        }

        // Code for "Next Song Button"
        if(e.getSource().equals(nextSongButton)){

                if (!myFiles[songCounter].getName().equals(" ")) {
                    player.close();
                    songCounter++;
                    playThread = new Thread(runnablePlay);
                    playThread.start();
                    TitleOfTheSongLabel.setText(myFiles[songCounter].getName());
                    PlayPauseButton.setText("⏸");
                    songStarted=true;
                    songPlaying=true;
                } else {
                    TitleOfTheSongLabel.setText("No File was selected!");
                }
        }

        // Code for "Previous Song Button"
        if(e.getSource().equals(previousSongButton)){

            if (!myFiles[songCounter].getName().equals(" ")) {
                player.close();
                songCounter--;
                playThread = new Thread(runnablePlay);
                playThread.start();
                TitleOfTheSongLabel.setText(myFiles[songCounter].getName());
                PlayPauseButton.setText("⏸");
                songStarted=true;
                songPlaying=true;
            } else {
                TitleOfTheSongLabel.setText("No File was selected!");
            }

        }

    }


    public void setLoadedPlaylist(Playlist aPlaylist){
        this.loadedPlaylist = aPlaylist;
    }

    Runnable runnablePlay = new Runnable() {
        @Override
        public void run() {
            while (songPlaying) {
                try {
                    //code for play button
                    TitleOfTheSongLabel.setText(myFiles[songCounter].getName());
                    fileInputStream = new FileInputStream(myFiles[songCounter]);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    player = new Player(bufferedInputStream);
                    totalLength = fileInputStream.available();
                    player.play();//starting music
                    songCounter++;
                } catch (ArrayIndexOutOfBoundsException e) {
                    stop();
                    break;
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }
        }
    };

    Runnable runnableResume = new Runnable() {
        @Override
        public void run() {
        while (songPlaying) {
                try {
                    //code for play button
                    TitleOfTheSongLabel.setText(myFiles[songCounter].getName());
                    fileInputStream = new FileInputStream(myFiles[songCounter]);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    player = new Player(bufferedInputStream);
                    fileInputStream.skip(totalLength - pauseLength);
                    totalLength = fileInputStream.available();
                    player.play();//starting music
                    songCounter++;
                } catch (ArrayIndexOutOfBoundsException e) {
                    stop();
                    break;
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }
        }
    };

    Runnable runnableRestart = new Runnable() {
        @Override
        public void run() {
            while (songPlaying) {
                try {
                    //code for play button
                    TitleOfTheSongLabel.setText(myFiles[songCounter].getName());
                    fileInputStream = new FileInputStream(myFiles[songCounter]);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    player = new Player(bufferedInputStream);
                    totalLength = fileInputStream.available();
                    player.play();//starting music
                    songCounter++;
                } catch (ArrayIndexOutOfBoundsException e) {
                    stop();
                    break;
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }
        }
    };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewNewGUIMusicPlayer.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new NewNewGUIMusicPlayer().setVisible(true));
    }
}