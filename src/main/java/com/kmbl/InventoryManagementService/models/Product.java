package com.kmbl.InventoryManagementService.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
    String id;
    String name;
    String category;
    String brand;
}
