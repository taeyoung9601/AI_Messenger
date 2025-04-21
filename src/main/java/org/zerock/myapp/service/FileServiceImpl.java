package org.zerock.myapp.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.entity.UpFile;
import org.zerock.myapp.persistence.FileRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor

@Service
public class FileServiceImpl implements FileService {
    
	@Autowired FileRepository dao;

    @Value("${file.path}")
    private String filePath; 
    
    @Override
    public void save(MultipartFile file) {
        // 디렉토리 확인 & 생성
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf('.') + 1);
        String uuidFileName = UUID.randomUUID().toString() + '.' + extension;

        UpFile upfile = new UpFile();
        
        upfile.setOriginal(originalName);
        upfile.setUuid(uuidFileName);
        upfile.setPath(filePath);
        upfile.setEnabled(true);
        
        dao.save(upfile);
        
        // 저장할 파일 경로 생성
        File dest = new File(filePath, uuidFileName);
        System.out.println("📦 실제 저장 파일명: " + uuidFileName);
        System.out.println("📍 저장 위치: " + dest.getAbsolutePath());
        try {
            file.transferTo(dest);
        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
    }
   

    @Override
    public Resource getFile(String fileName) {
    	String dir = System.getProperty("user.dir") + "/" + filePath + "/" + fileName;
    	Path path = Paths.get(filePath).resolve(fileName);
		try {
			Resource resource = new UrlResource(path.toUri());
			return resource;
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		return null;
    }
	
}//end class
