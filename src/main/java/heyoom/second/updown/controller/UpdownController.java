package heyoom.second.updown.controller;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
		String monthAndfile;
		switch (num) {
		case "1": 
			monthAndfile = updownService.downloadTxt();
			break;
		case "2": 
			monthAndfile = updownService.downloadExcel();
			break;
		case "3": 
			monthAndfile = updownService.downloadXml();
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + num);
		}
		
		UrlResource urlResource = new UrlResource("file:" + downloadPath + monthAndfile);
		
		String fileName = monthAndfile.substring(7);
		String encodedUploadFileName = UriUtils.encode(fileName, StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(urlResource);
	}
	
	// 파일 업로드
	@RequestMapping("/upload/{num}")
	public String uploadFile(@PathVariable String num){
		String result;
		switch (num) {
		case "1": 
			result = updownService.uploadTxt();
			break;
		case "2": 
			result = updownService.uploadExcel();
			break;
		case "3": 
			result = updownService.uploadXml();
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + num);
		}
		
		;
		return "redirect:/updown";
	}

}
