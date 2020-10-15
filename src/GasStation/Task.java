package GasStation;

public class Task {

    /**
     * Gas station to schedule at.
     */
    private int GasStationID;

    /**
     * Employee being scheduled.
     */
    private int EmployeeID;

    /**
     * Description of the task
     */
    private String Description;

    public Task(int GasStationId, int EmployeeId, String Description) {
        this.GasStationID = GasStationID;
        this.EmployeeID = EmployeeID;
        this.Description = Description;
    }
}
