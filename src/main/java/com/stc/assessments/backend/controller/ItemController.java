package com.stc.assessments.backend.controller;

import com.stc.assessments.backend.model.Item;
import com.stc.assessments.backend.model.PermissionGroup;
import com.stc.assessments.backend.model.enums.ItemType;
import com.stc.assessments.backend.repository.ItemRepository;
import com.stc.assessments.backend.repository.PermissionGroupRepository;
import com.stc.assessments.backend.services.ItemServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping ("/item")
@RestController
public class ItemController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    PermissionGroupRepository permissionGroupRepository;

    @Autowired
    ItemServices itemServices;

//    @PostMapping("/saveSpace")
//    public ResponseEntity<?> saveSpaceItem(  @RequestParam(required =true) String name ,@RequestParam(required =false) String type
//            ,@RequestParam(required =true) String permisstionGroupName){
//        log.info("ItemController - saveItem item name :{} ,type:{}, permissionGroupName:{}",name ,type,permisstionGroupName);
//        try {
//            Item createdSpace = spaceService.createSpace("stc-assessments");
//           // return ResponseEntity.ok("Space created with ID: " + createdSpace);
//            return ResponseEntity.ok(itemServices.saveParentItem ( name,ItemType.Space,permisstionGroupName ));
//        } catch (Exception e) {
//            return  ResponseEntity.badRequest().body(e.getMessage());
//        }
   // }

    @PostMapping("/saveItemChild")
    public ResponseEntity<?> saveFolderItem(  @RequestParam(required =true) String name
            ,@RequestParam(required =true) String  parentItemName ,@RequestParam(required =true) String userEmail){
        log.info("ItemController - saveItem item name :{} , parentItemName:{} ,userEmail :{}",name ,parentItemName ,userEmail);
        try {

            return ResponseEntity.ok(itemServices.saveChildItem  ( name,parentItemName,userEmail));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

