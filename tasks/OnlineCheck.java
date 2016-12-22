package tasks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class OnlineCheck extends TimerTask {

	@Override
	public void run() {
		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Bitte eine Verbindung zum Internet herstellen.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
