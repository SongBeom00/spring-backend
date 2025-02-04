package org.songbeom.mallapi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${org.songbeom.upload.path}")
    public String uploadPath;


    @PostConstruct //의존성 주입이 이루어진 후 초기화 작업을 수행하기 위한 메서드 -> 폴더를 만들어주는 작업
    public void init(){
        log.info("uploadPath: {}", uploadPath);
        File tempFolder = new File(uploadPath);

        if(!tempFolder.exists()){ //폴더가 존재하지 않으면 폴더를 만들어준다.
            tempFolder.mkdirs();
        }

        uploadPath = tempFolder.getAbsolutePath(); //절대경로로 변경

        log.info("----------------------------------");
        log.info("uploadPath {}",uploadPath);

    }



    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException{

        if(files == null || files.isEmpty()){
            return List.of(); //비어있는 리스트를 반환
        }

        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile file : files) {
            //원본 파일 이름
            String savedName = UUID.randomUUID() + "_" + file.getOriginalFilename(); // UUID 를 이용한 파일 이름 생성

            Path savePath = Paths.get(uploadPath, savedName); //저장 경로

            try {

                Files.copy(file.getInputStream(), savePath); //원본 파일 업로드

                String contentType = file.getContentType(); //파일 타입

                if(contentType != null || contentType.startsWith("image")){ //이미지 파일인 경우
                    Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName); //썸네일 파일 경로

                    Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumbnailPath.toFile()); //썸네일 생성
                }


                uploadNames.add(savedName); //저장된 파일 이름을 리스트에 추가


            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

        return uploadNames;
    }


    public ResponseEntity<Resource> getFile(String fileName) throws RuntimeException{

        try {
            Resource resource = new FileUrlResource(uploadPath+File.separator+fileName); //파일 경로를 이용한 Resource 객체 생성

            if(!resource.exists()){ //파일이 존재하지 않으면
                resource = new FileUrlResource(uploadPath+File.separator+"default.png"); //썸네일 파일 경로를 이용한 Resource 객체 생성
            }

            HttpHeaders headers = new HttpHeaders(); //HttpHeaders 객체 생성
            headers.add("Content-Type",Files.probeContentType(resource.getFile().toPath()));


            return ResponseEntity.ok().headers(headers).body(resource); //Resource 객체를 이용한 ResponseEntity 객체 반환

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void deleteFile(List<String> fileNames) throws RuntimeException{

        if(fileNames == null || fileNames.isEmpty()){
            return;
        }

        fileNames.forEach(fileName -> {
            Path file = Paths.get(uploadPath, fileName); //파일 경로

            try {
                Files.deleteIfExists(file); //파일 삭제

                Path thumbnail = Paths.get(uploadPath, "s_" + fileName); //썸네일 파일 경로

                Files.deleteIfExists(thumbnail); //썸네일 파일 삭제

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });



    }



}
