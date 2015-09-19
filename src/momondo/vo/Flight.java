package momondo.vo;

/**
 * Created by artests on 13.08.2015.
 */
public class Flight
{
    private String title;
    private String fromStart;
    private String toStart;
    private String dateStart;
    private String fromEnd;
    private String toEnd;
    private String dateEnd;
    private String coast;

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getFromStart() {
        return fromStart;
    }

    public String getToStart() {
        return toStart;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getFromEnd() {
        return fromEnd;
    }

    public String getToEnd() {
        return toEnd;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getCoast() {
        return coast;
    }

    public void setCoast(String coast) {
        this.coast = coast;
    }

    public void setFromStart(String fromStart) {
        this.fromStart = fromStart;
    }

    public void setToStart(String toStart) {
        this.toStart = toStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public void setFromEnd(String fromEnd) {
        this.fromEnd = fromEnd;
    }

    public void setToEnd(String toEnd) {
        this.toEnd = toEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (title != null ? !title.equals(flight.title) : flight.title != null) return false;
        if (fromStart != null ? !fromStart.equals(flight.fromStart) : flight.fromStart != null) return false;
        if (toStart != null ? !toStart.equals(flight.toStart) : flight.toStart != null) return false;
        if (dateStart != null ? !dateStart.equals(flight.dateStart) : flight.dateStart != null) return false;
        if (fromEnd != null ? !fromEnd.equals(flight.fromEnd) : flight.fromEnd != null) return false;
        return !(toEnd != null ? !toEnd.equals(flight.toEnd) : flight.toEnd != null);

    }

    @Override
    public int hashCode()
    {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (fromStart != null ? fromStart.hashCode() : 0);
        result = 31 * result + (toStart != null ? toStart.hashCode() : 0);
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (fromEnd != null ? fromEnd.hashCode() : 0);
        result = 31 * result + (toEnd != null ? toEnd.hashCode() : 0);
        return result;
    }
}
