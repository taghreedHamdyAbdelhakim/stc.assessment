package com.stc.assessments.backend.services;

import com.stc.assessments.backend.exception.BusinessException;
import com.stc.assessments.backend.model.File;
import com.stc.assessments.backend.model.Item;
import com.stc.assessments.backend.model.Permission;
import com.stc.assessments.backend.model.PermissionGroup;
import com.stc.assessments.backend.model.enums.ItemType;
import com.stc.assessments.backend.model.enums.PermissionLevel;
import com.stc.assessments.backend.repository.FileRepository;
import com.stc.assessments.backend.repository.ItemRepository;
import com.stc.assessments.backend.repository.PermissionGroupRepository;
import com.stc.assessments.backend.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class ItemServices {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    PermissionGroupRepository permissionGroupRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    FileRepository fileRepository;

    public Item createSpace ( String spaceName ) {
        log.info ( "SpaceService - createSpace spaceName:{}" , spaceName );
        if ( spaceName == null || spaceName.isEmpty ( ) ) {
            throw new BusinessException ( "Space name cannot be empty." );
        }
        Item space = new Item ( );
        space.setName ( spaceName );
        space.setType ( ItemType.Space.name ( ) );

        PermissionGroup adminGroup = new PermissionGroup ( );
        adminGroup.setGroupName ( "admin" );
        permissionGroupRepository.save ( adminGroup );


        Permission viewPermission = new Permission ( );
        viewPermission.setUserEmail ( "taghreed@gmail.com" );
        viewPermission.setPermissionLevel ( "VIEW" );
        viewPermission.setGroup ( adminGroup );
        permissionRepository.save ( viewPermission );

        Permission editPermission = new Permission ( );
        editPermission.setUserEmail ( "admin@stc.com" );
        editPermission.setPermissionLevel ( "EDIT" );
        editPermission.setGroup ( adminGroup );
        permissionRepository.save ( editPermission );

        space.setPermissionGroup ( adminGroup );
        space = itemRepository.save ( space );
        log.info ( "item name:{}" , space);
        return space;


    }
    public Item createFolder( Long spaceId, String folderName, String userEmail) throws BusinessException {
        if (folderName == null || folderName.isEmpty()) {
            log.info ( "ItemServices - createFolder - Folder name cannot be empty." );
            throw new BusinessException ("folderName cannot be empty.");
        }

        Item space= itemRepository.findById ( spaceId ).orElse ( null );
        if (space == null) {
            log.info ( "ItemServices- createFolder - Space not found." );
            throw new BusinessException("Space not found.");
        }

        PermissionGroup adminGroup = space.getPermissionGroup ();

        Permission editPermission = permissionRepository.findByUserEmailAndGroupAndPermissionLevel(userEmail, adminGroup, PermissionLevel.EDIT);
        if (editPermission == null) {
            log.info ( "ItemServices- createFolder - user did not have EDIT access to this space." );
            throw new BusinessException("You do not have EDIT access to this space.");
        }
        Item folder= new Item ();
        folder.setName ( folderName);
        folder.setType ( ItemType.Folder.name ( ) );
        folder.setParent ( space );
        folder.setPermissionGroup ( adminGroup );
        return itemRepository.save ( folder );

    }

    public Item saveParentItem(String itemName,ItemType itemType,String permissionGroupName){
        log.info("ItemServices - saveParentItem");
        Item item = new Item ();
        item.setName ( itemName);
        item.setType ( itemType.name () );
        PermissionGroup permissionGroup = permissionGroupRepository.findByGroupName ( permissionGroupName );
        log.info ( "permission Group name:{} ,id:{}" , permissionGroup.getGroupName (),permissionGroup.getId () );
        item.setPermissionGroup (permissionGroup);
        log.info ( "item name:{}" ,item.getName () );
       return itemRepository.save ( item );


    }


    public Item saveChildItem( String itemName, String parentItemName,String userEmail){
        log.info("ItemServices - saveChildItemname :{} , parentItemName:{} ,userEmail :{}",itemName ,parentItemName ,userEmail);

            try{

            Item item = new Item ();
            item.setName ( itemName);
           Item parentItem = itemRepository.findByName ( parentItemName);
           item.setParent ( parentItem );
           Permission permission=permissionRepository.findByUserEmail ( userEmail );
           if ( permission != null & permission.getPermissionLevel ( ) != null & permission.getPermissionLevel ( ).name ( ).equals ( PermissionLevel.EDIT.name ( ) ) ) {
                item.setPermissionGroup ( permission.getGroup ( ) );
               if(parentItem.getType ().name ().equals ( ItemType.Space.name ( ) ))  {
                   item.setType ( ItemType.Folder.name ( ) );
               }
               else if (parentItem.getType ().name ().equals ( ItemType.Folder.name ( ) )) {
                   item.setType ( ItemType.File.name ( ) );
               }

               log.info ( "item", item );
                Item savedItem=  itemRepository.save ( item );
                if(savedItem.getType ().name ().equals ( ItemType.File.name ( )  ))
                {
                    File file= new File (  );

                    file.setBinary (saveBinaryFile ( savedItem.getName () ));
                    fileRepository.save ( file );



                }

                return savedItem;
            } else {

                return null;
            }


        }catch ( Exception e ){
                e.printStackTrace (  );
            return null;
               }
    }

   private byte [] saveBinaryFile(String fileName) throws IOException {

       ClassPathResource resource = new ClassPathResource ("test.txt");
       InputStream inputStream = resource.getInputStream();

       return inputStream.readAllBytes ();
   }


}
