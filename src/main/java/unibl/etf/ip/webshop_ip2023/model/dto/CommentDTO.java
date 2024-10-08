package unibl.etf.ip.webshop_ip2023.model.dto;


public class CommentDTO {
    private long id;
    private String content;
    private String creatorInfo;
    private String time;
    private long productId;
    private String creatorAvatar;

    public String getCreatorAvatar() {
        return creatorAvatar;
    }

    public void setCreatorAvatar(String creatorAvatar) {
        this.creatorAvatar = creatorAvatar;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatorInfo() {
        return creatorInfo;
    }

    public void setCreatorInfo(String creator) {
        this.creatorInfo = creator;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", creatorInfo='" + creatorInfo + '\'' +
                ", time='" + time + '\'' +
                ", productId=" + productId +
                ", creatorAvatar='" + creatorAvatar + '\'' +
                '}';
    }
}
