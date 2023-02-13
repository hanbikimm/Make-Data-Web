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
	
	@Value("${dir.path}")
	private String path;
	
	@GetMapping
	public String main() {
		return "set-updown";
	}
	
	@GetMapping("/log")
	public String logList() {
		return "updown-log";
	}
	
	
	
	// Text 파일 다운로드
	@GetMapping("/download/txt")
	public ResponseEntity<Resource> downloadTextFile() throws IOException{
		String fileName = updownService.makeTxt();
		UrlResource urlResource = new UrlResource("file:" + path + fileName);
		
		String encodedUploadFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(urlResource);
	}
	
	// Excel 파일 다운로드
	@GetMapping("/download/excel")
	public ResponseEntity<Resource> downloadExcelFile() throws IOException{
		String fileName = updownService.makeExcel();
        
		UrlResource urlResource = new UrlResource("file:" + path + fileName);
		
		String encodedUploadFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(urlResource);
	}
		
	// 첨부파일 다운로드
	@GetMapping("/download/xml")
	public ResponseEntity<Resource> downloadXmlFile() throws IOException{
		String fileName = updownService.makeTxt();
        
		UrlResource urlResource = new UrlResource("file:" + path + fileName);
		
		String encodedUploadFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(urlResource);
	}

}
