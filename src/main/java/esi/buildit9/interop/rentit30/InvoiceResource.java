package esi.buildit9.interop.rentit30;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceResource {
    public Long invoiceId;
    public Date date;
    public Double total;
    public Long requestId;
    public Long poId;
}
