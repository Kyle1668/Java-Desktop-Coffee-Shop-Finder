package edu.srjc.finalproject.obrien.kyle.quickcafe.models;

public class Place
{
    private String name = "";
    private String address = "";
    private String rating = "";
    private String category = "";
    private String isOpenNow = "";
    private PlaceIcon image = new PlaceIcon();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getIsOpenNow()
    {
        return isOpenNow;
    }

    public void setIsOpenNow(String isOpenNow)
    {
        this.isOpenNow = isOpenNow;
    }

    public PlaceIcon getImage()
    {
        return image;
    }

    public void setImage(String photoReference)
    {
        image.setPhotoReference(photoReference);
    }

    public void print()
    {
        System.out.println("\n" + "Name: " + name);
        System.out.println("Address: " + address);

        if (!category.equals(""))
        {
            System.out.println("Category: " + category);
        }
        if (!rating.equals(""))
        {
            System.out.println("Rating: " + rating);
        }

    }

    @Override
    public String toString()
    {
        return "\n" + name + " { \n" +
                " Address = \"" + address + '\"' + '\n' +
                " Category = \"" + category + '\"' + '\n' +
                " Rating = \"" + rating + '\"' + '\n' +
                " Open = \"" + isOpenNow + '\"' + '\n' +
                " Image = \"" + image.getPhotoURL() + '\"' + "\n" +
                '}';
    }

}
