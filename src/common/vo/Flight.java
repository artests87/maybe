package common.vo;

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
    private String HREF;
    private String toCode;
    private String fromCode;
    private String toTimeArrival;
    private String toTimeDepartment;
    private String toDuration;
    private String fromTimeArrival;
    private String fromTimeDepartment;
    private String fromDuration;
    private String fromName;
    private String toName;

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getHREF() {
        return HREF;
    }

    public String getToDuration() {
        return toDuration;
    }

    public String getFromDuration() {
        return fromDuration;
    }

    public String getToCode() {
        return toCode;
    }

    public String getFromCode() {
        return fromCode;
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

    public String getToTimeArrival() {
        return toTimeArrival;
    }

    public String getToTimeDepartment() {
        return toTimeDepartment;
    }

    public String getFromTimeArrival() {
        return fromTimeArrival;
    }

    public String getFromTimeDepartment() {
        return fromTimeDepartment;
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

    public void setHREF(String HREF) {
        this.HREF = HREF;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public void setToTimeArrival(String toTimeArrival) {
        this.toTimeArrival = toTimeArrival;
    }

    public void setToTimeDepartment(String toTimeDepartment) {
        this.toTimeDepartment = toTimeDepartment;
    }

    public void setFromTimeArrival(String fromTimeArrival) {
        this.fromTimeArrival = fromTimeArrival;
    }

    public void setFromTimeDepartment(String fromTimeDepartment) {
        this.fromTimeDepartment = fromTimeDepartment;
    }

    public void setToDuration(String toDuration) {
        this.toDuration = toDuration;
    }

    public void setFromDuration(String fromDuration) {
        this.fromDuration = fromDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;

        Flight flight = (Flight) o;

        if (title != null ? !title.equals(flight.title) : flight.title != null) return false;
        if (fromStart != null ? !fromStart.equals(flight.fromStart) : flight.fromStart != null) return false;
        if (toStart != null ? !toStart.equals(flight.toStart) : flight.toStart != null) return false;
        if (dateStart != null ? !dateStart.equals(flight.dateStart) : flight.dateStart != null) return false;
        if (fromEnd != null ? !fromEnd.equals(flight.fromEnd) : flight.fromEnd != null) return false;
        if (toEnd != null ? !toEnd.equals(flight.toEnd) : flight.toEnd != null) return false;
        if (dateEnd != null ? !dateEnd.equals(flight.dateEnd) : flight.dateEnd != null) return false;
        if (coast != null ? !coast.equals(flight.coast) : flight.coast != null) return false;
        if (HREF != null ? !HREF.equals(flight.HREF) : flight.HREF != null) return false;
        if (toCode != null ? !toCode.equals(flight.toCode) : flight.toCode != null) return false;
        return !(fromCode != null ? !fromCode.equals(flight.fromCode) : flight.fromCode != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (fromStart != null ? fromStart.hashCode() : 0);
        result = 31 * result + (toStart != null ? toStart.hashCode() : 0);
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (fromEnd != null ? fromEnd.hashCode() : 0);
        result = 31 * result + (toEnd != null ? toEnd.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (coast != null ? coast.hashCode() : 0);
        result = 31 * result + (HREF != null ? HREF.hashCode() : 0);
        result = 31 * result + (toCode != null ? toCode.hashCode() : 0);
        result = 31 * result + (fromCode != null ? fromCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "coast='" + coast + '\'' +
                ", title='" + title + '\'' +
                ", fromStart='" + fromName+" ("+fromStart+")" + '\'' +
                ", toStart='" + toName+" ("+toStart+")" + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", fromEnd='" + fromEnd + '\'' +
                ", toEnd='" + toEnd + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", HREF='" + HREF + '\'' +
                ", toCode='" + toCode + '\'' +
                ", fromCode='" + fromCode + '\'' +
                ", toTimeArrival='" + toTimeArrival + '\'' +
                ", toTimeDepartment='" + toTimeDepartment + '\'' +
                ", toDuration='" + toDuration + '\'' +
                ", fromTimeArrival='" + fromTimeArrival + '\'' +
                ", fromTimeDepartment='" + fromTimeDepartment + '\'' +
                ", fromDuration='" + fromDuration + '\'' +
                '}';
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
    public void setToName(String toName) {
        this.toName = toName;
    }
}
