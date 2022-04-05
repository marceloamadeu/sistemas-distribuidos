import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
 
    private int id;
    private String name;
    private String publicKey;
    private String remoteObjectReference;
    private boolean subscriber = true;

    public User() {

    }

    public User(String name, String publicKey, String remoteObjectReference, 
            boolean subscriber) {
        this.name = name;
        this.publicKey = publicKey;
        this.remoteObjectReference = remoteObjectReference;
        this.subscriber = subscriber;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getRemoteObjectReference() {
        return this.remoteObjectReference;
    }

    public void setRemoteObjectReference(String remoteObjectReference) {
        this.remoteObjectReference = remoteObjectReference;
    }

    public boolean isSubscriber() {
        return this.subscriber;
    }

    public boolean getSubscriber() {
        return this.subscriber;
    }

    public void setSubscriber(boolean subscriber) {
        this.subscriber = subscriber;
    }
    


   
}
