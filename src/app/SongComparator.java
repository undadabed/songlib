// GROUP MEMBERS
// Jonathan Fan
// Ryan Hsiu
package app;

import java.util.Comparator;

public class SongComparator implements Comparator<Song>{

    @Override
    public int compare(Song o1, Song o2) {
        // TODO Auto-generated method stub
        if (o1.getSong().equals(o2.getSong())) {
            return o1.getArtist().compareTo(o2.getArtist());
        }
        return o1.getSong().compareTo(o2.getSong());
    }
    
}
