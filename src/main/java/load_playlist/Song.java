package load_playlist;
import java.io.File;
public class Song {
    public String Name;
    public String FilePath;
    public File SongFile;


    public Song(String name, String filepath, File songfile){
       this.Name = name;
       this.FilePath = filepath;
       this.SongFile = songfile;
    }
    public void setName(String name) {
      this.Name = name;
   }
   public String getName() {
      return this.Name;
   }
     public String getFilePath(){
       return this.FilePath;
   }

   public void setFilePath(String filepath){
       this.FilePath = filepath;
   }

}