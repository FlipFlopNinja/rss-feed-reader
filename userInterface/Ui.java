package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import dataIO.URLList;
import rSS.Feed;
import rSS.RSSFeedList;
import tasks.FeedTimer;

@SuppressWarnings("serial")
public class Ui extends JFrame {

	JMenuBar menuBar = new JMenuBar();
	JMenu feeds = new JMenu("Feeds");
	JMenu addFeeds = new JMenu("Add feeds...");
	JMenu about = new JMenu("About");
	
	RSSFeedList myFeedList = new RSSFeedList();
	
	public Ui() {
		FeedTimer feedTimer = new FeedTimer(myFeedList);

		JMenuItem mI_feeds_nofeeds = new JMenuItem("No feeds present...");
		JMenuItem mI_addFeeds_addFeed = new JMenuItem("Add feed...");
		JMenuItem mI_addFeeds_addFeedList = new JMenuItem("Add feed list...");

		menuBar.add(feeds, 0);
		menuBar.add(addFeeds, 1);
		menuBar.add(about, 2);

		feeds.add(mI_feeds_nofeeds, 0);
		addFeeds.add(mI_addFeeds_addFeed);
		addFeeds.add(mI_addFeeds_addFeedList);

		mI_addFeeds_addFeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String url = (String) JOptionPane.showInputDialog(null, "Enter a valid URL: ", "Add feed...",
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					myFeedList.addFeed(url);
					addFeedMenuItem(myFeedList.getFeed(url));
					removePlaceHolder(mI_feeds_nofeeds);
				} catch (Exception exception) {
				}
			}
		});

		mI_addFeeds_addFeedList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int rv = chooser.showOpenDialog(null);
				if (rv == JFileChooser.APPROVE_OPTION) {
					try {
						myFeedList.addFeedList(new URLList(chooser.getSelectedFile()).getURLList());
						addFeedMenuItems(myFeedList);
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(null, "Please provide a valid .txt-file with RSS feed links!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}

				}
				removePlaceHolder(mI_feeds_nofeeds);
			}
		});

		this.setJMenuBar(menuBar);
		this.setTitle("RSS News Feed Reader");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		refresh();
	}

	public void refresh() {
		this.validate();
		this.pack();
		this.repaint();
	}

	private void addFeedMenuItems(RSSFeedList rssFeedList) {
		rssFeedList.forEach((string, feed) -> {
			addFeedMenuItem(feed);
		});
	}

	private void addFeedMenuItem(Feed feed) {
		if (feed.getMenuItem() == null) {
			FeedMenuItem tempItem = new FeedMenuItem(feed);
			feed.setMenuItem(tempItem);
			feeds.add(tempItem);
		}
	}
	
	private void removePlaceHolder(JMenuItem MenuItem) {
		if (myFeedList.size() > 0) {
			if (MenuItem.getParent() != null) {
				feeds.remove(MenuItem);
			}
		}
	}

	public static void main(String[] args) {
		Ui myUi = new Ui();
	}
}