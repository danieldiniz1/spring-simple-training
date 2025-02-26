package br.com.demoobjectstore.controller;

import br.com.demoobjectstore.model.ImageModel;
import br.com.demoobjectstore.repository.ImageRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("images")
public class UploadController {

    private final MinioClient minioClient;
    private final ImageRepository imageRepository;

    public UploadController(MinioClient minioClient, ImageRepository imageRepository) {
        this.minioClient = minioClient;
        this.imageRepository = imageRepository;
    }

    @PostMapping
    private void uploadImage(@RequestParam MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        InputStream inputStream = file.getInputStream();
        var objectId = UUID.randomUUID().toString();
        minioClient.putObject(PutObjectArgs.builder()
                .bucket("images")
                .object(objectId)
                .stream(inputStream, inputStream.available(), -1)
                .contentType("image/png")
                .build());

        imageRepository.save(ImageModel.of(objectId));
    }

    @GetMapping(value = "/{ObjectId}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable String ObjectId) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        var stream = minioClient.getObject(
                io.minio.GetObjectArgs.builder()
                        .bucket("images")
                        .object(ObjectId)
                        .build()
        );

        return IOUtils.toByteArray(stream);
    }

}
