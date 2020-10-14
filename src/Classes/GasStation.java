package Classes;

public class GasStation {
    private int gasStationID;
    private int location;

    public GasStation(int gasStationID, int location) {
        this.gasStationID = gasStationID;
        this.location = location;
    }

    public int getGasStationID() {
        return gasStationID;
    }

    public void setGasStationID(int gasStationID) {
        this.gasStationID = gasStationID;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
