package org.songbeom.mallapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.songbeom.mallapi.dto.PageRequestDTO;
import org.songbeom.mallapi.dto.PageResponseDTO;
import org.songbeom.mallapi.dto.ProductDTO;
import org.songbeom.mallapi.service.ProductService;
import org.songbeom.mallapi.util.CustomFileUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        log.info("fileName: {}",fileName);

        return fileUtil.getFile(fileName);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponseDTO<ProductDTO> list(PageRequestDTO pageRequestDTO){

        return productService.searchProductList(pageRequestDTO);
    }


    @GetMapping("/{pno}")
    public ProductDTO read(@PathVariable("pno") Long pno){
        return productService.get(pno);
    }

    @PostMapping
    public Map<String,Long> register(ProductDTO productDTO){
        log.info("productDTO....... {}",productDTO);

        List<MultipartFile> files = productDTO.getFiles(); //파일들을 가져온다.

        List<String> uploadedFileNames = fileUtil.saveFiles(files); //파일들을 저장하고 저장된 파일 이름을 가져온다.

        productDTO.setUploadedFileNames(uploadedFileNames); //저장된 파일 이름을 DTO 에 저장한다.

        Long pno = productService.register(productDTO);   //상품을 등록한다.

        log.info("uploadedFileNames: {}",uploadedFileNames);

        try {
            Thread.sleep(2000); // 서버 부하를 일으키기 위해 2초간 대기
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return Map.of("result",pno);
    }

    @PutMapping("/{pno}")
    public Map<String,String> modify(@PathVariable("pno") Long pno, @ModelAttribute ProductDTO productDTO){

        productDTO.setPno(pno);
        //old product -> 원래의 상품 정보를 가져온다. DB 에 저장된 상품 정보입니다.
        ProductDTO oldProductDTO = productService.get(pno);
        //file upload
        List<String> oldFileNames = oldProductDTO.getUploadedFileNames();

        List<MultipartFile> files = productDTO.getFiles(); // 새로운 파일들
        List<String> currentUploadFileNames = fileUtil.saveFiles(files);

        //기존에 저장되어 있던 파일 이름들
        List<String> uploadedFileNames = productDTO.getUploadedFileNames();

        if(currentUploadFileNames != null && !currentUploadFileNames.isEmpty()){
            uploadedFileNames.addAll(currentUploadFileNames);
        }

        //수정 작업
        productService.modify(productDTO);


        if(oldFileNames != null && !oldFileNames.isEmpty()){
            List<String> removeFiles =
            oldFileNames.stream().filter(fileName -> !uploadedFileNames.contains(fileName))
                    .collect(Collectors.toList());
            fileUtil.deleteFile(removeFiles);
        }

        return Map.of("RESULT","SUCCESS");
    }

    @DeleteMapping("/view/{fileName}")
    public Map<String,String> deleteFile(@PathVariable List<String> fileName){
        log.info("fileName: {}",fileName);

        fileUtil.deleteFile(fileName);

        return Map.of("result","success");
    }


    @DeleteMapping("/{pno}")
    public Map<String,String> remove(@PathVariable Long pno){

        List<String> oldFileNames = productService.get(pno).getUploadedFileNames();

        productService.remove(pno);

        fileUtil.deleteFile(oldFileNames);

        return Map.of("result","success");
    }





}
