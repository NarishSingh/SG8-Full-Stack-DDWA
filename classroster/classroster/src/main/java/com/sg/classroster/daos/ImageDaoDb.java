package com.sg.classroster.daos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class ImageDaoDb implements ImageDao {

    //Multipart file obj's are stored temp, its on the dev to persist it, hence the fields
    private final String RESOURCE_ROOT = "C:/Users/naris/Documents/Work/TECHHIRE/REPOSITORY/SG8-Full-Stack-DDWA/classroster/classroster/src/main/resources/static/";
    private final String UPLOAD_DIRECTORY = "images/uploads/";

    @Override
    public String saveImage(MultipartFile file, String fileName, String directory) {
        String savedFileName = "";

        String mimetype = file.getContentType();
        if (mimetype != null && mimetype.split("/")[0].equals("image")) {
            //prepare the filename for persistance
            String originalName = file.getOriginalFilename();
            String[] parts = originalName.split("[.]");
            fileName = fileName + "." + parts[parts.length - 1];

            try {
                //assemble and make path if needed
                String fullPath = RESOURCE_ROOT + UPLOAD_DIRECTORY + directory + "/";
                File dir = new File(fullPath);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                Path path = Paths.get(fullPath + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                savedFileName = UPLOAD_DIRECTORY + directory + "/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } //can also else throw an exception

        return savedFileName;
    }

    @Override
    public String updateImage(MultipartFile file, String fileName, String directory) {
        String savedFileName = "";

        if (fileName != null && !fileName.isEmpty()) {
            //delete with old file
            File oldFile = new File(RESOURCE_ROOT + fileName);
            oldFile.delete();

            //make a new filename if name is invalid
            String[] fileNameParts = fileName.split("/");
            fileName = fileNameParts[fileNameParts.length - 1].split("[.]")[0];
        } else {
            //use the time in
            fileName = Long.toString(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        }

        String mimetype = file.getContentType(); //check the file extension
        if (mimetype != null && mimetype.split("/")[0].equals("image")) {
            String originalName = file.getOriginalFilename();
            String[] parts = originalName.split("[.]");
            fileName = fileName + "." + parts[parts.length - 1];

            try {
                String fullPath = RESOURCE_ROOT + UPLOAD_DIRECTORY + directory + "/";
                File dir = new File(fullPath);

                if (!dir.exists()) {
                    dir.mkdirs();
                }

                Path path = Paths.get(fullPath + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                savedFileName = UPLOAD_DIRECTORY + directory + "/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } //can also else throw an exception

        return savedFileName;
    }

    @Override
    public boolean deleteImage(String fileName) {
        File oldFile = new File(RESOURCE_ROOT + fileName);
        return oldFile.delete();
    }

}
