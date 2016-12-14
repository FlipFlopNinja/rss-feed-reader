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

		rssFeedList = FillRssFeedList();
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

	// fill some feeds into the rss feedlist
	// TODO: make this dynamically changeable by providing a settings tab where
	// those values can be edited
	public HashMap<String, Feed> FillRssFeedList() {
		HashMap<String, Feed> rssFeedList = new HashMap<String, Feed>();
		RSSFeedParser tempParser = new RSSFeedParser("http://www.faz.net/rss/aktuell/");
		Feed tempFeed = tempParser.readFeed();
		rssFeedList.put("http://www.faz.net/rss/aktuell/", tempFeed);
		tempParser = new RSSFeedParser("http://www.spiegel.de/schlagzeilen/tops/index.rss");
		tempFeed = tempParser.readFeed();
		rssFeedList.put("http://www.spiegel.de/schlagzeilen/tops/index.rss", tempFeed);
		return rssFeedList;
	}

	// add buttons for each feed
	private JPanel AddFeedButtons(JTabbedPane pane, JPanel panel, HashMap<String, Feed> feedList) {

		int count = 0;

		feedList.forEach((key, value) -> {
			panel.add(new JButton(value.getTitle()), count);
			((JButton) panel.getComponent(count)).setText(value.getTitle());
			((JButton) panel.getComponent(count)).setToolTipText(value.getDescription());
			// add actionlistener to each button
			((JButton) panel.getComponent(count)).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// check if the specific tab already exists and create a new
					// if not
					if (checkExistence(pane, value.getTitle()) == false)
						pane.add(value.getTitle(), addRssFeedTab(key));
				}
			});
		});
		return panel;
	}

	// check existence of a tab in the pane by comparing the components
	// name(feed title) with the feed-title
	public boolean checkExistence(JTabbedPane pane, String title) {

		for (Component comp : pane.getComponents()) {

			// cast the component into a JPanel and check if its null, compare
			// titles if not
			if (((JPanel) comp).getName() != null && ((JPanel) comp).getName().equals(title)) {
				return true;
			}
		}

		return false;
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
		// create hyperlinks, caption is the title and the feedmessage link is hyperlink
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