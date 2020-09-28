/*
For taking in a file upload
 */
package com.sg.classroster.daos;

import org.springframework.web.multipart.MultipartFile;

public interface ImageDao {

    String saveImage(MultipartFile file, String fileName, String directory);

    String updateImage(MultipartFile file, String fileName, String directory);

    boolean deleteImage(String oldFile);
}
