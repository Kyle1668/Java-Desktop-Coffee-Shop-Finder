package edu.srjc.finalproject.obrien.kyle.quickcafe.models;

import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.net.URLConnection;
import java.io.InputStreamReader;

public class APIRequest
{

    static public String formatAPIRequest(String target)
    {
        String[] apiKeys = {"AIzaSyDkaFm1KmYTToZTX8Z2S-Mn9rdblJOk1YY", "AIzaSyC_KZyErDtZ42CuFscO2l5YseWaV8MCHrQ"};
        String request = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
        String query = "query=Cafe+coffee+near+" + target.replace(" ", "+");
        String apiKey = "&key=" + apiKeys[0] + "&sensor=false";
        return request + query + apiKey;
    }

    static public void printAPIResponse(String httpGetRequest) throws Exception
    {
        URL apiURL = new URL(httpGetRequest);
        URLConnection apiConnection = apiURL.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));

        while (in.readLine() != null)
        {
            System.out.println(in.readLine());
        }

        in.close();
    }

    static public String returnStringAPIResponse(String httpGetRequest) throws Exception
    {
        URL apiURL = new URL(httpGetRequest);
        StringBuilder returnString = new StringBuilder();
        URLConnection apiConnection = apiURL.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));

        while (in.readLine() != null)
        {
            returnString.append(in.readLine());
        }

        in.close();

        return returnString.toString();
    }

    static public ArrayList<Place> parsePlacesResponse(String httpGetRequest) throws Exception
    {
        String inputLine = new String();
        URL apiURL = new URL(httpGetRequest);
        URLConnection apiConnection = apiURL.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));

        ArrayList<Place> places = new ArrayList<>();
        places.add(new Place());

        while ((inputLine = in.readLine()) != null)
        {
            if (inputLine.split(":").length > 1)
            {
                handleInput(places, inputLine);
            }
        }

        in.close();
        cleanPlacesData(places);

        return places;
    }

    static private void handleInput(ArrayList<Place> places, String inputLine)
    {
        String attribute = inputLine.split(":")[0].split("\"")[1];

        switch (attribute)
        {
            case "formatted_address":
                String address = inputLine.split(":")[1].split("\"")[1];
                assignAddressData(places, address);
                break;
            case "name":
                String name = inputLine.split(":")[1].split(",")[0].split("\"")[1];
                assignNameData(places, name);
                break;
            case "rating":
                String[] attributeComponents = inputLine.split(":")[1].split(",")[0].split(" ");
                boolean hasRating = attributeComponents.length >= 2;
                String rating = hasRating ? attributeComponents[1] : "";
                assignRatingData(places, rating);
                break;
            case "open_now":
                String[] hoursDataArray = inputLine.split("[\\p{Punct}\\s]+");
                String isOpenValue = hoursDataArray.length >= 3 ? hoursDataArray[3] : "";
                assignIsOpenDataData(places, isOpenValue);
                break;
            case "photo_reference":
                String[] photoDataArray = inputLine.split("\"");
                String photoReference = photoDataArray.length >= 3 ? photoDataArray[3] : "";
                assignImageData(places, photoReference);
                break;
            case "types":
                String[] typesArray = inputLine.split("[^a-zA-Z0-9_]");
                String category = parseCategoryInput(typesArray);
                assignCategoryData(places, category);
                break;
        }
    }

    static private void assignAddressData(ArrayList<Place> places, String address)
    {
        if (!places.get(places.size() - 1).getAddress().equals(""))
        {
            Place newPlace = new Place();
            newPlace.setAddress(address);
            places.add(newPlace);
        }
        else
        {
            places.get(places.size() - 1).setAddress(address);
        }
    }

    static private void assignNameData(ArrayList<Place> places, String name)
    {
        if (!places.get(places.size() - 1).getName().equals(""))
        {
            Place newPlace = new Place();
            newPlace.setName(name);
            places.add(newPlace);
        }
        else
        {
            places.get(places.size() - 1).setName(name);
        }
    }

    static private void assignIsOpenDataData(ArrayList<Place> places, String isOpenValue)
    {
        if (!places.get(places.size() - 1).getIsOpenNow().equals(""))
        {
            Place newPlace = new Place();
            newPlace.setIsOpenNow(isOpenValue);
            places.add(newPlace);
        }
        else
        {
            Place last = places.get(places.size() - 1);
            last.setIsOpenNow(isOpenValue);
        }
    }

    static private void assignImageData(ArrayList<Place> places, String photoReference)
    {
        if (!places.get(places.size() - 1).getImage().getPhotoReference().equals(""))
        {
            Place newPlace = new Place();
            newPlace.setImage(photoReference);
            places.add(newPlace);
        }
        else
        {
            Place last = places.get(places.size() - 1);
            last.setImage(photoReference);
        }
    }

    static private void assignRatingData(ArrayList<Place> places, String rating)
    {
        if (!places.get(places.size() - 1).getRating().equals(""))
        {
            Place newPlace = new Place();
            newPlace.setRating(rating);
            places.add(newPlace);
        }
        else
        {
            Place last = places.get(places.size() - 1);
            last.setRating(rating);
        }
    }

    static private String parseCategoryInput(String[] categoryInput)
    {
        for (String element : categoryInput)
        {
            if (!element.equals("") && !element.equals("types"))
            {
                return element;
            }
        }
        return "N/A";
    }

    static private void assignCategoryData(ArrayList<Place> places, String category)
    {
        if (!places.get(places.size() - 1).getCategory().equals(""))
        {
            Place newPlace = new Place();
            newPlace.setCategory(category);
            places.add(newPlace);
        }
        else
        {
            Place last = places.get(places.size() - 1);
            last.setCategory(category);
        }
    }

    static private void cleanPlacesData(ArrayList<Place> places)
    {
        Place currentPlace = new Place();

        for (int index = 0; index < places.size(); index++)
        {
            currentPlace = places.get(index);
            if (currentPlace.getName().equals(""))
            {
                places.remove(index);
                index--;
            }
            else if (currentPlace.getAddress().equals(""))
            {
                places.remove(index);
                index--;
            }
        }

    }

}
