package load_playlist;


import java.util.ArrayList;
import java.util.Arrays;

public class Playlist{

    public ArrayList<Song> songs;
    public String name;


    public Playlist(String name){
        songs = new ArrayList<>();
        this.name = name;
    }

    public Playlist(String name, Song[] songArray){
        this.name  = name;
        songs = new ArrayList<>(Arrays.asList(songArray));
    }

    public Playlist() {
        songs = new ArrayList<>();
    }

    public void addSong(Song aSong){
        songs.add(aSong);
    }


    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}