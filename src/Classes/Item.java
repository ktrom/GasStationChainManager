package Classes;

public class Item {
    private int itemID;
    private int inventoryID;
    private String name;
    private double price;
    private String photoURL;
    private String notes;


    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Item(int itemID, int inventoryID, String name, double price, String photoURL, String notes) {
        this.itemID = itemID;
        this.inventoryID = inventoryID;
        this.name = name;
        this.price = price;
        this.photoURL = photoURL;
        this.notes = notes;
    }

    public void addItem(int ID){

    }
}
