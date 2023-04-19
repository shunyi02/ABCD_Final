package group.assignment.abcdfinal.model;

public class HomeModel {

    private String userName,timeStamp,profileImage,postImage,uid, comment, description,id;

    private int likeCount;

    public HomeModel(){
    }

    public HomeModel(String userName, String timeStamp, String profileImage, String postImage, String uid, String comment, String description,String id, int likeCount) {
        this.userName = userName;
        this.timeStamp = timeStamp;
        this.profileImage = profileImage;
        this.postImage = postImage;
        this.uid = uid;
        this.comment = comment;
        this.description = description;
        this.likeCount = likeCount;
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
