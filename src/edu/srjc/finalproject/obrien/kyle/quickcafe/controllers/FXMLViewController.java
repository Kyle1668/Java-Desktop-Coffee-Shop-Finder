
package edu.srjc.finalproject.obrien.kyle.quickcafe.controllers;

import javafx.application.*;
import java.net.URL;
import javafx.fxml.FXML;
import java.util.ArrayList;
import javafx.geometry.Insets;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.TextAlignment;
import edu.srjc.finalproject.obrien.kyle.quickcafe.models.Place;
import edu.srjc.finalproject.obrien.kyle.quickcafe.models.APIRequest;

/**
 * @author
 */
public class FXMLViewController implements Initializable
{

    @FXML
    private Label label;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField txtName;

    @FXML
    private GridPane gridPaneList;

    @FXML
    private ScrollPane scrollPaneArea;

    @FXML
    private void handleSearchButtonAction(ActionEvent event) throws Exception
    {

        if (txtName.getText().length() > 0)
        {

            String targetLocation = txtName.getText();
            ArrayList<Place> places = getPlacesFromAPI(targetLocation);
            Insets padding = new Insets(10, 10, 10, 10);

            initFirstGridRow(padding);

            if (gridPaneList.getRowCount() > 1)
            {
                // Clears each row excluding the first.
                gridPaneList.getChildren().remove(5, gridPaneList.getChildren().size() - 1);
            }

            for (int rowIndex = 0; rowIndex < places.size(); rowIndex++)
            {
                int gridColSize = gridPaneList.getColumnCount();
                double newScrollDimensions = scrollPaneArea.getVvalue() + (scrollPaneArea.getVvalue() * .5);

                scrollPaneArea.setVvalue(newScrollDimensions);
                gridPaneList.addRow(rowIndex + 1);

                for (int colIndex = 0; colIndex < gridColSize; colIndex++)
                {
                    initGridCell(places, rowIndex, colIndex, padding);
                }
            }


            statusLabel.setText(places.size() + " Results Found");

        }
        else
        {
            label.setText("Error");
        }
    }

    private void initFirstGridRow(Insets padding)
    {
        String[] titles = {"Name", "Address", "Currently Open", "Google Rating", "Image"};

        for (int i = 0; i < 5; i++)
        {
            Label nameLabel = new Label(titles[i]);
            nameLabel.setTextAlignment(TextAlignment.JUSTIFY);
            nameLabel.setWrapText(true);
            nameLabel.setPadding(padding);
            gridPaneList.add(nameLabel, i, 0);
        }

    }

    private void initGridCell(ArrayList<Place> places, int rowIndex, int colIndex, Insets padding)
    {
        Label newCellLabel = new Label();
        newCellLabel.setTextAlignment(TextAlignment.JUSTIFY);
        newCellLabel.setPadding(padding);
        newCellLabel.setWrapText(true);

        switch (colIndex)
        {
            case 0:
                newCellLabel.setText(places.get(rowIndex).getName());
                break;
            case 1:
                newCellLabel.setText(places.get(rowIndex).getAddress());
                break;
            case 2:
                newCellLabel.setText(places.get(rowIndex).getIsOpenNow());
                break;
            case 3:
                newCellLabel.setText(places.get(rowIndex).getRating());
                break;
            case 4:
                final ImageView gridImageView = initImage(places, rowIndex);
                gridPaneList.add(gridImageView, colIndex, rowIndex + 1);
                break;
        }

        gridPaneList.add(newCellLabel, colIndex, rowIndex + 1);

    }

    private ImageView initImage(ArrayList<Place> places, int rowIndex)
    {
        final ImageView gridImageView = new ImageView();
        String imageSource = places.get(rowIndex).getImage().getPhotoURL();
        Image image1 = new Image(imageSource);

        gridImageView.setFitWidth(200);
        gridImageView.setFitHeight(200);
        gridImageView.setImage(image1);

        return gridImageView;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    static private ArrayList<Place> getPlacesFromAPI(String target) throws Exception
    {
        final String request = APIRequest.formatAPIRequest(target);

        System.out.println("\n" + "HTTP API Request: " + request + "\n");

//        APIRequest.printAPIResponse(getAPIRequest(request));

        ArrayList<Place> places = APIRequest.parsePlacesResponse(request);

        for (Place place : places)
        {
            System.out.println(place.toString());
        }

        return places;

    }

}
