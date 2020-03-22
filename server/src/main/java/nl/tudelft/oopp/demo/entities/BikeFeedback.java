package nl.tudelft.oopp.demo.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class BikeFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private AppUser client;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private AppUser recipient;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    private BikeReservation bikeReservation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    private String feedback;

    public BikeFeedback() {

    }

    public BikeFeedback(AppUser client, AppUser recipient, BikeReservation bikeReservation, Date time, String feedback) {
        this.client = client;
        this.recipient = recipient;
        this.bikeReservation = bikeReservation;
        this.time = time;
        this.feedback = feedback;
    }

    public AppUser getClient() {
        return client;
    }

    public void setClient(AppUser client) {
        this.client = client;
    }

    public AppUser getRecipient() {
        return recipient;
    }

    public void setRecipient(AppUser recipient) {
        this.recipient = recipient;
    }

    public BikeReservation getBikeReservation() {
        return bikeReservation;
    }

    public void setBikeReservation(BikeReservation bikeReservation) {
        this.bikeReservation = bikeReservation;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BikeFeedback)) return false;
        BikeFeedback that = (BikeFeedback) o;
        return Objects.equals(client, that.client) &&
                Objects.equals(recipient, that.recipient) &&
                Objects.equals(bikeReservation, that.bikeReservation) &&
                Objects.equals(time, that.time) &&
                Objects.equals(feedback, that.feedback);
    }
}