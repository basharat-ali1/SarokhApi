package net.sarokh.api.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "Warehouse_Storage", schema = "Sarokh_db")
public class WarehouseStorage {
    private int id;
    private String name;
    private Integer columnNumber;
    private Integer rowNumber;
    private String size;
    private Byte occupied;
    private String details;
    private int sarokhWarehouseId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "column_number", nullable = true)
    public Integer getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    @Basic
    @Column(name = "row_number", nullable = true)
    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Basic
    @Column(name = "size", nullable = true, length = 45)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Basic
    @Column(name = "occupied", nullable = true)
    public Byte getOccupied() {
        return occupied;
    }

    public void setOccupied(Byte occupied) {
        this.occupied = occupied;
    }

    @Basic
    @Column(name = "details", nullable = true, length = -1)
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Basic
    @Column(name = "sarokh_warehouse_id", nullable = false)
    public int getSarokhWarehouseId() {
        return sarokhWarehouseId;
    }

    public void setSarokhWarehouseId(int sarokhWarehouseId) {
        this.sarokhWarehouseId = sarokhWarehouseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarehouseStorage that = (WarehouseStorage) o;

        if (id != that.id) return false;
        if (sarokhWarehouseId != that.sarokhWarehouseId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (columnNumber != null ? !columnNumber.equals(that.columnNumber) : that.columnNumber != null) return false;
        if (rowNumber != null ? !rowNumber.equals(that.rowNumber) : that.rowNumber != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (occupied != null ? !occupied.equals(that.occupied) : that.occupied != null) return false;
        return details != null ? details.equals(that.details) : that.details == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (columnNumber != null ? columnNumber.hashCode() : 0);
        result = 31 * result + (rowNumber != null ? rowNumber.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (occupied != null ? occupied.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + sarokhWarehouseId;
        return result;
    }
}
