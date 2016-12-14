package userInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;

import org.swingplus.JHyperlink;

import rSS.*;

@SuppressWarnings("serial")
public class Ui extends JFrame {

	private JTabbedPane tabbedPane = new JTabbedPane();
	private JPanel overviewTab = new JPanel();
	
	public Ui() {
		
		// default settings for the new JFrame
		this.setTitle("RSS News Feed Reader");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(750, 600));

		// add the tabbedPane including the overviewTab
		this.overviewTab.setLayout(new GridLayout(0,2));
		this.tabbedPane.addTab("Overview", this.overviewTab);
		this.add(this.tabbedPane);

		// create RSSFeedList
		// TODO: implement settings tab in GUI to make uris editable
		// http://www.faz.net/rss/aktuell/finanzen/
		RSSFeedList rssFeedList = new RSSFeedList();
		rssFeedList.addFeed("http://www.faz.net/rss/aktuell/");
		rssFeedList.addFeed("http://www.spiegel.de/schlagzeilen/tops/index.rss");

		// add a button for each feed in rssFeedList
		AddNewFeedTextboxAndButton(rssFeedList);
		AddFeedButtons(rssFeedList);

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
	private void AddFeedButtons(RSSFeedList rssFeedList) {

		int panelcount = this.overviewTab.getComponentCount();
		JTabbedPane pane = this.tabbedPane;
		rssFeedList.forEach((string, feed) -> {
			if(feed.getButtonIndex() == Integer.MAX_VALUE) {
				feed.setButtonIndex(panelcount); 
				this.overviewTab.add(new JButton(feed.getTitle()), panelcount);
				((JButton) this.overviewTab.getComponent(panelcount)).setText(feed.getTitle());
				((JButton) this.overviewTab.getComponent(panelcount)).setToolTipText(feed.getDescription());
				((JButton) this.overviewTab.getComponent(panelcount)).addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// TODO: implement correct tabindex handling
						if (feed.getTabIndex() == Integer.MAX_VALUE) {
							pane.add(feed.getTitle(), addRssFeedTab(string));
							feed.setTabIndex(panelcount);
						}
					}
				});
			}
		});
	}

	// AddNewFeedTextboxAndButton
	public void AddNewFeedTextboxAndButton(RSSFeedList rssFeedList) {
		JTextField inputUri = new JTextField(10);
		inputUri.setToolTipText("Enter RSS Feed URI");
		this.overviewTab.add(inputUri);
		JButton inputUriButton = new JButton("add Feed");
		inputUriButton.setToolTipText("Add the provided RSS Feed");
		inputUriButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rssFeedList.addFeed(inputUri.getText());
				AddFeedButtons(rssFeedList);
				refresh();
			}
		});
		this.overviewTab.add(inputUriButton);
	}
	
	// create the rss feed Tab
	public JPanel addRssFeedTab(String uri) {

		// create feed, panel and hashmap
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 8));

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
