package com.iot.dataservice.data;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by awmishra on 10/19/2015.
 */
@Entity
@Data
@Table(name = "item", schema = "iot")
public class ItemEntity {
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long itemId;

    /**
     * item Upc
     */
    @Column(name = "ITEM_UPC",
            nullable = false,
            insertable = true,
            updatable = true,
            length = 255)
    private String itemUpc;

    /**
     * Item Name
     */
    @Column(name = "ITEM_NAME",
            nullable = false,
            insertable = true,
            updatable = true)
    private String itemName;

    /**
     * Item Desc
     */
    @Column(name = "ITEM_DESC",
            nullable = false,
            insertable = true,
            updatable = true)
    private String itemDesc;

}