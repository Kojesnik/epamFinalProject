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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        CourierOffer courierOffer = (CourierOffer) obj;
        return (idOffer == courierOffer.idOffer) && (idUser == courierOffer.idUser) && (status != null && status.equals(courierOffer.status))
                && (transport != null && transport.equals(courierOffer.transport))
                && (goods != null && goods.equals(courierOffer.goods))
                && (startDate != null && startDate.equals(courierOffer.startDate))
                && (endDate != null && endDate.equals(courierOffer.endDate))
                && (courierComment != null && courierComment.equals(courierOffer.courierComment))
                && (userComment != null && userComment.equals(courierOffer.userComment));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + ((idUser == null) ? 0 : idUser.hashCode());
        result = result * prime + ((idOffer == null) ? 0 : idOffer.hashCode());
        result = result * prime + ((status == null) ? 0 : status.hashCode());
        result = result * prime + ((transport == null) ? 0 : transport.hashCode());
        result = result * prime + ((goods == null) ? 0 : goods.hashCode());
        result = result * prime + ((startDate == null) ? 0 : startDate.hashCode());
        result = result * prime + ((endDate == null) ? 0 : endDate.hashCode());
        result = result * prime + ((courierComment == null) ? 0 : courierComment.hashCode());
        result = result * prime + ((userComment == null) ? 0 : userComment.hashCode());
        return result;
    }
}
