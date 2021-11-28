package load_playlist;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;


public class Load_Playlist_Filechooser{

    public Map<String,String> ChooseFile() {
                // System.out.println("Current Dir: ./src/main/resources/SongTest");
        JFileChooser chooser = new JFileChooser("./src/main/resources/playlists");


        FileNameExtensionFilter mp3_Filter = new FileNameExtensionFilter("Text Files", "txt");
        Map<String, String> fileAttributesMap = new HashMap<>();
        chooser.addChoosableFileFilter(mp3_Filter);
        chooser.setFileFilter(mp3_Filter);


        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            fileAttributesMap.put("name", selectedFile.getName());
            fileAttributesMap.put("absolutePath", selectedFile.getAbsolutePath());
            return fileAttributesMap;
        }
        else
        {
            return fileAttributesMap;
        }

    }

    public static void main(String[] args) {
        Load_Playlist_Filechooser choose = new Load_Playlist_Filechooser();

        PlaylistCreator createPlaylist = new PlaylistCreator();
        Playlist newPlay = createPlaylist.CreatePlaylist(
                choose.ChooseFile().get("name"), choose.ChooseFile().get("absolutePath"));
    }
}
