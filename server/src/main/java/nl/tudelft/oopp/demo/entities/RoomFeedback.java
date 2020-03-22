package nl.tudelft.oopp.demo.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class RoomFeedback {
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
    private RoomReservation roomReservation;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    private String feedback;

    public RoomFeedback() {

    }

    /**
     * Constructor for Bike Feedback.
     * @param client client sending feedback.
     * @param recipient admin receiving feedback.
     * @param roomReservation bike reservation receiving feedback.
     * @param time time when feedback was sent.
     * @param feedback feedback text field.
     */
    public RoomFeedback(AppUser client, AppUser recipient, RoomReservation roomReservation, Date time, String feedback) {
        this.client = client;
        this.recipient = recipient;
        this.roomReservation = roomReservation;
        this.time = time;
        this.feedback = feedback;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public RoomReservation getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(RoomReservation roomReservation) {
        this.roomReservation = roomReservation;
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
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomFeedback)) {
            return false;
        }
        RoomFeedback that = (RoomFeedback) o;
        return Objects.equals(client, that.client)
                && Objects.equals(recipient, that.recipient)
                && Objects.equals(roomReservation, that.roomReservation)
                && Objects.equals(time, that.time)
                && Objects.equals(feedback, that.feedback);
    }
}