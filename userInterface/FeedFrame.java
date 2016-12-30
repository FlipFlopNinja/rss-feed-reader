package userInterface;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JFrame;

import org.swingplus.JHyperlink;

import rSS.Feed;
import rSS.FeedMessage;

@SuppressWarnings("serial")
public class FeedFrame extends JFrame{

	public FeedFrame(Feed feed) {		
		// add a hyperlink for each message in the feed
		List<FeedMessage> feedmessages = feed.getMessages();
		feedmessages.forEach((message) -> {
			this.add((new JHyperlink(message.getTitle(), message.getLink())));
		});
		
		this.setTitle(feed.getTitle());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(0,1));
		this.setVisible(true);
		this.validate();
		this.pack();
		this.setResizable(false);
		this.repaint();
		
		this.addWindowListener(new WindowListener() {

		    @Override
		    public void windowOpened(WindowEvent e) {}

		    @Override
		    public void windowClosing(WindowEvent e) {
		    	feed.setFrame(null);
		    }
		    @Override
		    public void windowClosed(WindowEvent e) {}

		    @Override
		    public void windowIconified(WindowEvent e) {}

		    @Override
		    public void windowDeiconified(WindowEvent e) {}

		    @Override
		    public void windowActivated(WindowEvent e) {}

		    @Override
		    public void windowDeactivated(WindowEvent e) {}

		});
	}
}
