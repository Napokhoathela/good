package com.example.galleryapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private List<Mountain> highestMountains;
    private int currentIndex = 0;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f0;");

        highestMountains = loadHighestMountains();

        GridPane galleryGrid = createGalleryGrid(primaryStage);
        ScrollPane scrollPane = new ScrollPane(galleryGrid);
        scrollPane.setFitToWidth(true);

        root.setCenter(scrollPane);

        Button previousButton = new Button("Previous");
        previousButton.setStyle("-fx-font-size: 16px;");
        previousButton.setOnAction(event -> showPreviousMountain(primaryStage));

        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-font-size: 16px;");
        nextButton.setOnAction(event -> showNextMountain(primaryStage));

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(previousButton, nextButton);

        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Highest Mountains in Africa");
        primaryStage.show();

        showMountainDetails(primaryStage, highestMountains.get(0));
    }

    private GridPane createGalleryGrid(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        int col = 0;
        int row = 0;
        for (Mountain mountain : highestMountains) {
            ImageView thumbnail = createThumbnail(mountain.getThumbnail());

            thumbnail.setOnMouseClicked(event -> {
                showMountainDetails(primaryStage, mountain);
            });

            gridPane.add(thumbnail, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        return gridPane;
    }

    private ImageView createThumbnail(Image image) {
        ImageView thumbnail = new ImageView(image);
        thumbnail.setFitWidth(150);
        thumbnail.setFitHeight(150);
        thumbnail.getStyleClass().add("thumbnail"); 
        return thumbnail;
    }


    private List<Mountain> loadHighestMountains() {
        List<Mountain> highestMountains = new ArrayList<>();
        // Load information about the highest mountains here
        try {
            highestMountains.add(new Mountain("Mount Kilimanjaro", "Tanzania", "5,895 meters", new Image("kilimanjaro.jpg")));
            highestMountains.add(new Mountain("Mount Kenya", "Kenya", "5,199 meters", new Image("kenya.jpg")));
            highestMountains.add(new Mountain("Mount kenya2", "lesotho", "5,799 meters", new Image("kenya2.jpg")));
            highestMountains.add(new Mountain("Mount Kenya3", "Kenya", "5,199 meters", new Image("kenya3.jpg")));
            // Add more mountains as needed
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
            e.printStackTrace();
        }

        return highestMountains;
    }

    private void showMountainDetails(Stage primaryStage, Mountain mountain) {
        BorderPane root = (BorderPane) primaryStage.getScene().getRoot();

        StackPane centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);

        ImageView imageView = new ImageView(mountain.getThumbnail());
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(primaryStage.getWidth());
        imageView.setFitHeight(primaryStage.getHeight());

        Text text = new Text(mountain.getName() + "\n" + mountain.getLocation() + "\nHeight: " + mountain.getHeight());
        text.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        text.setFill(javafx.scene.paint.Color.WHITE);

        centerPane.getChildren().addAll(imageView, text);
    }

    private void showPreviousMountain(Stage primaryStage) {
        if (currentIndex > 0) {
            currentIndex--;
            showMountainDetails(primaryStage, highestMountains.get(currentIndex));
        }
    }

    private void showNextMountain(Stage primaryStage) {
        if (currentIndex < highestMountains.size() - 1) {
            currentIndex++;
            showMountainDetails(primaryStage, highestMountains.get(currentIndex));
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static class Mountain {
        private final String name;
        private final String location;
        private final String height;
        private final Image thumbnail;

        public Mountain(String name, String location, String height, Image thumbnail) {
            this.name = name;
            this.location = location;
            this.height = height;
            this.thumbnail = thumbnail;
        }


        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getHeight() {
            return height;
        }

        public Image getThumbnail() {
            return thumbnail;
        }
    }
}
