package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import rSS.Feed;

@SuppressWarnings("serial")
public class FeedMenuItem extends JMenuItem {
	
	Boolean updated = false;
	Feed feed;

	public FeedMenuItem(Feed feed) {
		this.feed = feed;
		this.setText(feed.getTitle());
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (updated) {
					if (feed.getFrame() != null) {
						feed.getFrame().dispose();
						feed.setFrame(new FeedFrame(feed));
					}
					((FeedMenuItem) e.getSource()).setUpdated(false);
				}
				if (feed.getFrame() == null) {
					feed.setFrame(new FeedFrame(feed));
				} else {
					feed.getFrame().toFront();
				}
			}
		});
	}
	
	public void setUpdated(Boolean updated) {
		this.updated = updated;
		if (updated) {
			this.setText(feed.getTitle() + "*");
			this.setToolTipText("Updates avaiable!");
		} else {
			this.setText(feed.getTitle());
			this.setToolTipText(null);
		}
	}
}
