package by.epam.courierPicker.entity;

import java.util.List;
import java.sql.Date;

public class CourierOffer extends Entity{

    private Integer idOffer;
    private Integer idUser;
    private String status;
    private List<Transport> transport;
    private List<Goods> goods;
    private Date startDate;
    private Date endDate;
    private String courierComment;
    private String userComment;

    public CourierOffer(Integer idUser, String status) {
        this.status = status;
        this.idUser = idUser;
    }

    public CourierOffer() {}

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getCourierComment() {
        return courierComment;
    }

    public void setCourierComment(String courierComment) {
        this.courierComment = courierComment;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(Integer idOffer) {
        this.idOffer = idOffer;
    }

    public List<Transport> getTransport() {
        return transport;
    }

    public void setTransport(List<Transport> transport) {
        this.transport = transport;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CourierOffer{");
        sb.append("idOffer=").append(idOffer);
        sb.append(", idUser=").append(idUser);
        sb.append(", status='").append(status).append('\'');
        sb.append(", transport=").append(transport);
        sb.append(", goods=").append(goods);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", courierComment=").append(courierComment);
        sb.append('}');
        return sb.toString();
    }
}
