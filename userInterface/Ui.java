package userInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import org.swingplus.JHyperlink;

import dataIO.URIList;
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
		this.overviewTab.setLayout(new GridLayout(0, 3));
		this.tabbedPane.addTab("Overview", this.overviewTab);
		this.add(this.tabbedPane);
		
		// TODO: make the start option editable with settings.
		// create RSSFeedList
		RSSFeedList rssFeedList = new RSSFeedList("feedlist.txt");

		// 
		AddFileChooser(rssFeedList);
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
	// METHOD: add the FileChooser button to overview; the chosen file populates rssFeedList
	private void AddFileChooser(RSSFeedList rssFeedList) {
		JButton addFileButton = new JButton("Add RSS feed list");
		addFileButton.setToolTipText("Add a file which contains RSS feed URIs");
		addFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int rv = chooser.showOpenDialog(null);
				if (rv == JFileChooser.APPROVE_OPTION) {
					try {
						rssFeedList.addFeedList(new URIList(chooser.getSelectedFile()).getURIList());
						AddFeedButtons(rssFeedList);
						refresh();
					}
					catch (Exception exception) {
						JOptionPane.showMessageDialog(null, "Please provide a valid .txt-file with RSS feed links!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		this.overviewTab.add(addFileButton);
	}

	// METHOD: add a button for each feed in rssFeedList
	private void AddFeedButtons(RSSFeedList rssFeedList) {

		int panelcount = this.overviewTab.getComponentCount();
		JTabbedPane pane = this.tabbedPane;
		rssFeedList.forEach((string, feed) -> {
			if (feed.getButtonIndex() == Integer.MAX_VALUE) {
				feed.setButtonIndex(panelcount);
				this.overviewTab.add(new JButton(feed.getTitle()), panelcount);
				((JButton) this.overviewTab.getComponent(panelcount)).setText(feed.getTitle());
				((JButton) this.overviewTab.getComponent(panelcount)).setToolTipText(feed.getDescription());
				((JButton) this.overviewTab.getComponent(panelcount)).addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// TODO: implement correct tabindex handling
						if (feed.getTabIndex() == Integer.MAX_VALUE) {
							pane.add(feed.getTitle(), addRssFeedTab(feed));
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
				try {
					rssFeedList.addFeed(inputUri.getText());
					inputUri.setText("");
					AddFeedButtons(rssFeedList);
					refresh();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, "Please enter a valid URI", "Input Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.overviewTab.add(inputUriButton);
	}

	// create the rss feed Tab
	public JPanel addRssFeedTab(Feed feed) {
		// create feed, panel and hashmap
		JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 8));
		// tempPanel.setName(feed.getTitle());
		// create msg-list
		List<FeedMessage> feedmessages = feed.getMessages();
		// create hyperlinks, caption is the title and the feedmessage link is
		// hyperlink
		feedmessages.forEach((message) -> {
			try {
				tempPanel.add(new JHyperlink(message.getTitle(), message.getLink()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return tempPanel;
	}

}
