package esi.buildit9.interop.rentit1;

import org.springframework.roo.addon.javabean.RooJavaBean;

import javax.xml.bind.annotation.XmlRootElement;

@RooJavaBean
@XmlRootElement(name="RejectPO")
public class RejectPOResource {

    private Long poid;
    private String commentt;

    public String getCommentt() {
        return commentt;
    }

    public void setCommentt(String commentt) {
        this.commentt = commentt;
    }

    public Long getPoid() {
        return poid;
    }

    public void setPoid(Long poid) {
        this.poid = poid;
    }

}
