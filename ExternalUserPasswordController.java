package com.tingweichen.applicationsystem.controller;

import com.tingweichen.applicationsystem.service.ExternalUserPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExternalUserPasswordController {

    private final static Logger log = LoggerFactory.getLogger(ExternalUserPasswordController.class);

    @Autowired
    private ExternalUserPasswordService externalUserPasswordService;


}
