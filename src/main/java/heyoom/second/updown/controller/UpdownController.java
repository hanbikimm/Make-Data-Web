package heyoom.second.updown.controller;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import heyoom.second.updown.service.UpdownService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/updown")
public class UpdownController {
	
	private final UpdownService updownService;
	
	@Value("${download.path}")
	private String downloadPath;
	
	@GetMapping
	public String main() {
		return "set-updown";
	}
	
	@GetMapping("/log")
	public String logList() {
		return "updown-log";
	}
	
	// 파일 다운로드
	@GetMapping("/download/{num}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String num) throws IOException{
		String fileName;
		switch (num) {
		case "1": 
			fileName = updownService.makeTxt();
			break;
		case "2": 
			fileName = updownService.makeExcel();
			break;
		case "3": 
			fileName = updownService.makeXml();
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + num);
		}
		
		UrlResource urlResource = new UrlResource("file:" + downloadPath + fileName);
		
		String encodedUploadFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(urlResource);
	}

}
