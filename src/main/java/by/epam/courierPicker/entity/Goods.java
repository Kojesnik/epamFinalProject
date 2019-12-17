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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Goods goods = (Goods) obj;
        return (id != null && id.equals(goods.id))
                && (type != null && type.equals(goods.type));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + ((id == null) ? 0 : id.hashCode());
        result = result * prime + ((type == null) ? 0 : type.hashCode());
        return result;
    }
}
