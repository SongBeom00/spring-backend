package org.songbeom.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.dto.ProductDTO;
import org.songbeom.mallapi.util.CustomFileUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final CustomFileUtil fileUtil;


    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        log.info("fileName: {}",fileName);

        return fileUtil.getFile(fileName);
    }



    @PostMapping
    public Map<String,String> register(ProductDTO productDTO){
        log.info("productDTO....... {}",productDTO);

        List<MultipartFile> files = productDTO.getFiles(); //파일들을 가져온다.

        List<String> uploadedFileNames = fileUtil.saveFiles(files); //파일들을 저장하고 저장된 파일 이름을 가져온다.

        productDTO.setUploadedFileNames(uploadedFileNames); //저장된 파일 이름을 DTO 에 저장한다.

        log.info("uploadedFileNames: {}",uploadedFileNames);


        return Map.of("result","success");
    }

    @DeleteMapping("/{fileName}")
    public Map<String,String> deleteFile(@PathVariable List<String> fileName){
        log.info("fileName: {}",fileName);

        fileUtil.deleteFile(fileName);

        return Map.of("result","success");
    }



}
