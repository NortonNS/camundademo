package com.camunda.camundademo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @RequestMapping("/test")
    public String returnTest() {
        return "Testing";
    }

    @RequestMapping("/")
    public String returnDefault() {
        return "Default";
    }

//    private DmnTemplateService dmnTemplateService;
//
//    public AdminController(DmnTemplateService dmnTemplateService) {
//        this.dmnTemplateService = dmnTemplateService;
//    }
//
//    @PostMapping(value = "/rules/import")
//    public ResponseEntity<BasicResponseMessage> importDecisionRules(@RequestParam("file")MultipartFile file) {
//        try {
//            log.info("Importing new excel files");
//            dmnTemplateService.convertDmnTemplate(file.getInputStream());
//            return ResponseEntity
//                    .ok(new BasicResponseMessage("Rules are imported successfully"));
//        } catch (IOException e) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(new BasicResponseMessage("Failed to convert DMN template file"));
//        }
//
//    }
}
