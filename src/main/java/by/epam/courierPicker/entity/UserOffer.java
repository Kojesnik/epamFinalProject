package by.epam.courierPicker.entity;

import java.sql.Date;
import java.util.List;

public class UserOffer extends Entity {

    private Integer idOffer;
    private Integer idUser;
    private String status;
    private Integer neededCourierNumber;
    private Integer activeCourierNumber;
    private List<Goods> goods;
    private Date startDate;
    private Date endDate;
    private String userComment;
    private String courierComment;

    public UserOffer() {}

    public String getCourierComment() {
        return courierComment;
    }

    public void setCourierComment(String courierComment) {
        this.courierComment = courierComment;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Integer getNeededCourierNumber() {
        return neededCourierNumber;
    }

    public void setNeededCourierNumber(Integer neededCourierNumber) {
        this.neededCourierNumber = neededCourierNumber;
    }

    public Integer getActiveCourierNumber() {
        return activeCourierNumber;
    }

    public void setActiveCourierNumber(Integer activeCourierNumber) {
        this.activeCourierNumber = activeCourierNumber;
    }

    public Integer getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(Integer idOffer) {
        this.idOffer = idOffer;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Goods> getGoods() {
        return goods;
    }

    public void setGoods(List<Goods> goods) {
        this.goods = goods;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserOffer{");
        sb.append("idOffer=").append(idOffer);
        sb.append(", idUser=").append(idUser);
        sb.append(", status='").append(status).append('\'');
        sb.append(", neededCourierNumber=").append(neededCourierNumber);
        sb.append(", activeCourierNumber=").append(activeCourierNumber);
        sb.append(", goods=").append(goods);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
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
        UserOffer userOffer = (UserOffer) obj;
        return (idOffer == userOffer.idOffer) && (idUser == userOffer.idUser) && (status != null && status.equals(userOffer.status))
                && (neededCourierNumber != null && neededCourierNumber.equals(userOffer.neededCourierNumber))
                && (activeCourierNumber != null && activeCourierNumber.equals(userOffer.activeCourierNumber))
                && (goods != null && goods.equals(userOffer.goods))
                && (startDate != null && startDate.equals(userOffer.startDate))
                && (endDate != null && endDate.equals(userOffer.endDate))
                && (courierComment != null && courierComment.equals(userOffer.courierComment))
                && (userComment != null && userComment.equals(userOffer.userComment));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + ((idUser == null) ? 0 : idUser.hashCode());
        result = result * prime + ((idOffer == null) ? 0 : idOffer.hashCode());
        result = result * prime + ((status == null) ? 0 : status.hashCode());
        result = result * prime + ((neededCourierNumber == null) ? 0 : neededCourierNumber.hashCode());
        result = result * prime + ((activeCourierNumber == null) ? 0 : activeCourierNumber.hashCode());
        result = result * prime + ((goods == null) ? 0 : goods.hashCode());
        result = result * prime + ((startDate == null) ? 0 : startDate.hashCode());
        result = result * prime + ((endDate == null) ? 0 : endDate.hashCode());
        result = result * prime + ((courierComment == null) ? 0 : courierComment.hashCode());
        result = result * prime + ((userComment == null) ? 0 : userComment.hashCode());
        return result;
    }
}
