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
}
