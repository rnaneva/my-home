package bg.softuni.myhome.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pictures")
public class PictureEntity extends BaseEntity {

    private String title;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    private OfferEntity offer;

    public OfferEntity getOffer() {
        return offer;
    }

    public PictureEntity setOffer(OfferEntity offer) {
        this.offer = offer;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public PictureEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureEntity setUrl(String url) {
        this.url = url;
        return this;
    }
}
