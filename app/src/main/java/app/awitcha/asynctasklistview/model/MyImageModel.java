package app.awitcha.asynctasklistview.model;

import java.util.List;

/**
 * Created by Nirvana on 20/11/2559.
 */
public class MyImageModel {
    private boolean status;
    private List<ImageModel> images;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ImageModel> getImages() {
        return images;
    }

    public void setImages(List<ImageModel> images) {
        this.images = images;
    }
}
