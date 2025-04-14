package org.zerock.myapp.controller.common;
 
 import java.util.List;
 import java.util.Vector;
 
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.zerock.myapp.entity.Code;
 
 import lombok.NoArgsConstructor;
 import lombok.extern.slf4j.Slf4j;
 
 @Slf4j
 @NoArgsConstructor
 
 @Controller
 public class CommonController { 
 	
 	
 	@GetMapping(path = "/getCodeList", params = { "category" } )
 	List<Code> codeList(String category) { // code리스트
 		log.debug("codeList() invoked.");
 		
 		List<Code> list = new Vector<>();
 		
 		return list;
 	} // codeList
 	
 	@GetMapping(path = "/getCodeName", params = { "category", "code" } )
 	String codeName(String category, Integer code) { 
 		log.debug("codeName() invoked.");
 		
 		String codeName = "";
 		
 		return codeName;
 	} // codeName
 	
 } // end class