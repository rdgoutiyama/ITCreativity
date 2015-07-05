package br.com.projetodevoa.ITCreativity.Events;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import br.com.projetodevoa.ITCreativity.Events.model.Events;


public class SoundEvent extends Events {

	private int posWidth;

	private int posHeight;

	@Override
	public boolean detect() {
	    try {    
            AudioClip clip = Applet.newAudioClip(new File("som1.wav").toURL());    
            clip.play();    
            
            InputStream arq = new FileInputStream("C:/som1.wav");
            AudioStream som = new AudioStream(arq);
            AudioPlayer.player.start(som);
            
            System.out.println("Tocando som.");
            Thread.sleep(1000);
            AudioPlayer.player.stop(som);
        } catch (IOException | InterruptedException ex) {    
            ex.printStackTrace();    
        }   
		return false;
	}

}
