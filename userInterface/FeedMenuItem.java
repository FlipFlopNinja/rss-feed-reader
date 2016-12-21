package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import rSS.Feed;

@SuppressWarnings("serial")
public class FeedMenuItem extends JMenuItem {

	public FeedMenuItem(Feed feed) {
		this.setText(feed.getTitle());
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!feed.getIsOpened()) {
					new FeedFrame(feed);
					feed.setIsOpened(true);
				}
			}
		});
	}
}
