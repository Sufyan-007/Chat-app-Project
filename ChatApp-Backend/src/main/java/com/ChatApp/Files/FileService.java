package com.ChatApp.Files;

import com.ChatApp.Exceptions.AppException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

    public String addFile(MultipartFile file) throws IOException {
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize",file.getSize());
        metadata.put("type",file.getContentType());
        Object savedFile = gridFsTemplate.store(file.getInputStream(),file.getOriginalFilename(),file.getContentType(),metadata);
        return savedFile.toString();
    }

    public File downloadFile(String id) throws IOException {

        GridFSFile gridFSFile = gridFsTemplate.findOne( new Query(Criteria.where("_id").is(id)) );

        File file= new File();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            file.setFilename( gridFSFile.getFilename() );

            file.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );

            file.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

            file.setFile(gridFsOperations.getResource(gridFSFile).getInputStream().readAllBytes() );
            return file;
        }
        throw new AppException("File not found", HttpStatus.NOT_FOUND);

    }
}
