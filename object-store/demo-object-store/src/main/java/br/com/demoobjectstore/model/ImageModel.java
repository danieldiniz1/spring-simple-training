package br.com.demoobjectstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "images")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String objectId;

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public static ImageModel of(String objectId) {
        var imageModel = new ImageModel();
        imageModel.setObjectId(objectId);
        return imageModel;
    }
}
