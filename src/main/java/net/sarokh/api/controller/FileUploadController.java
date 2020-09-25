package net.sarokh.api.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sarokh.api.util.ApiResponse;
import net.sarokh.api.util.ApplicationMessagesUtil;
import net.sarokh.api.util.ApplicationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    @Value("${file.upload.directory}")
    private String FILE_UPLOAD_DIR;

    @Value("${resources.directory}")
    private String RESOURCES_DIR;

    @Value("${web.application.url}")
    private String WEB_APP_URL;

    @RequestMapping(value = "/upload-file", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {

        String filePath = null;

        if (uploadfile.isEmpty()) {
            return new ResponseEntity<String>(ApplicationMessagesUtil.INVALID_FILE_UPLOAD, HttpStatus.ACCEPTED);
        }

        try {
            List<String> paths = new ArrayList<String>();
            paths = saveUploadedFiles(Arrays.asList(uploadfile));

            if (paths != null && paths.size() > 0) {
               // JSONObject filePathJson = new JSONObject();
               // filePathJson.put("filePath", paths.get(0));
               // filePath = FILE_UPLOAD_PATH + filePathJson.toString();
                filePath = paths.get(0);
                //System.out.println("upload file Response = " + filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok(ApiResponse.builder()
                    .message(ApplicationMessagesUtil.UNABLE_TO_UPLOAD_FILE)
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build());
        }

        return ResponseEntity.ok(ApiResponse.builder()
                .data(filePath)
                .status(HttpStatus.OK.value())
                .message(ApplicationMessagesUtil.FILE_UPLOAD_SUCCESSFULLY)
                .build());
    }

    private List<String> saveUploadedFiles(List<MultipartFile> files) throws IOException {
        List<String> paths = new ArrayList<String>();
        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            String filePath = FILE_UPLOAD_DIR + RESOURCES_DIR;
            Path path = Paths.get(filePath + ApplicationUtil.randomAlphaNumeric(12) + "_" + file.getOriginalFilename());
            Files.write(path, bytes);
            String fileUrl = null;

            if (!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
                fileUrl = path.toString().replace(FILE_UPLOAD_DIR, WEB_APP_URL);
            } else {
                fileUrl = path.toString();
            }

            System.out.println("FileUrl=" + file.getContentType());

            paths.add(fileUrl);
        }

        return paths;
    }
}
