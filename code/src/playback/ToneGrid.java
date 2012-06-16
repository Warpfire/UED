/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package playback;

import GUI.ParticlePanel;
//import GUI.VisualizationPanel;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;

/**
 *
 * @author Niels Kamp, Kasper Vaessen, Niels Visser
 * @param <syncronized>
 */
public class ToneGrid {

    protected List<List<Boolean>> grid;
    protected int width;
    protected int height;
    protected boolean isActive;
    protected GridConfiguration gc;

    public ToneGrid(GridConfiguration gc) {
        this.height = 10;
        this.gc = gc;
    }
    
    public void toggleTone(int column, int note) {
        List<Boolean> col = this.grid.get(column);
        synchronized (col) {
            col.set(note, !col.get(note));
        }
    }
    
    public void activateTone(int column, int note) {
        List<Boolean> col = this.grid.get(column);
        synchronized (col) {
            col.set(note, true);
        }
    }
    
    public void deactivateTone(int column, int note) {
    	List<Boolean> col = this.grid.get(column);
        synchronized (col) {
            col.set(note, false);
        }
    }

    public synchronized List<List<Boolean>> getAllTones() {
        List<List<Boolean>> result = new ArrayList<List<Boolean>>();
        for (int i = 0; i < this.width; i++) {
            List<Boolean> el = new ArrayList<Boolean>();
            result.add(el);
            for (int j = 0; j < this.height; j++) {
                el.add(this.grid.get(i).get(j));
            }
        }
        return result;
    }
    
    public boolean getTone(int column, int note) {
        synchronized (this.grid) {
            return this.grid.get(column).get(note);
        }
    }
    
    /**
     * Delegeert aan gridconfig
     * @param column
     * @param vp 
     */
    public void playColumnTones(int column, ParticlePanel vp) {
        List<Integer> tones = this.getColumnTones(column);
        this.gc.playTones(tones, vp);
    }
    
    public List<Integer> getColumnTones(int x) {
        List<Boolean> notesArray = this.grid.get(x);
        List<Integer> notesList = new ArrayList<Integer>();
        for(int i=0; i<notesArray.size(); i++) {
            if(notesArray.get(i))
                notesList.add(i);
        }
        return notesList;
    }
    
    public void setConfiguration(GridConfiguration gc) {
        this.gc = gc;
    }
    
    public GridConfiguration getGridConfiguration() {
    	return gc;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public void registerCallBack(int w) {
        this.width = w;
        this.grid = new ArrayList<List<Boolean>>();
        for (int i = 0; i < w; i++) {
            List<Boolean> el = new ArrayList<Boolean>();
            this.grid.add(el);
            for (int j = 0; j < height; j++) {
                el.add(false);
            }
        }
        this.isActive = false;
    }
    
    public void clear() {
        for(List<Boolean> l1 : this.grid) {
            for(int i = 0; i < l1.size(); i++) {
                l1.set(i, false);
            }
        }
    }
    
}
