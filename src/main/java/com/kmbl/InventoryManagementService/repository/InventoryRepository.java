package com.kmbl.InventoryManagementService.repository;


import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

import com.kmbl.InventoryManagementService.models.Inventory;


@Repository
@EnableScan
public interface InventoryRepository extends DynamoDBCrudRepository<Inventory, String> {

    
}



// public class InventoryRepository {
//     private final DynamoDBMapper dynamoDBMapper;

//     @Autowired
//     public InventoryRepository(DynamoDBMapper dynamoDBMapper){
//         this.dynamoDBMapper = dynamoDBMapper;
//     }

//     public Inventory findById(String itemId){
//         return dynamoDBMapper.load(Inventory.class, itemId);
//     }

//     public void saveItem(Inventory item){
//         dynamoDBMapper.save(item);
//     }

//     public void deleteById(String id){
//         Inventory inventory = new Inventory();
//         inventory.setId(id);
//         dynamoDBMapper.delete(inventory); 
//     }

//     public List<Inventory> findAll(){
//         return dynamoDBMapper.scan(Inventory.class, new DynamoDBScanExpression());
//     }


// }
