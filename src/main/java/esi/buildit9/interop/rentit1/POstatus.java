package esi.buildit9.interop.rentit1;

public class POstatus {

    public final static short PENDING_CONFIRMATION = 0;
    public final static short REJECTED = -1;
    public final static short OPEN = 1;
    public final static short PENDING_UPDATE = 2;
    public final static short CLOSED = 3;
    public final static short Plant_Dispatched = 4;
    public final static short Plant_Delivered = 5;
    public final static short Plant_Rejected_By_Customer = 6;
    public final static short Plant_Returned = 7;
    public final static short Invoiced = 8;


    public static String getStatusText(short status){

        switch(status){
            case -1:
                return "REJECTED";
            case 0:
                return "PENDING_CONFIRMATION";
            case 1:
                return "OPEN";
            case 2:
                return "PENDING_UPDATE";
            case 3:
                return "CLOSED";
            case 4:
                return "Plant_Dispatched";
            case 5:
                return "Plant_Delivered";
            case 6:
                return "Plant_Rejected_By_Customer";
            case 7:
                return "Plant_Returned";
            case 8:
                return "Invoiced";
        }

        return "";
    }

    public static short getStatusID(String status){
        if (status.equals("REJECTED"))
            return -1;
        if (status.equals("PENDING_CONFIRMATION"))
            return 0;
        if (status.equals("OPEN"))
            return 1;
        if (status.equals("PENDING_UPDATE"))
            return 2;
        if (status.equals("CLOSED"))
            return 3;
        if (status.equals("Plant_Dispatched"))
            return 4;
        if (status.equals("Plant_Delivered"))
            return 5;
        if (status.equals("Plant_Rejected_By_Customer"))
            return 6;
        if (status.equals("Plant_Returned"))
            return 7;
        if (status.equals("Invoiced"))
            return 8;

        return 0;
    }
}

