package load_playlist;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.logging.Logger;




public  class PlaylistCreator{
    public String Filename;
    public Song [] SongsArray;
    private static final Logger logger = Logger.getLogger(PlaylistCreator.class.getName());



    public Playlist CreatePlaylist(String playlistName, String fileName) {
        try {

            this.Filename = fileName;
            File SongNamesFile =  new File(Filename);
            
            // counts the song titles in the file & creates an array with the same size
            Scanner countScanner = new Scanner(SongNamesFile);
            int count = 0;
            while (countScanner.hasNextLine()) {
                count++;
                countScanner.nextLine();
                
            }
            SongsArray = new Song[count];
            countScanner.close();
            
            
            Scanner SongScanner = new Scanner(SongNamesFile);
            count = 0;
            String songName = null;
            while (SongScanner.hasNextLine()) {
                songName = SongScanner.nextLine();
                String songPath = PlaylistCreator.findAbsolutePath(songName);
                Song newSong = new Song(songName,
                songPath, new File(songPath));
                SongsArray[count] = newSong;
                count++;
            
                
            }
            SongScanner.close();
        } catch (FileNotFoundException e) {
            logger.warning("File " + Filename + " not found");
        }

        Playlist loadedPlaylist = new Playlist(playlistName, SongsArray);
		return loadedPlaylist;
    }


    // Searches a directory for a file based on its songNameInp
    // Borrowed code: https://stackoverflow.com/questions/15624226/java-search-for-files-in-a-directory
    public  static String findAbsolutePath(String songNameInp)
    {

        // Change System.getProperty("user.dir")+"\\src\\main\\resources\\tracks\\" to the directory the tracks are
        File songFile = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\tracks\\"+songNameInp);
        if (songFile.exists()) {
            return songFile.getAbsolutePath();
        } else {
        logger.warning("File" + songNameInp+ " not found. Make sure the file is in the same directory as playlist file\n\n");
        return "N/A";
    }
  
    }
}