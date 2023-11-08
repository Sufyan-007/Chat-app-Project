package com.ChatApp.Files;

import lombok.Data;
import lombok.Getter;

@Data
public class File {
    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;
}
