// GROUP MEMBERS
// Jonathan Fan
// Ryan Hsiu
package view;

import java.util.Scanner;
import java.io.*;
import app.Song;
import app.SongComparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ListController {

    @FXML
    private TextField yearInput;

	@FXML
    private TextField songInput;

	@FXML
    private TextField artistInput;
	
    @FXML
    private TextField albumInput;

    @FXML
    private Button addSong;

    @FXML
    private Button updateSong;

    @FXML
    private ListView<Song> listView;

	private String currentSong = "";
	private String currentArtist = "";

	private ObservableList<Song> obsList = FXCollections.observableArrayList(
	);

	

	public void start(Stage mainStage) throws IOException {
		File load = new File("songs.txt");
		Scanner sc = new Scanner(load);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			Song song = new Song();
			int i = 0;
			int s = 0;
			while (!(line.substring(i, i+3).equals("|||"))) {
				i++;
			}
			song.setSong(line.substring(s, i));
			i += 3;
			s = i;
			while (!(line.substring(i, i+3).equals("|||"))) {
				i++;
			}
			song.setArtist(line.substring(s, i));
			i += 3;
			s = i;
			while (!(line.substring(i, i+3).equals("|||"))) {
				i++;
			}
			if (s != i) {
				song.setYear(Integer.parseInt(line.substring(s,i)));
			}
			i += 3;
			s = i;
			while (i < line.length()) {
				i++;
			}
			if (s != i) {
				song.setAlbum(line.substring(s, line.length()));
			}
			obsList.add(song);
		}

		sort();
		listView.setItems(obsList);
	}

	public void fileUpdate() throws IOException {
		File file = new File("songs.txt");
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		for (Song s : obsList) {
			pw.println(s.getSong() + "|||" + s.getArtist() + "|||" + s.getYear() + "|||" + s.getAlbum());
		}
		pw.close();
	}

	@FXML
	public void addSong(ActionEvent event) throws IOException {
		Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure want to add this song?", ButtonType.YES, ButtonType.NO);
		a.showAndWait();

		if (a.getResult() == ButtonType.NO) {
    		return;
		}
		if (songInput.getText().trim().isEmpty() || artistInput.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Cannot add song");
			alert.setHeaderText("Error: Missing song name and/or artist name");
			alert.setContentText("You must enter in a song name and artist name");
			alert.showAndWait();
			return;
		}
		//else if (/*CONTAINS DUPLICATE*/) {

		//}
		Song song = new Song(songInput.getText(), artistInput.getText());
		if (search(song, obsList)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Cannot add song");
			alert.setHeaderText("Error: Song artist pair already exists");
			alert.setContentText("You must enter a unique song and artist name");
			alert.showAndWait();
			return;
		}
		if (!(yearInput.getText().trim().isEmpty())) {
			song.setYear(Integer.parseInt(yearInput.getText()));
		}
		if (!(albumInput.getText().trim().isEmpty())) {
			song.setAlbum(albumInput.getText());
		}
		obsList.add(song);
		sort();
		listView.setItems(obsList);
		currentSong = song.getSong();
		currentArtist = song.getArtist();
		songInput.setText(song.getSong());
		artistInput.setText(song.getArtist());
		yearInput.setText("" + song.getYear());
		albumInput.setText(song.getAlbum());
		fileUpdate();
	}

	public boolean search(Song song, ObservableList<Song> songs) {
		for (Song s : songs) {
			if (s.getSong().equals(song.getSong()) && s.getArtist().equals(song.getArtist())) {
				return true;
			}
		}
		return false;
	}

	public void sort() {
		SongComparator songcomp = new SongComparator();
		obsList.sort(songcomp);
	}

	public void updateSong(ActionEvent event) throws IOException {
		Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure want to update " +currentSong + " by " + currentArtist + "?", ButtonType.YES, ButtonType.NO);
		a.showAndWait();

		if (a.getResult() == ButtonType.NO) {
    		return;
		}
		if (currentSong == "" || currentArtist == "") {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No song selected");
			alert.setHeaderText("Error: No song selected");
			alert.setContentText("Please select the song you wish to update");
			alert.showAndWait();
			return;
		}
		if (songInput.getText().trim().isEmpty() || artistInput.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Cannot update song");
			alert.setHeaderText("Error: Missing song name and/or artist name");
			alert.setContentText("You must enter in a song name and artist name");
			alert.showAndWait();
			return;
		}
		Song temp = new Song(songInput.getText(), artistInput.getText());
		if (temp.getSong().equals(currentSong) && temp.getArtist().equals(currentArtist)) {

		}
		else if (search(temp, obsList)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Cannot add song");
			alert.setHeaderText("Error: Song artist pair already exists");
			alert.setContentText("You must enter a unique song and artist name");
			alert.showAndWait();
			return;
		}
		for (Song s : obsList) {
			if (s.getSong().equals(currentSong) && s.getArtist().equals(currentArtist)) {
				s.setSong(songInput.getText());
				s.setArtist(artistInput.getText());
				if (!(yearInput.getText().trim().isEmpty())) {
					s.setYear(Integer.parseInt(yearInput.getText()));
				}
				if (!(albumInput.getText().trim().isEmpty())) {
					s.setAlbum(albumInput.getText());
				}
				currentSong = s.getSong();
				currentArtist = s.getArtist();
				sort();
				listView.setItems(obsList);
				listView.refresh();
				fileUpdate();
				return;
			}
		}
	}

	public void deleteSong(ActionEvent event) throws IOException {
		Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure want to delete " +currentSong + " by " + currentArtist + "?", ButtonType.YES, ButtonType.NO);
		a.showAndWait();

		if (a.getResult() == ButtonType.NO) {
    		return;
		}
		if (currentSong == "" || currentArtist == "") {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No song selected");
			alert.setHeaderText("Error: No song selected");
			alert.setContentText("Please select the song you wish to delete");
			alert.showAndWait();
			return;
		}
		String tempSong = "";
		String tempArtist = "";
		int tempYear = 0;
		String tempAlbum = "";
		boolean prev = true;
		ObservableList<Song> temp = FXCollections.observableArrayList(
		);
		for (Song s : obsList) {
			if (s.getSong().equals(currentSong) && s.getArtist().equals(currentArtist)) {
				prev = false;
			}
			else {
				if (prev) {
					tempSong = s.getSong();
					tempArtist = s.getArtist();
					tempYear = s.getYear();
					tempAlbum = s.getAlbum();
				}
				else if (tempSong.trim().equals("")) {
					tempSong = s.getSong();
					tempArtist = s.getArtist();
					tempYear = s.getYear();
					tempAlbum = s.getAlbum();
				}
				temp.add(s);
			}
		}
		obsList = temp;
		songInput.setText(tempSong);
		artistInput.setText(tempArtist);
		yearInput.setText("" + tempYear);
		albumInput.setText(tempAlbum);
		currentSong = tempSong;
		currentArtist = tempArtist;
		listView.setItems(obsList);
		listView.refresh();
		fileUpdate();
	}

	public void rowClicked(MouseEvent event) {
		Song clickedSong = listView.getSelectionModel().getSelectedItem();
		if (clickedSong == null) {
			return;
		}
		songInput.setText(String.valueOf(clickedSong.getSong()));
		artistInput.setText(String.valueOf(clickedSong.getArtist()));
		yearInput.setText(String.valueOf(clickedSong.getYear()));
		albumInput.setText(String.valueOf(clickedSong.getAlbum()));
		currentSong = songInput.getText();
		currentArtist = artistInput.getText();
	}

}
