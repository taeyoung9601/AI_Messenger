package org.zerock.myapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.myapp.entity.UpFile;
import org.zerock.myapp.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@Slf4j
@RequestMapping("/file")
@RestController
@RequiredArgsConstructor
public class FileController {
	
	private final FileService service;
	
	
//	@PostMapping("/upload")
//	public void upload(@RequestParam MultipartFile file) {
//		log.info("📤 upload() 호출됨"); 
//		service.save(file);
//		return;
//	};
	
	@PostMapping("/upload")
	public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam MultipartFile file) {
	    // 파일 저장 로직...
	    UpFile savedFile = service.save(file);

	    Map<String, Object> result = new HashMap<>();
	    result.put("id", savedFile.getId()); // 프론트에서 사용할 파일 ID
	    result.put("uuid", savedFile.getUuid());
	    log.debug("result: {}" , result);
	    return ResponseEntity.ok(result);
	}

		
	
	@GetMapping("/get/{file}")
	public ResponseEntity<Resource> getFile(@PathVariable String file) {
		Resource resource = service.getFile(file);
		
		if(resource != null) {
			return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, 
					"attachment; filename=\""  + resource.getFilename() + "\"" )
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.body(resource);
			}
		return ResponseEntity.internalServerError().build();
	}
		
			
} // end class
