package userInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;

import org.swingplus.JHyperlink;

import rSS.*;

@SuppressWarnings("serial")
public class Ui extends JFrame {

	HashMap<String, Feed> rssFeedList;

	public Ui() {

		// default settings for the new JFrame
		this.setTitle("RSS News Feed Reader");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(750, 600));
		this.setLayout(new GridLayout(1, 1));

		// add the tabbedPane including the overviewTab
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel overviewTab = new JPanel();
		tabbedPane.addTab("Overview", overviewTab);
		this.add(tabbedPane);

		// create RSSFeedList
		// TODO: implement settings tab in GUI to make uris editable
		RSSFeedList rssFeedList = new RSSFeedList();
		rssFeedList.addFeed("http://www.faz.net/rss/aktuell/");
		rssFeedList.addFeed("http://www.spiegel.de/schlagzeilen/tops/index.rss");

		// add a button for each feed in rssFeedList
		overviewTab = AddFeedButtons(tabbedPane, overviewTab, rssFeedList);

		// draw UI
		this.setVisible(true);
		refresh();
	}

	// validate and repaint UI
	public void refresh() {
		this.validate();
		this.repaint();
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Ui rssFeedUI = new Ui();
	}

	// METHOD: add a button for each feed in rssFeedList
	private JPanel AddFeedButtons(JTabbedPane pane, JPanel panel, RSSFeedList rssFeedList) {

		int count = 0;

		rssFeedList.forEach((string, feed) -> {
			panel.add(new JButton(feed.getTitle()), count);
			((JButton) panel.getComponent(count)).setText(feed.getTitle());
			((JButton) panel.getComponent(count)).setToolTipText(feed.getDescription());
			((JButton) panel.getComponent(count)).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (feed.getIndex() == Integer.MAX_VALUE) {
						pane.add(feed.getTitle(), addRssFeedTab(string));
						feed.setIndex(count); 
					}
				}
			});
		});
		return panel;
	}

	// create the rss feed Tab
	public JPanel addRssFeedTab(String uri) {

		// create feed, panel and hashmap
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridLayout(0, 1));

		Feed tempfeed = new RSSFeedParser(uri).readFeed();
		HashMap<JLabel, FeedMessage> msgLabel = new HashMap<JLabel, FeedMessage>();
		// fill hashmap
		for (FeedMessage message : tempfeed.getMessages()) {
			msgLabel.put(new JLabel(), message);
		}
		// create hyperlinks, caption is the title and the feedmessage link is
		// hyperlink
		msgLabel.forEach((key, value) -> {
			try {
				tempPanel.add(new JHyperlink(value.getTitle(), value.getLink()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		// set name of the component to be able to search for its existence
		// later on
		tempPanel.setName(tempfeed.getTitle());
		return tempPanel;
	}

}
