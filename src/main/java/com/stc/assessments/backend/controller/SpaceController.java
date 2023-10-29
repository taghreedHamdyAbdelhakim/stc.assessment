package com.stc.assessments.backend.controller;

import com.stc.assessments.backend.model.Item;
import com.stc.assessments.backend.services.ItemServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/spaces")
@Slf4j
public class SpaceController {
    @Autowired
    ItemServices itemServices;

    @PostMapping
    public ResponseEntity <Item> createSpace( ) {
        Item createdSpace = itemServices.createSpace("stc-assessments");
        log.info ( "SpaceController - createSpace - spaceId:{}" ,createdSpace.getId ());
        return ResponseEntity.ok( createdSpace);
    }
}