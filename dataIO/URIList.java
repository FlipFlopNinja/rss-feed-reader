package dataIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class URIList {

	private List<String> uriList = new ArrayList<String>();
	private BufferedReader bReader;
	private File file;

	public URIList(String fileName) {
		file = new File(fileName);
		ReadFile(file);
	}

	public URIList(File file) {
		ReadFile(file);
	}

	public void ReadFile(File file) {
		if (!file.canRead() || !file.isFile()) {
			// find path where app is running "new File(".").getAbsolutePath()"
			JOptionPane.showMessageDialog(null,
					"Cannot find the URI list. \nPlease provide the file feedlist.txt in the directory shown below to avoid this message!"
							+ "\nThe file should contain one rss feed link per line." + "\n" + new File(".").getAbsolutePath(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}

		try {
			bReader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = bReader.readLine()) != null) {
				uriList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bReader != null)
				try {
					bReader.close();
				} catch (IOException e) {
				}
		}
	}

	public List<String> getURIList() {
		return this.uriList;
	}

}
