package by.epam.courierPicker.entity;

public class Goods extends Entity {

    private Integer id;
    private String type;

    public Goods(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Goods() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Goods{");
        sb.append("id=").append(id);
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}